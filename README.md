# Custom Validation Annotations for Spring Boot

This repository provides a collection of custom validation annotations for use with Spring Boot applications. These
annotations help you enforce specific validation rules in your application by adding meaningful constraints (even though
some of them are just for fun) to your data models.

## Video Tutorial

Learn how to create these custom validation annotations and integrate them into your Spring Boot applications.
Watch the tutorial on YouTube for step-by-step guidance and examples:

[Watch the Tutorial](https://youtu.be/c_C1lv4Kh7U?si=KbP1rErIujlAE9qp)

## Custom Validation Annotations

### Age

The `@Age` annotation ensures that a `LocalDate` field represents an age within a specified range.

### AtLeastOneNotBlank

The `@AtLeastOneNotBlank` annotation validates whether at least one of the specified fields is not blank (non-empty).

### ExcludedNumbers

The `@ExcludedNumbers` annotation checks that a field's value is not one of the specified excluded numbers.

### Fibonacci

The `@Fibonacci` annotation validates whether a field's value is a number within the Fibonacci series.

### IntegerValues

The `@IntegerValues` annotation ensures that a field's value is one of the specified integer values.

### ISO3166CountryCode

The `@ISO3166CountryCode` annotation checks whether a field's value represents a valid ISO 3166-1 country code (either
alpha-2 or alpha-3).