package com.bn.book.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReturnDateValidator implements ConstraintValidator<ReturnDateConstraint, LocalDate> {
    private int daysFromNow;

    @Override
    public void initialize(ReturnDateConstraint constraint) {
        daysFromNow = constraint.daysFromNow();
    }

    @Override
    public boolean isValid(LocalDate returnDate, ConstraintValidatorContext context) {
        if (returnDate == null) {
            return true;
        }
        LocalDate minimumReturnDate = LocalDate.now().plusDays(daysFromNow);
        return !returnDate.isBefore(minimumReturnDate);
    }
}
