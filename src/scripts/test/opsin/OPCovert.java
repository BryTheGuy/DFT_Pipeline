package scripts.test.opsin;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

public class OPCovert {
    NameToStructure nts = NameToStructure.getInstance();
    NameToStructureConfig ntsConfig = new NameToStructureConfig();
    public void nameToCML() {
        nameToCML("Propanal");
    }

    public void nameToCML(String moleculeName) {
        OpsinResult result = nts.parseChemicalName(moleculeName, ntsConfig);
        String cml = result.getPrettyPrintedCml();
        String smiles = result.getSmiles();
        System.out.println("SMILES: " + smiles);
        System.out.print("cml: ");
        System.out.println(cml);
    }

    public OPCovert()
    {
        ntsConfig.setDetailedFailureAnalysis(true);
        ntsConfig.isDetailedFailureAnalysis();
        ntsConfig.setInterpretAcidsWithoutTheWordAcid(true);
        ntsConfig.setAllowRadicals(true);
        ntsConfig.isAllowRadicals();
    }

    public static void main(String[] args) {
        System.out.println("Running scripts.test.opsin.OPConvert...");

        OPCovert test = new OPCovert();

        test.nameToCML();
    }
}
