package com.matrixdev.dremergency.Utils;


import com.matrixdev.dremergency.Application;
import com.matrixdev.dremergency.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Tirthesh on 7/7/2015.
 */
public class Validator {

    public static String isValidEmail(String emailText) {
        String email = Strings.nullSafeString(emailText);
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        if (email.isEmpty()) {
            return Application.getContext().getString(R.string.error_field_required);
        } else if (!matcher.matches()) {
            return Application.getContext().getString(R.string.error_invalid_email);
        }
        return Strings.EMPTY;
    }

    public static String isValidPassword(String password) {
        String pass = Strings.nullSafeString(password);
        if (pass.isEmpty()) {
            return Application.getContext().getString(R.string.error_field_required);
        } else if (pass.length() < 6) {
            return Application.getContext().getString(R.string.error_invalid_password);
        }
        return Strings.EMPTY;
    }

    public static String isValidPhoneNumber(String phoneNumber) {
        String number = Strings.nullSafeString(phoneNumber);

        try {
            Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return Application.getContext().getString(R.string.error_incorrect_phone_number);
        }


        if (number.isEmpty()) {
            return Application.getContext().getString(R.string.error_field_required);
        } else if (number.length() != 10) {
            return Application.getContext().getString(R.string.error_incorrect_phone_number);
        }


        return Strings.EMPTY;
    }

    public static String isValidName(String name) {
        String number = Strings.nullSafeString(name);
        if (number.isEmpty()) {
            return Application.getContext().getString(R.string.error_field_required);

        }

        return Strings.EMPTY;
    }
}
