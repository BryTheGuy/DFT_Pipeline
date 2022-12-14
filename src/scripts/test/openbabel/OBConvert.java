package scripts.test.openbabel;

import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

public class OBConvert {

    public OBConvert()
    {
        System.loadLibrary("openbabel_java");
    }

    public void run()
    {
        run("CN1C=NC2=C1C(=O)N(C(=O)N2C)C"); // Caffeine
    }

    public void run(String smi)
    {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        if (conv.SetInAndOutFormats("smi", "xyz")) {
            conv.ReadString(mol, smi);
            mol.AddHydrogens();
            builder.Build(mol);
            mol.Center();
            System.out.println(conv.WriteString(mol));
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Running scripts.test.openbabel.OBConvert...");
        OBConvert test = new OBConvert();
        test.run();
    }
}
