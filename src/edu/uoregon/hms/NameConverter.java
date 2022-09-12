package edu.uoregon.hms;

// Open babel https://openbabel.org/api/2.3/
import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;
// opsin https://www.javadoc.io/doc/uk.ac.cam.ch.opsin/opsin-core/latest/uk/ac/cam/ch/wwmm/opsin/package-summary.html
import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author      Brycen Falzone <brycenf@uoregon.edu>
 * @version     0.3                (current version number of program)
 * @since       0.3          (the version of the package this class was first added to)
 */
public class NameConverter {
    String cml;
    String Name_of_Molecule;

    public NameConverter(String molName) {
        Name_of_Molecule = molName;
    }

    /**
     *
     */
    public String toCml(String molName) {
        NameToStructure nts = NameToStructure.getInstance();
        NameToStructureConfig ntsConfig = new NameToStructureConfig(); // TODO: Figure out configs
        //a new NameToStructureConfig starts as a copy of OPSIN default configuration
        ntsConfig.setDetailedFailureAnalysis(true);
        /*
        Config settings might add need to check
        ntsConfig.setAllowRadicals(true);
        ntsConfig.setInterpretAcidsWithoutTheWordAcid(true);
        ntsConfig.setWarnRatherThanFailOnUninterpretableStereochemistry(true);
        */
        try {   // TODO: Add better error catching to chemical name parsing
            OpsinResult result = nts.parseChemicalName(molName, ntsConfig);
            return result.getCml(); // TODO: What if parser returns "null"?
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void genStructure(String cml, String molName) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        String fileName = molName + "/" + molName + ".gau";
        if(conv.SetInAndOutFormats("cml", "gau")) {
            conv.ReadString(mol, cml);
            mol.AddHydrogens();
            builder.Build(mol);
            conv.WriteFile(mol, Locator.getFilePath(fileName));
            conv.CloseOutFile();
        }
    }

    public void genGaussianFiles() throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter("tempFileName.inp")) {
            try (InputStream in = getClass().getResourceAsStream("/gaussian_input_opt.txt")) {
                try {
                    assert in != null;
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                        reader.skip(52);
                        String line = reader.readLine();
                        while (line != null) {
                            pw.println(line);
                            line = reader.readLine();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } // TODO: Name this better
    }
}
