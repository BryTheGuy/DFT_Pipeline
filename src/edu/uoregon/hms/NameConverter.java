package edu.uoregon.hms;

// Open babel https://openbabel.org/api/2.3/
// opsin https://www.javadoc.io/doc/uk.ac.cam.ch.opsin/opsin-core/latest/uk/ac/cam/ch/wwmm/opsin/package-summary.html

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

import java.util.ArrayList;

/**
 * @author      Brycen Falzone <brycenf@uoregon.edu>
 * @version     0.3                (current version number of program)
 * @since       0.3          (the version of the package this class was first added to)
 */
public class NameConverter {
    static NameToStructureConfig ntsConfig = new NameToStructureConfig();
    static ArrayList<String> failedNames = new ArrayList<>();


    public static String toCml(String molName) {
        NameToStructure nts = NameToStructure.getInstance();
        NameToStructureConfig ntsConfig = new NameToStructureConfig();
        //a new NameToStructureConfig starts as a copy of OPSIN default configuration
        ntsConfig.setAllowRadicals(true);
        ntsConfig.setDetailedFailureAnalysis(true);
        ntsConfig.setInterpretAcidsWithoutTheWordAcid(true);
        ntsConfig.setWarnRatherThanFailOnUninterpretableStereochemistry(true);
        OpsinResult result = nts.parseChemicalName(molName, ntsConfig); // TODO: Add better error catching to chemical name parsing
        return result.getCml(); // TODO: What if parse returns "null"?
    }


    public static String nameToCML(String moleculeName) {
        NameToStructure nts = NameToStructure.getInstance();
        OpsinResult result = nts.parseChemicalName(moleculeName, ntsConfig);
        String output = result.getPrettyPrintedCml();
        if (output == null) {
            failedNames.add(moleculeName);
//            System.out.println(moleculeName);
        }
        return output;
    }

    public static String nameToSmi(String moleculeName) {
        NameToStructure nts = NameToStructure.getInstance();
        OpsinResult result = nts.parseChemicalName(moleculeName, ntsConfig);
        String output = result.getSmiles();
        if (output == null) {
            failedNames.add(moleculeName);
//            System.out.println(moleculeName);
        }
        return output;
    }

    public NameConverter()
    {
        ntsConfig.setDetailedFailureAnalysis(true);
        ntsConfig.isDetailedFailureAnalysis();
        ntsConfig.setInterpretAcidsWithoutTheWordAcid(true);
        ntsConfig.setAllowRadicals(true);
        ntsConfig.isAllowRadicals();
    }
}
