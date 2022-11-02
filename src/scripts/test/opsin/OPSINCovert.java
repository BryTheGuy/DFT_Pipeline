package scripts.test.opsin;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

public class OPSINCovert {
    NameToStructure nts = NameToStructure.getInstance();
    NameToStructureConfig ntsConfig = new NameToStructureConfig();
    public void nameToCML() {
        nameToCML("Propanal");
    }

    public void nameToCML(String moleculeName) {
        OpsinResult result = nts.parseChemicalName(moleculeName, ntsConfig);
        String cml = result.getPrettyPrintedCml();
        System.out.print("cml: ");
        System.out.println(cml);
    }

    public void nameToSmiles() {
        nameToSmiles("Propanal");
    }

    public void nameToSmiles(String moleculeName) {
        OpsinResult result = nts.parseChemicalName(moleculeName, ntsConfig);
        String smiles = result.getSmiles();
        String extendedSmiles = result.getExtendedSmiles();
        System.out.println("SMILES: ");
        System.out.println(smiles);
        System.out.println("Extended SMILES: ");
        System.out.println(extendedSmiles);
    }

    public OPSINCovert()
    {
        ntsConfig.setDetailedFailureAnalysis(true);
        ntsConfig.isDetailedFailureAnalysis();
        ntsConfig.setInterpretAcidsWithoutTheWordAcid(true);
        ntsConfig.setAllowRadicals(true);
        ntsConfig.isAllowRadicals();
    }

    public static void main(String[] args) {
        System.out.println("Running scripts.test.opsin.OPConvert...");

        OPSINCovert test = new OPSINCovert();

        test.nameToSmiles();
        test.nameToCML();
    }
}
