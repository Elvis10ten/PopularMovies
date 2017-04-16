package mobymagic.com.popularmovies.utils;

import android.support.annotation.Nullable;

import java.util.List;

public class ValidateUtils {

    /**
     * Checks if the given list is empty. A list is empty if its null or doesn't have any element.
     * The benefit of using this method is that it does null check for the list
     * @param list the list to check for emptiness
     * @return true if the list is empty, false otherwise
     */
    public static boolean isEmpty(@Nullable List list) {
        return list == null || list.isEmpty();
    }

    /**
     * Checks if the given string is blank. A string is blank if its null or doesn't have any non-whitespace character.
     * The benefit of using this method is that it does null check for the string
     * @param s the string to check for blankness
     * @return true if the string is empty, false otherwise
     */
    public static boolean isBlank(@Nullable String s) {
        return s == null || s.trim().isEmpty();
    }
}
