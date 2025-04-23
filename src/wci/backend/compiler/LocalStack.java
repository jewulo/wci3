package wci.backend.compiler;

/**
 * <h1>LocalStack</h1>
 * <p>Maintain a method's local runtime stack.</p>
 */
public class LocalStack
{
    private int size;       // current stack size
    private int maxSize;    // maximum attained stack size

    /**
     * Constructor.
     */
    public LocalStack()
    {
        this.size = 0;
        this.maxSize = 0;
    }

    /**
     * Constructor.
     * @param size initial stack size.
     */
    public LocalStack(int size)
    {
        this.size = 0;
        this.maxSize = 0;
    }

    /**
     * Getter
     * @return the current stack size.
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Increase the stack size by a given amount.
     * @param amount the amount to increase.
     */
    public void increase(int amount)
    {
        this.size += amount;
        maxSize = Math.max(maxSize, size);
    }

    /**
     * Decrease the stack size by a given amount.
     * @param amount the amount to decrease.
     */
    public void decrease(int amount)
    {
        this.size -= amount;
    }

    /**
     * Increase and decrease the stack size by the same amount.
     * @param amount the amount to increase and decrease.
     */
    public void use(int amount)
    {
        increase(amount);
        decrease(amount);
    }

    /**
     * Increase and decrease the stack size by different amounts.
     * @param amountIncrease the amount to increase.
     * @param amountDecrease the amount to decrease.
     */
    public void use(int amountIncrease, int amountDecrease)
    {
        increase(amountIncrease);
        decrease(amountDecrease);
    }

    /**
     * Return the maximum attained stack size.
     * @return the maximum size.
     */
    public int capacity()
    {
        return maxSize;
    }
}
