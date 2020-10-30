package com.cinema.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatchValidator
        implements ConstraintValidator<FieldsValueMatchValidation, Object> {
    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatchValidation constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(dto).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(dto).getPropertyValue(fieldMatch);
        return fieldValue != null ? fieldValue.equals(fieldMatchValue) : fieldMatchValue == null;
    }
}
