import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <h1>Cloner</h1>
 * <p>Pascal Runtime Library:
 * Creates a deep clone of an object to pass it by value.</p>
 * <p>For instructional purposes only. No warranties.</p>
 */
public class Cloner
{
    public static Object deepClone(Object original)
        throws PascalRunTimeException
    {
        try {
            // Write the original object to a byte array stream.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(original);

            // Construct a copy of the original object from the stream.
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);

            // Return the copy as the deep clone.
            return ois.readObject();
        }
        catch (Exception ex) {
            throw new PascalRunTimeException("Deep clone failed.");
        }
    }
}
