package handlers.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundingCalculator {

    public static double roundDecimal(double value)
    {
        return BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }
}
