import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * Controller - controls algorithms in the pipeline
 */
public class Controller {
    private String fInName;
    private String fConfigName;
    private String fOutName;
    private ParseConfig configFile = new ParseConfig();
    private ArrayList<Map<ParseConfig.algInfo, String>> infoAlgs;
    private final String RUN = "Run";
    private final String WRITE = "Write";
    /**
     * Initialisation
     * @param args array of string from command line
     * @throws IOException if configuration file of conveyer not found
     * @throws ParseConfigExeption if config file is not correct
     * @throws ParseCmdExeption if arguments of cmd args are not correct
     */
    Controller(String args[]) throws IOException, ParseConfigExeption, ParseCmdExeption {
        ParseCmd cmdArgs = new ParseCmd();
        cmdArgs.Parse(args);

        fInName = cmdArgs.GetFInName();
        fConfigName = cmdArgs.GetFConfigName();
        fOutName = cmdArgs.GetFOutName();
        configFile.Parse(fConfigName);
        infoAlgs = configFile.GetArrAlg();
    }

    /**
     * Running PipeLine
     * @throws ClassNotFoundException if we can't create algorithm class, using reflection
     * @throws IllegalAccessException if there is a mistake in creating class, or getting method using reflection
     * @throws InstantiationException if there is a mistake if we try to create class using newInstance
     * @throws InvocationTargetException if there is a mistake if we try to get constructor or invoke method
     * @throws NoSuchMethodException if method of class not found
     * @throws IOException if input file not found
     */
    void PipeLine() throws
            ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            InstantiationException, NoSuchMethodException, IOException {
        Algorithm curAlg;
        Algorithm prevAlg = null;

        for (int i = 0; i < infoAlgs.size(); ++i) {
            String name = infoAlgs.get(i).get(ParseConfig.algInfo.NAME);
            String config = infoAlgs.get(i).get(ParseConfig.algInfo.CONFIG);
            Class myClass = Class.forName(name);

            if (i == 0) {
                Constructor cons = myClass.getConstructor(String.class, String.class);
                curAlg = (Algorithm) cons.newInstance(config, fInName);
            } else {
                Constructor cons = myClass.getConstructor(String.class, Algorithm.class);
                curAlg = (Algorithm) cons.newInstance(config, prevAlg);
            }
            Method run = myClass.getMethod(RUN);
            run.invoke(curAlg);
            prevAlg = curAlg;

            if (i == infoAlgs.size() - 1) {
                Method write = myClass.getMethod(WRITE, String.class);
                write.invoke(curAlg, fOutName);
            }
        }
    }
}

