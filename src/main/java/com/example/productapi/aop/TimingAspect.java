package com.example.productapi.aop;

// TODO Day 1 lab (slide "AOP: aspects, pointcuts and advice"): time every ProductService call
// An @Aspect that is also a @Component, with
//   a @Pointcut for every public ProductService method:
//       execution(public * com.example.productapi.product.ProductService.*(..))
//   an @Around advice that notes System.nanoTime(), calls proceed(), and in a finally block logs at DEBUG
//       "{} took {} ms" with call.getSignature().toShortString() and the milliseconds
// ProductService has no methods yet, so the lines appear from Day 2.
public class TimingAspect {
}
