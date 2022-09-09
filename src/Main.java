import edu.uoregon.hms.FileInterpreter;
import edu.uoregon.hms.Locator;
import edu.uoregon.hms.Settings;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        setup();
        System.out.println("Main running...");
        // Looping through arg inputs looking for -f keyword for the file name
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-f")) {
                if (args[i + 1] != null && !args[i + 1].isBlank()) {
                    Locator.setFileName(args[i + 1]);
                } else {
                    throw new RuntimeException("Expected file after '-f' got: " + args[i + 1]);
                }
            }else if (args[i].equals("-v")) {
                System.out.println("Version @version");
            }
        }
        FileInterpreter.readToList();
        FileInterpreter.checkLines();
        FileInterpreter.checker();
    }
    private static void setup() {
        Settings.setLineLengthOfInterest();
        Settings.setTextBlockEnds();
        Settings.setWhiteTillText();
        Settings.setLinesInFileHeader();
    }
}