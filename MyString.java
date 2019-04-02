import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class MyString, contains String
 */
public class MyString {
    private String MyString;

    /**
     * Init string
     */
    MyString(){
        MyString = new String();
    }

    /**
     * Init string reading file
     * @param fName name of input file
     * @throws IOException if input file not found
     */
    MyString(String fName) throws IOException {
        MyString = new String(Files.readAllBytes(Paths.get(fName)));
    }

    /**
     * Set next element of string by char symbol
     * @param c
     */
    public void SetNextChar(char c){
        MyString += c;
    }

    /**
     * Get string
     * @return string
     */
    public String Get(){
        return MyString;
    }

    /**
     * Get size of string
     * @return size
     */
    public int Size(){
        return MyString.length();
    }
}
