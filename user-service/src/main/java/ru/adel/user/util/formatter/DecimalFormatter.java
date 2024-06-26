package ru.adel.user.util.formatter;

import java.text.DecimalFormat;


public class DecimalFormatter {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.##");
    private static final DecimalFormat PRICE_FULL_FORMAT = new DecimalFormat("### ### ###,##0.00");
    private static final DecimalFormat PRICE_SHORT_FORMAT = new DecimalFormat("###,###,###,##0.000");
    private static final DecimalFormat EIGHT_DECIMAL_FORMAT = new DecimalFormat("0.0000000000");
    private static final String[] UNITS = {"", "", "M", "B", "T", "Q"};


    private DecimalFormatter() {
    }

    public static String formatDecimal(double number) {
        if (Math.abs(number) < 1) {
            return formatSmallNumber(number);
        }
        return formatLargeDecimal(number);
    }

    public static String formatLargeDecimal(double number) {
        int digitGroups = (int) (Math.log10(Math.abs(number)) / Math.log10(1000));
        int index = Math.min(digitGroups, UNITS.length - 1);
        double formattedNumber = number / Math.pow(1000, index);
        if (digitGroups >= 2) {
            return String.format("%s %s", DECIMAL_FORMAT.format(formattedNumber), UNITS[index]);
        }
        return PRICE_FULL_FORMAT.format(number);
    }

    public static String formatSmallNumber(double number) {
        String formattedShortNumber = PRICE_SHORT_FORMAT.format(number);
        if (containsSignificantDigits(formattedShortNumber)) {
            return formattedShortNumber;
        }
        return EIGHT_DECIMAL_FORMAT.format(number);
    }

    private static boolean containsSignificantDigits(String number) {
        return number.matches(".*[1-9].*");
    }
}
