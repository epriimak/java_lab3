import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

/**
 * interface for all algorithm, which take part in conveyor
 * @param <TYPE> type of data, which algorithm return
 */
public interface Algorithm<TYPE> {
    enum typeInOut {
        INPUT,
        OUTPUT
    }

    /**
     * Get name of types that previous algorithm return
     *
     * @return type name
     */
    String[] GetOutTypeData();

    /**
     * Comparing input and output types
     *
     * @param prevAlg previous algorithm
     * @return true, if they are compatible; else otherwise
     */
    boolean ComparingData(Algorithm prevAlg);

    /**
     * Get results of previous algorithm
     *
     * @return result of running algorithm
     */
    TYPE Result();

    /**
     * Writing results in file
     *
     * @param fNameOut file name
     * @throws IOException if file not found
     */
    void Write(String fNameOut) throws IOException;

    /**
     * get default type? which algorithm work
     * @return type name
     */
    String GetDefaultType();
}
