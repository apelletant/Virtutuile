package com.virtutuile.systeme.tools;

public class Validators {

    public static final boolean isNumber(String test) throws ValidationsException {
        try {
            Integer.parseInt(test);
        } catch (NumberFormatException except) {
            throw new ValidationsException("Bad number format");
        }
        return true;
    }
}
