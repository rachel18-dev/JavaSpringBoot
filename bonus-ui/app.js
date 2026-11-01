// Product Catalog UI: plain JavaScript calling the same secured REST API that Postman tests.
// No framework and no build step: Spring Boot serves this file from src/main/resources/static.

let token = null;     // the JWT lives in memory only: reload the page and you sign in again
let page = 0;
let query = "";
let category = null;  // set by clicking a category in the table
let editingId = null; // set while the form edits an existing product

const $ = (id) => document.getElementById(id);
const money = new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" });

// Every API call goes through here: adds the Bearer token and turns errors into exceptions
async function api(path, options = {}) {
  const res = await fetch(path, {
    ...options,
    headers: { "Content-Type": "application/json", Authorization: "Bearer " + token },
  });
  if (res.status === 401) {  // token expired (after 1 hour) or invalid
    signOut();
    $("login-error").textContent = "Your session expired. Please sign in again.";
    throw new Error("Session expired");
  }
  const text = await res.text();  // 204 and 403 have no body
  const body = text ? JSON.parse(text) : null;
  if (!res.ok) {
    const message = res.status === 403 ? "You don't have permission to do that." : body?.detail || `Request failed (${res.status})`;
    throw Object.assign(new Error(message), { problem: body });
  }
  return body;
}

// ---------- sign in / sign out ----------
$("login-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  const f = new FormData(e.target);
  const res = await fetch("/api/auth/token", {
    method: "POST",
    headers: {
      Authorization: "Basic " + btoa(f.get("username") + ":" + f.get("password")),
      "X-Requested-With": "XMLHttpRequest",  // Spring Security then skips WWW-Authenticate, so no browser pop-up
    },
  });
  if (!res.ok) {
    $("login-error").textContent = "Wrong username or password.";
    return;
  }
  token = (await res.json()).access_token;
  // Read the claims only to adapt the screen; the server still checks the role on every request
  const claims = JSON.parse(atob(token.split(".")[1].replace(/-/g, "+").replace(/_/g, "/")));
  document.body.classList.toggle("is-admin", claims.roles.includes("ADMIN"));
  $("who-name").textContent = `${claims.sub} · ${claims.roles.join(", ")}`;
  e.target.reset();
  $("login-error").textContent = "";
  show(true);
  loadProducts();
});

$("logout").addEventListener("click", signOut);

function signOut() {
  token = null;
  page = 0;
  query = "";
  category = null;
  stopEditing();
  document.querySelectorAll("form").forEach((f) => f.reset());
  document.querySelectorAll(".error, small").forEach((el) => (el.textContent = ""));
  $("sort").selectedIndex = 0;
  document.body.classList.remove("is-admin");
  show(false);
}

function show(signedIn) {
  $("login-view").hidden = signedIn;
  $("app-view").hidden = !signedIn;
  $("who").hidden = !signedIn;
}

// ---------- read, search, sort, filter and page ----------
async function loadProducts() {
  // a category uses the repository method's fixed order (cheapest first); the others send ?sort=
  const sort = $("sort").value;
  $("sort").disabled = category !== null;
  $("category-chip").hidden = category === null;
  if (category !== null) $("category-chip").textContent = `Category: ${category} · cheapest first ✕`;
  const path = category !== null
    ? `/api/products?category=${encodeURIComponent(category)}&page=${page}&size=5`
    : query
      ? `/api/products/search?q=${encodeURIComponent(query)}&page=${page}&size=5&${sort}`
      : `/api/products?page=${page}&size=5&${sort}`;
  try {
    const data = await api(path);
    $("rows").replaceChildren(...data.content.map(rowFor));
    if (!data.content.length) $("rows").append(emptyRow());
    $("page-info").textContent = `Page ${data.page.number + 1} of ${Math.max(data.page.totalPages, 1)} · ${data.page.totalElements} products`;
    $("prev").disabled = data.page.number === 0;
    $("next").disabled = data.page.number + 1 >= data.page.totalPages;
    $("list-error").textContent = "";
  } catch (err) {
    $("list-error").textContent = err.message;
  }
}

