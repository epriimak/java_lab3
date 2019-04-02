import java.io.FileInputStream;
import java.io.IOException;

/**
 * Class of BytesArr
 */
public class MyBytesArr {
    private byte[] MyBytesArr;

    /**
     * Create new BytesArr
     * @param size size
     */
    MyBytesArr(int size){
        MyBytesArr = new byte[size];
    }

    /**
     * Create new BytesArr reading from file
     * @param fIn name if input file
     * @throws IOException if input file not found
     */
    MyBytesArr(FileInputStream fIn) throws IOException {
        MyBytesArr = new byte[fIn.available()];
        fIn.read(MyBytesArr);
    }

    /**
     * Get size of BytesArr
     * @return size
     */
    public int Size() {
        return MyBytesArr.length;
    }

    /**
     * Get exact byte, which has index i
     * @param i index
     * @return byte
     */
    byte Get(int i) {
        return MyBytesArr[i];
    }

    /**
     * Set byte, which has index i, by byte b
     * @param i index
     * @param b byte
     */
    void Set(int i, byte b) {
        MyBytesArr[i] = b;
    }

}
