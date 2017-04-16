package mobymagic.com.popularmovies.utils;

public class DisplayUtils {

    public static int getGridSpanCount(int measuredWidth, int gridWidth) {
        int newSpanCount = (int) Math.floor(measuredWidth / gridWidth);
        if (newSpanCount == 0) {
            // Can't be zero, if not bad things will happen
            newSpanCount = 2;
        }

        return newSpanCount;
    }
}
