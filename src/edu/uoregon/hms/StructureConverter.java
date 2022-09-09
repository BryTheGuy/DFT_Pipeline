package edu.uoregon.hms;

// Open babel

import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;
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
public class StructureConverter {
    String cml; // this could get very big. TODO: find better solution
    String Name_of_Molecule;

    public StructureConverter(String molName) {
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

/*
broken readFile()
    public static String readFile() {
        try {
            File molNames = new File("C:\\Users\\bryce\\Documents\\Hendon_Lab\\GCMS_Redox\\GC-MS_to_DFT\\MoleculeNameList.txt");
            Scanner myReader = new Scanner(molNames);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String molLine = myReader.nextLine();
                if (line == "*C:\\Database\\*") {
                    if (molLine.indexOf(" (CAS) ") != -1) {

                    }
                }

                return null;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
*/

/*
unused parse()
    public static void findMolecule() {
        String molName;
        try {
            File molNames = new File("C:\\Users\\bryce\\Documents\\Hendon_Lab\\GCMS_Redox\\GC-MS_to_DFT\\MoleculeNameList.txt");
            Scanner myReader = new Scanner(molNames);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.length() == 74) {
                    line = line.trim();
                    line = line.toLowerCase();
                    if (line.contains(" (cas)")) {
                        int endCAS = line.indexOf(" (cas)");
                        molName = line.substring(0, endCAS);
                    } else if (line.contains(" $$")) {
                        int end$$ = line.indexOf(" $$");
                        molName = line.substring(0, end$$);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
*/
}
