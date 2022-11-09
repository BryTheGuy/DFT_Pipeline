package scripts.test.openbabel;

import org.jetbrains.annotations.TestOnly;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

@TestOnly
public class OBEdit {
    public OBEdit() {
        System.loadLibrary("openbabel_java");
    }

    public void run()
    {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();

        conv.SetInFormat("smi");
        conv.ReadString(mol, "c1ccccc1");

        edit(mol);
        System.out.println("Benzene has " + mol.NumAtoms() + " atoms.");
    }

    public void edit(OBMol mol)
    {
        OBMol newMol = new OBMol(mol);
        newMol.AddHydrogens();
        System.out.println("Benzene with hydrogen has " + newMol.NumAtoms() + " atoms.");
    }

    public static void main(String[] args) {
        System.out.println("Running scripts.test.openbabel.OBEdit...");

        OBEdit test = new OBEdit();

        test.run();
    }
}