function rowFor(p) {
  const tr = document.createElement("tr");
  const cell = (text, cls) => {
    const td = tr.insertCell();
    td.textContent = text;  // textContent, never innerHTML: product data can't inject HTML or scripts
    if (cls) td.className = cls;
    return td;
  };
  cell(p.name);
  const cat = cell("");
  const link = Object.assign(document.createElement("button"), { className: "link", textContent: p.category, title: "Show this category, cheapest first" });
  link.addEventListener("click", () => { category = p.category; query = ""; $("search-form").reset(); page = 0; loadProducts(); });
  cat.append(link);
  cell(money.format(p.price), "num");
  cell(p.quantity, "num");

  const actions = cell("", "admin-only num actions");
  const edit = Object.assign(document.createElement("button"), { className: "link", textContent: "Edit" });
  edit.addEventListener("click", () => startEditing(p));
  const del = Object.assign(document.createElement("button"), { className: "link danger", textContent: "Delete" });
  del.addEventListener("click", async () => {
    if (!confirm(`Delete ${p.name}?`)) return;
    try {
      await api(`/api/products/${p.id}`, { method: "DELETE" });
      if (editingId === p.id) stopEditing();
      loadProducts();
    } catch (err) {
      $("list-error").textContent = err.message;
    }
  });
  actions.append(edit, del);
  return tr;
}

function emptyRow() {
  const tr = document.createElement("tr");
  const td = tr.insertCell();
  td.colSpan = 5;
  td.className = "empty";
  td.textContent = "No products to show.";
  return tr;
}

$("search-form").addEventListener("submit", (e) => {
  e.preventDefault();
  query = new FormData(e.target).get("q").trim();
  category = null;  // search covers every category
  page = 0;
  loadProducts();
});
$("sort").addEventListener("change", () => { page = 0; loadProducts(); });
$("category-chip").addEventListener("click", () => { category = null; page = 0; loadProducts(); });
$("prev").addEventListener("click", () => { page--; loadProducts(); });
$("next").addEventListener("click", () => { page++; loadProducts(); });

// ---------- add and edit (ADMIN) ----------
function startEditing(p) {
  editingId = p.id;
  const form = $("product-form");
  for (const field of ["name", "category", "price", "quantity"]) form.elements[field].value = p[field];
  $("form-title").textContent = `Edit product #${p.id}`;
  $("save").textContent = "Save changes";
  $("cancel").hidden = false;
  clearFormErrors();
  form.scrollIntoView({ behavior: "smooth" });
}

function stopEditing() {
  editingId = null;
  $("product-form").reset();
  $("form-title").textContent = "Add a product";
  $("save").textContent = "Add product";
  $("cancel").hidden = true;
  clearFormErrors();
}

function clearFormErrors() {
  $("product-form").querySelectorAll("small").forEach((s) => (s.textContent = ""));
  $("form-error").textContent = "";
}

$("cancel").addEventListener("click", stopEditing);

$("product-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  clearFormErrors();
  const f = new FormData(form);
  const product = {
    name: f.get("name"),
    category: f.get("category"),
    price: f.get("price") === "" ? null : Number(f.get("price")),
    quantity: Number(f.get("quantity")),
  };
  try {
    if (editingId === null) {
      await api("/api/products", { method: "POST", body: JSON.stringify(product) });
    } else {
      await api(`/api/products/${editingId}`, { method: "PUT", body: JSON.stringify(product) });
    }
    stopEditing();
    loadProducts();
  } catch (err) {
    // 400: the problem details "errors" map has one message per field; anything else: show the detail
    const errors = err.problem?.errors || {};
    for (const [field, message] of Object.entries(errors)) {
      const slot = form.querySelector(`[data-for="${field}"]`);
      if (slot) slot.textContent = message;
    }
    if (!Object.keys(errors).length) $("form-error").textContent = err.message;
  }
});
