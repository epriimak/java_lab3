import java.util.HashMap;
import java.util.Map;

/**
 * Class, contains names of all types, which are in program
 */
class Types {
    enum types{MYSTRING, MYBYTESARR};
    final static Map<String, String>typesClasses = new HashMap<>();

    static{
        String t = types.MYSTRING.name() + types.MYBYTESARR.name();
        typesClasses.put(t, "StrToBytes");
        String t1 = types.MYBYTESARR.name() + types.MYBYTESARR.name();
        typesClasses.put(t1, "BytesToBytes");
        String t2 = types.MYBYTESARR.name() + types.MYSTRING.name();
        typesClasses.put(t2, "BytesToStr");
        String t3 = types.MYSTRING.name() + types.MYSTRING.name();
        typesClasses.put(t3, "StrToStr");
    }
}
