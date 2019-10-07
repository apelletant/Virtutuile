package com.virtutuile.system;

import com.virtutuile.system.exeptions.ValidationException;

public final class Validators {

    public static final boolean isNumber(String test) throws ValidationException {
        try {
            Integer.parseInt(test);
        } catch (NumberFormatException except) {
            throw new ValidationException("Bad number format");
        }
        return true;
    }
}
