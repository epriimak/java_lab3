import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for parsing config file, which is for Algorithm1
 */
public class ParseAlgorithm1Config {
    public enum mode {C, D}
    private enum config {MODE, NUM, TABLE}

    private mode realMode;
    private String fTableName;
    private int numBytes;

    private Map<config, String> configKeys = new HashMap<>();
    private Map<String, String> configArgs = new HashMap<>();

    private int SIZE = 3;
    private String REGEX = ":";

    /**
     * initialization of keys
     */
    ParseAlgorithm1Config() {
        configKeys.put(config.MODE, "mode");
        configKeys.put(config.NUM, "num");
        configKeys.put(config.TABLE, "table");
    }

    /**
     * set mode using enum
     *
     * @param strMode stringMode
     * @throws ParseAlgorithm1ConfigException if types in algorithm not suitable
     */
    private void SetMode(String strMode) throws ParseAlgorithm1ConfigException {
        if (strMode.equals("code"))
            realMode = mode.C;
        else if (strMode.equals("decode"))
            realMode = mode.D;
        else
            throw new ParseAlgorithm1ConfigException("Invalidate mode");
    }

    /**
     * parsing configuration file
     *
     * @param fName file name
     * @throws IOException if configuration file of algorithm not found
     * @throws ParseAlgorithm1ConfigException if configuration file of algorithm is not  correct
     */
    public void RunParseAlgorithm1Config(String fName) throws IOException, ParseAlgorithm1ConfigException {
        BufferedReader reader = new BufferedReader(new FileReader(fName));
        String str;

        while ((str = reader.readLine()) != null) {
            String[] parts = str.split(REGEX);
            configArgs.put(parts[0].replaceAll("\\s*", ""), parts[1].replaceAll("\\s*", ""));

            if(configKeys.containsValue(parts[0].replaceAll("\\s*", "")) == false){
                throw new ParseAlgorithm1ConfigException("Incorrect config files");
            }
        }

        if (configArgs.size() != SIZE) {
            throw new ParseAlgorithm1ConfigException("Incorrect configuration file");
        }

        fTableName = configArgs.get(configKeys.get(config.TABLE));
        if (!fTableName.contains("txt")) {
            throw new ParseAlgorithm1ConfigException("Incorrect table name");
        }

        numBytes = Integer.parseInt(configArgs.get(configKeys.get(config.NUM)));
        if (numBytes <= 0) {
            throw new ParseAlgorithm1ConfigException("Wrong number of code bytes");
        }

        SetMode(configArgs.get(configKeys.get(config.MODE)));
    }

    /**
     * get mode
     *
     * @return mode
     */
    public mode GetMode() {
        return realMode;
    }

    /**
     * get number of bytes for encoding
     *
     * @return number of bytes
     */
    public int NumBytes() {
        return numBytes;
    }

    /**
     * get name of byte table
     *
     * @return file name
     */
    public String GetFTableName() {
        return fTableName;
    }
}

/**
 * Class exception of parsing configuration file of Algorithm1
 */
class ParseAlgorithm1ConfigException extends Exception {
    ParseAlgorithm1ConfigException(String message) {
        super(message);
    }
}

