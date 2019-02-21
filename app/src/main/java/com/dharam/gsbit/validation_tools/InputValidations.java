package com.dharam.gsbit.validation_tools;

/**
 * This class is to check all the validations for inputs available
 */
public class InputValidations {

    private static final int MIN_PASSWORD_LENGTH =  6;

    public static boolean isValidString(String stringToCheck)
    {
        return stringToCheck != null && !("".equals(stringToCheck));
    }

    public static boolean isPasswordValid(String password)
    {
        return isValidString(password) && password.length() >  MIN_PASSWORD_LENGTH;
    }

    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        return isValidString(phoneNumber) && phoneNumber.matches("\\d{10}");
    }
}
