package scripts.test.openbabel;

import org.jetbrains.annotations.TestOnly;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

@TestOnly
public class OBRedox {
    public OBRedox() {
        System.loadLibrary("openbabel_java");
    }

    public void run()
    {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();

        conv.SetInFormat("smi");
        conv.ReadString(mol, "c1ccccc1");

        int charge = mol.GetTotalCharge();
        long multiplicity = mol.GetTotalSpinMultiplicity();

        System.out.println("Benzene has a charge of " + charge);
        System.out.println("Benzene has a multiplicity of " + multiplicity);


        mol.SetTotalCharge(charge + 1);
        mol.SetTotalSpinMultiplicity(multiplicity + 1);

        int chargeNew = mol.GetTotalCharge();
        long multiplicityNew = mol.GetTotalSpinMultiplicity();

        System.out.println("Benzene now has a charge of " + chargeNew);
        System.out.println("Benzene now has a multiplicity of " + multiplicityNew);
    }

    public static void main(String[] args)
    {
        System.out.println("Running scripts.test.openbabel.OBRedox...");

        OBRedox test = new OBRedox();

        test.run();
    }
}
