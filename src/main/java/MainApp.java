/**
 * @since 07/02/15.
 */
public class MainApp {

    public static void main(String[] args) {
        if (args.length < 1) {
            printHelp();
        } else {
            String ssid = args[0];
            StKeys stKeys = new StKeys();
            stKeys.setStartYear(2005);
            stKeys.setEndYear(2015);
            stKeys.setEndCharsetLength(3);
            stKeys.setVerbosity(1);
            if (ssid.startsWith("save")) {
                stKeys.saveToDatabase();
            } else {
                stKeys.crack(ssid);
            }
        }
    }

    public static void printHelp() {
        System.out.println("StKeys <sessid>");

    }
}
