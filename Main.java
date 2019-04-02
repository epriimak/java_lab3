import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Main class contains main function
 */
public class Main {
    /**
     * Do pipeline
     *
     * @param args arguments of cmd
     */
    public static void main(String[] args) {
        try {
            Controller c = new Controller(args);
            c.PipeLine();
        } catch (ParseCmdExeption | //command arguments mistake
                IllegalAccessException | //reflection mistake
                InstantiationException |
                NoSuchMethodException |
                InvocationTargetException |
                ClassNotFoundException |
                IOException | // mistake, appear when we try ro read/write from/in file
                ParseConfigExeption //configuration file mistake
                e) {
            System.out.println(e.getCause().toString());
            e.printStackTrace();
        }
    }

}
