import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Algorithm2 - algorithm, which do nothing. Output data is the same, that Input data.
 **/

public class Algorithm2 implements Algorithm<MyString>, Process {
    private MyString inStr;
    private MyString outStr;

    private final static String INNER_REGEX = "$";

    static AlgTypesInfo typesAlg2 = new AlgTypesInfo();
    /**
     * Init types, which algorithm can work
     */
    static {
        typesAlg2.outTypes = new String[2];
        typesAlg2.outTypes[0] = Types.types.MYBYTESARR.name();
        typesAlg2.outTypes[1] = Types.types.MYSTRING.name();
        typesAlg2.inTypes = Types.types.MYSTRING.name();
    }

    /**
     * Class which convert MyString to MyBytesArr classes
     */
    class StrToBytes implements InnerClass<MyBytesArr> {
        @Override
        /**
         * return new MyBytesArr, converting MyString
         */
        public MyBytesArr ConvertType() throws UnsupportedEncodingException {
            MyBytesArr tmp = new MyBytesArr(outStr.Size());
            for (int i = 0; i < outStr.Size(); i++)
                tmp.Set(i, outStr.Get().getBytes("UTF-8")[i]);
            return tmp;
        }
    }
    /**
     * Class which convert MyString to MyString classes
     */
    class StrToStr implements InnerClass<MyString> {
        @Override
        /**
         * return MyString
         */
        public MyString ConvertType() {
            return outStr;
        }
    }

    /**
     * Init class
     * @param fConfigName configuration file name
     * @param prevAlg previous algorithm
     * @throws Algorithm2Exeption if there is a mistake in types
     * reflection exception
     * @throws ClassNotFoundException if inner class not found
     * @throws IllegalAccessException if there is a mistake in creating class, or getting method using reflection
     * @throws InstantiationException if there is a mistake if we try to create class using newInstance
     * @throws InvocationTargetException if there is a mistake if we try to get constructor or invoke method
     * @throws NoSuchMethodException if method of class not found
     * @throws UnsupportedEncodingException if the character encoding not support
     */
    public Algorithm2(String fConfigName, Algorithm prevAlg) throws Algorithm2Exeption, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException {
        if (!ComparingData(prevAlg)) {
            throw new Algorithm2Exeption("Error types");
        }

        String classKey = prevAlg.GetDefaultType() + typesAlg2.inTypes;
        String className = Types.typesClasses.get(classKey);

        Class<?> clazz = Class.forName(prevAlg.getClass().getName() + INNER_REGEX + className);
        Constructor<?> ctor = clazz.getDeclaredConstructor(prevAlg.getClass());

        InnerClass c = (InnerClass) ctor.newInstance(prevAlg);
        inStr = (MyString) c.ConvertType();
        outStr = new MyString();
    }

    /**
     * Initialization, using input file
     *
     * @param fConfigName config file name
     * @param fInName     input file name
     * @throws IOException if input file not found
     * @throws ParseAlgorithm1ConfigException
     * @throws TableException
     */
    public Algorithm2(String fConfigName, String fInName) throws IOException, ParseAlgorithm1ConfigException, TableException {
        inStr = new MyString(fInName);
        outStr = new MyString();
    }

    @Override
    public void Run() {
        outStr = inStr;
    }

    public MyString Result() {
        return outStr;
    }

    @Override
    public boolean ComparingData(Algorithm prev) {
        for (String prevType : prev.GetOutTypeData())
            if (Objects.equals(typesAlg2.inTypes, prevType)) {
                return true;
            }
        return false;
    }

    @Override
    public String[] GetOutTypeData() {
        return typesAlg2.outTypes;
    }

    @Override
    public void Write(String fNameOut) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fNameOut);
        fileOut.write(outStr.Get().getBytes());
    }

    @Override
    public String GetDefaultType() {
        return typesAlg2.inTypes;
    }

}

/**
 * Class of algorithm2 exception
 */
class Algorithm2Exeption extends Exception {
    Algorithm2Exeption(String message) {
        super(message);
    }
}