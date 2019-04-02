import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class - parsing command arguments
 */
public class ParseCmd {
    private enum cmdArguments {INPUT, OUTPUT, CONFIG};

    private Map<String, String> cmdArgs = new HashMap<>();
    private Map<cmdArguments, String> cmdKeys = new HashMap<>();
    private ArrayList<String> keys = new ArrayList<>();

    private int NUM_ARGS = 6;
    private int STEP = 2;

    /**
     * initialization of keys of command arguments
     */
    ParseCmd() {
        cmdKeys.put(cmdArguments.INPUT, "input:");
        cmdKeys.put(cmdArguments.OUTPUT, "output:");
        cmdKeys.put(cmdArguments.CONFIG, "config:");

        keys.add(cmdKeys.get(cmdArguments.INPUT));
        keys.add(cmdKeys.get(cmdArguments.OUTPUT));
        keys.add(cmdKeys.get(cmdArguments.CONFIG));
    }

    /**
     * initialize map of command arguments by key-value
     *
     * @param Args array of strings, consisting keys and values
     * @throws ParseCmdExeption if cmd args is not correct
     */

    public void Parse(String[] Args) throws ParseCmdExeption {
        if (Args.length != NUM_ARGS) {
            throw new ParseCmdExeption("Incorrect number of command arguments");
        }

         for (int i = 0; i < Args.length; i += STEP) {
            if (keys.contains(Args[i]))
                cmdArgs.put(Args[i], Args[i + 1]);
        }
        if (cmdArgs.size() != NUM_ARGS / STEP) {
            throw new ParseCmdExeption("Incorrect command arguments");
        }
    }

    /**
     * get name of input file
     *
     * @return file name
     */
    public String GetFInName() {

        return cmdArgs.get(cmdKeys.get(cmdArguments.INPUT));
    }

    /**
     * get name of output file
     *
     * @return file name
     */
    public String GetFOutName() {

        return cmdArgs.get(cmdKeys.get(cmdArguments.OUTPUT));
    }

    /**
     * get name of configuration file
     *
     * @return file name
     */
    public String GetFConfigName() {

        return cmdArgs.get(cmdKeys.get(cmdArguments.CONFIG));
    }
}

/**
 * Class of command arguments exception
 */
class ParseCmdExeption extends Exception {
    ParseCmdExeption(String message) {
        super(message);
    }
}