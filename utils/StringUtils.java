package utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final String ALPHABET_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";

    public static String generateRandomString(final int length) {
        if (length < 1) {
            throw new IllegalArgumentException("String length cannot be less than 1.");
        }
        StringBuilder newStringBuilder = new StringBuilder();
        final String alphabetWithDigits = ALPHABET_LETTERS.concat(ALPHABET_LETTERS.toUpperCase()).concat(DIGITS);
        final int alphabetWithDigitsLength = alphabetWithDigits.length();
        final Random random = new Random();
        for (int i = 0; i < length; i++) {
            newStringBuilder.append(alphabetWithDigits.charAt(random.nextInt(alphabetWithDigitsLength)));
        }
        return newStringBuilder.toString();
    }

    public static String buildString(final int length, final char symbol) {
        return new String(new char[length]).replace('\0', symbol);
    }

    public static String getFirstSubstring(final String string, final String template) {
        final Pattern pattern = Pattern.compile(template);
        final Matcher matcher = pattern.matcher(string);
        matcher.find();
        return matcher.group(0);
    }

    public static int parseNumberFromText(final String text) {
        return Integer.parseInt(text.replaceAll("[^0-9]+", ""));
    }
}
