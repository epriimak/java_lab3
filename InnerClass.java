import java.io.UnsupportedEncodingException;

/**
 * Interface for all converting classes
 * @param <T> type, which function "ConvertType" convert to
 */
public interface InnerClass<T> {
    /**
     * Converting data
     * @return data in new type
     * @throws UnsupportedEncodingException if character not support
     */
    T ConvertType() throws UnsupportedEncodingException;
}
