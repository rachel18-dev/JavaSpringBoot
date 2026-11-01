package com.example.productapi.config;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.server.context.WebServerApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// dev convenience: opens the product catalog in your default browser once the app has started.
// Turn it off with app.open-browser: false (or --app.open-browser=false).
@Component
@Profile("dev")
@ConditionalOnBooleanProperty("app.open-browser")
public class BrowserLauncher {

    private static final Logger log = LoggerFactory.getLogger(BrowserLauncher.class);

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowser(ApplicationReadyEvent event) {
        // only with a real web server (not in MockMvc tests), and only once per run:
        // a system property survives DevTools restarts, so you don't get a new tab on every code change
        if (!(event.getApplicationContext() instanceof WebServerApplicationContext context)
                || System.getProperty("product.browser.opened") != null) {
            return;
        }
        System.setProperty("product.browser.opened", "true");

        // Spring Boot runs headless, so java.awt.Desktop can't be used: ask the operating system instead
        String url = "http://localhost:" + context.getWebServer().getPort();
        String os = System.getProperty("os.name").toLowerCase();
        String[] command = os.contains("win") ? new String[] {"rundll32", "url.dll,FileProtocolHandler", url}
                : os.contains("mac") ? new String[] {"open", url}
                : new String[] {"xdg-open", url};
        try {
            new ProcessBuilder(command).start();
            log.info("Opened {} in your browser", url);
        } catch (IOException e) {
            log.info("Couldn't open a browser; open {} yourself", url);
        }
    }
}
