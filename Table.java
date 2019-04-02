import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class - create Table, using in convertable table algorithm
 */
public class Table {
    private FileInputStream fileTab;
    private ParseAlgorithm1Config.mode flagMode;
    private String REGEX;
    private HashMap<Integer, byte[]> tabMap = new HashMap<>();
    private int num;
    /**
     * initialize class table
     *
     * @param fName name of table file
     * @param fCode mode
     */
    Table(String fName, ParseAlgorithm1Config.mode fCode, int numBytes) {
        try {
            fileTab = new FileInputStream(fName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        flagMode = fCode;
        REGEX = " ";
        num = numBytes;
    }

    /**
     * create table from file
     *
     * @throws IOException if file that contains table not found
     * @throws TableException if table is not correct
     */
    public void makeTable() throws IOException, TableException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fileTab));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            String[] parts = strLine.split(REGEX);
            if (parts[0].length() != parts[1].length() || parts[0].length() != num) {
                throw new TableException("Incorrect data in table");
            }

            if (flagMode == ParseAlgorithm1Config.mode.C) {
                if (tabMap.containsKey(Arrays.hashCode(parts[0].getBytes())) || tabMap.containsValue(parts[1].getBytes())) {
                    throw new TableException("Incorrect table");
                }
                tabMap.put(Arrays.hashCode(parts[0].getBytes()), parts[1].getBytes());

            } else {
                if(tabMap.containsKey(Arrays.hashCode(parts[1].getBytes())) || tabMap.containsValue(parts[0].getBytes())){
                    throw new TableException("Incorrect table");
                }
                tabMap.put(Arrays.hashCode(parts[1].getBytes()), parts[0].getBytes());
            }
        }

        for (Integer i : tabMap.keySet()) {
            byte[] arBytes = tabMap.get(i);
            if (!tabMap.containsKey(Arrays.hashCode(arBytes))) {
                throw new TableException("Incorrect table");
            }
        }
    }

    /**
     * encoding array of bytes
     *
     * @param bytes input array of bytes
     * @return coded array of bytes
     */
    public byte[] getValue(byte[] bytes) {
        byte[] value = tabMap.get(Arrays.hashCode(bytes));
        if (value == null)
            return bytes;
        return value;
    }
}

/**
 * Class of table exception
 */
class TableException extends Exception {
    TableException(String message) {
        super(message);
    }
}



