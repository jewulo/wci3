/**
 * <h1>RunTimer</h1>
 *
 * <p>Pascal Runtime Library:
 * Compute and print the elapsed run time of a compiled Pascal program.</p>
 */
public class RunTimer
{
    long startTime;

    /**
     * Constructor.
     */
    public RunTimer()
    {
        startTime = System.currentTimeMillis();
    }

    /**
     * Compute and print the elapsed run time.
     */
    public void printElapsedTime()
    {
        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        System.out.println(
                String.format("\n%,20.2f seconds total execution time.", elapsedTime));
    }
}
