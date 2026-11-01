package com.example.productapi.validation;

// TODO Day 2 lab (slide "Custom validation: your own constraint")
// A custom Bean Validation constraint: the text must start with a capital letter ("Furniture", not "furniture").
// Make this annotation a constraint:
//   @Constraint(validatedBy = CapitalizedValidator.class)
//   @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
//   @Retention(RetentionPolicy.RUNTIME)
// and give it the three members every constraint needs:
//   String message() default "must start with a capital letter";
//   Class<?>[] groups() default {};
//   Class<? extends Payload>[] payload() default {};
public @interface Capitalized {
}
