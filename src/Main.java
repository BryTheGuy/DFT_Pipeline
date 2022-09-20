import edu.uoregon.hms.*;
import org.jetbrains.annotations.NotNull;
import org.openbabel.OBMol;

import java.util.Iterator;

public class Main {

    public static void main(String @NotNull [] args) {
        System.out.println("Main running...");
        System.loadLibrary("openbabel_java");
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
        setup();
        FileInterpreter.readToList();
        FileInterpreter.checkLines();
//        FileInterpreter.checker();
        StructureOptimizer structureOptimizer = new StructureOptimizer();
        StructureConverter structureConverter = new StructureConverter();
        for (String name : Settings.getStringMoleculeNames()) {
            String smi = NameConverter.nameToSmi(name);
            if (smi != null) {
//                OBMol mol = structureConverter.fromSmilesToMol(smi);
//                assert mol != null;
//                System.out.println(mol);
//                OBMol molOpt = structureOptimizer.molConjugateGradient(mol);
//                String molString = structureConverter.fromMolToFormat(molOpt, "xyz");
//                System.out.println(molString);
            }
        }
    }
    private static void setup() {
        Settings.setLineLengthOfInterest();
        Settings.setTextBlockEnds();
        Settings.setWhiteTillText();
        Settings.setLinesInFileHeader();
    }
}