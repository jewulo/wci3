/**
 * <h1>RangeChecker</h1>
 *
 * <p>Pascal RunTime Library: Perform a runtime range check.</p>
 */
public class RangeChecker
{
    public static void check(int value, int minValue, int maxValue)
            throws PascalRunTimeException
    {
        if ((value < minValue) || (value > maxValue)) {
            throw new PascalRunTimeException(
                String.format("Range error: %1d not in (%1d, %1d)",
                                value, minValue, maxValue));
        }
    }
}
