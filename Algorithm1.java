import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Algorithm1 - this is algorithm of conversion table
 */
public class Algorithm1 implements Algorithm<MyBytesArr>, Process {
    MyBytesArr inArr;
    MyBytesArr outArr;

    private final static String INNER_REGEX = "$";

    private ParseAlgorithm1Config confFile;
    private Table tabMap;

    static AlgTypesInfo typesAlg1 = new AlgTypesInfo();

    /**
     * Init types, witch algorithm can work
     */
    static {
        typesAlg1.outTypes = new String[2];
        typesAlg1.outTypes[0] = Types.types.MYBYTESARR.name();
        typesAlg1.outTypes[1] = Types.types.MYSTRING.name();
        typesAlg1.inTypes = Types.types.MYBYTESARR.name();
    }

    /**
     * Parse config file and initialize table
     *
     * @param fConfigName name of config file
     * @throws IOException
     * @throws ParseAlgorithm1ConfigException if configuration file is no correct
     * @throws TableException                 if table is not correct
     */
    private void CreateTableConfig(String fConfigName) throws IOException, ParseAlgorithm1ConfigException, TableException {
        confFile = new ParseAlgorithm1Config();
        confFile.RunParseAlgorithm1Config(fConfigName);

        tabMap = new Table(confFile.GetFTableName(), confFile.GetMode(), confFile.NumBytes());
        tabMap.makeTable();
    }

    /**
     * Class which convert MyBytes to MyString classes
     */
    class BytesToStr implements InnerClass<MyString> {
        @Override
        /**
         * return new MyString, converting MyBytesArr
         */
        public MyString ConvertType() {
            MyString tmp = new MyString();
            for (int i = 0; i < outArr.Size(); i++) {
                tmp.SetNextChar((char) outArr.Get(i));
            }
            return tmp;
        }
    }

    /**
     * Class which convert MyBytesArr to MyBytesArr classes
     */
    class BytesToBytes implements InnerClass<MyBytesArr> {
        @Override
        /**
         * return new MyBytesArr
         */
        public MyBytesArr ConvertType() {
            return outArr;
        }
    }

    /**
     * Init class
     *
     * @param fConfigName configuration file name
     * @param prevAlg     previous algorithm
     * @throws Algorithm2Exeption        if there is a mistake in types
     *                                   reflection exception
     * @throws ClassNotFoundException    if inner class not found
     * @throws IllegalAccessException    if there is a mistake in creating class, or getting method using reflection
     * @throws InstantiationException    if there is a mistake if we try to create class using newInstance
     * @throws InvocationTargetException if there is a mistake if we try to get constructor or invoke method
     * @throws NoSuchMethodException     if method of class not found
     */
    public Algorithm1(String fConfigName, Algorithm prevAlg) throws
            IOException, ParseAlgorithm1ConfigException,
            TableException, Algorithm1Exeption,
            ClassNotFoundException, IllegalAccessException,
            InstantiationException, InvocationTargetException,
            NoSuchMethodException {
        if (!ComparingData(prevAlg)) {
            throw new Algorithm1Exeption("Error types");
        }

        String classKey = prevAlg.GetDefaultType() + typesAlg1.inTypes;
        String className = Types.typesClasses.get(classKey);

        Class<?> clazz = Class.forName(prevAlg.getClass().getName() + INNER_REGEX + className);
        Constructor<?> ctorPrevAlg = clazz.getDeclaredConstructor(prevAlg.getClass());

        InnerClass c = (InnerClass) ctorPrevAlg.newInstance(prevAlg);
        inArr = (MyBytesArr) c.ConvertType();
        outArr = new MyBytesArr(inArr.Size());
        CreateTableConfig(fConfigName);
    }

    /**
     * Initialization, usinf input file
     *
     * @param fConfigName config file name
     * @param fInName     input file name
     * @throws IOException                    if input file not found
     * @throws ParseAlgorithm1ConfigException if configuration file of this algorithm is not correct
     * @throws TableException                 if table is not correct
     */
    public Algorithm1(String fConfigName, String fInName) throws IOException, ParseAlgorithm1ConfigException, TableException {
        FileInputStream fIn = new FileInputStream(fInName);
        inArr = new MyBytesArr(fIn);
        outArr = new MyBytesArr(inArr.Size());
        CreateTableConfig(fConfigName);
    }

    @Override
    public void Run() {
        int sizeBytesArr = inArr.Size() / confFile.NumBytes();
        int sizeRestBytesArr = inArr.Size() % confFile.NumBytes();
        int iByteCur = 0, iByte;

        for (int i = 0; i < sizeBytesArr; i++) {
            byte[] inArrBytes = new byte[confFile.NumBytes()];
            for (iByte = 0; iByte < confFile.NumBytes(); iByte++)
                inArrBytes[iByte] = inArr.Get(iByte + iByteCur);

            byte[] outArrBytes = tabMap.getValue(inArrBytes);
            for (iByte = 0; iByte < confFile.NumBytes(); iByte++)
                outArr.Set(iByte + iByteCur, outArrBytes[iByte]);

            iByteCur += iByte;
        }

        if (sizeRestBytesArr != 0) {
            for (iByte = iByteCur; iByte < inArr.Size(); iByte++)
                outArr.Set(iByte, inArr.Get(iByte));
        }
    }

    public MyBytesArr Result() {
        return outArr;
    }

    @Override
    public boolean ComparingData(Algorithm prev) {
        for (String prevType : prev.GetOutTypeData())
            if (typesAlg1.inTypes == prevType) {
                return true;
            }
        return false;
    }

    @Override
    public String[] GetOutTypeData() {
        return typesAlg1.outTypes;
    }

    @Override
    public void Write(String fNameOut) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fNameOut);
        for (int iBytes = 0; iBytes < outArr.Size(); iBytes++) {
            fileOut.write(outArr.Get(iBytes));
        }
    }

    @Override
    public String GetDefaultType() {
        return typesAlg1.inTypes;
    }

}

/**
 * Class of Algorithm1 exception
 */
class Algorithm1Exeption extends Exception {
    Algorithm1Exeption(String message) {
        super(message);
    }
}
