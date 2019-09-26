package by.training.module1.validator;

public final class NumberValidator {

    public static boolean isDouble(String s) {
        if (s == null) {
            return false;
        }

        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String s) {
        if (s == null) {
            return false;
        }

        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
