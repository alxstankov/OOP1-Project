package handlers.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * The {@code RoundingCalculator} class is used for rounding decimal values.
 */
public class RoundingCalculator {
    /**
     *  Rounds a decimal value to a specified number of decimal places.
     *  This method is used to prevent arithmetic errors that can occur due to
     *  differences in decimal precision. It uses the {@link java.math.BigDecimal}
     *  class with {@link java.math.RoundingMode#HALF_UP} rounding mode.
     *
     * Example:
     * {@code
     *  double value = RoundingCalculator.roundDecimal(3.647123); // The result will be 3.65
     * }
     * @param value The decimal value, that needs to be rounded.
     * @return The rounded decimal value.
     */
    public static double roundDecimal(double value)
    {
        return BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }
}
