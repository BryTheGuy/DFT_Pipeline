package scripts.test.openbabel;

import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

import static java.lang.String.format;
import static org.openbabel.OBConversion.Option_type.OUTOPTIONS;

public class OBOption {
    public OBOption()
    {
        System.loadLibrary("openbabel_java");
    }

    public void run()
    {
        try {
            OBConversion c = new OBConversion();
            OBBuilder builder = new OBBuilder();
            OBMol mol = new OBMol();

            if (c.SetInAndOutFormats("smi", "gau")) {
                c.ReadString(mol, "c1ccccc1");
                mol.SetTitle("benzene");
                mol.AddHydrogens();
                builder.Build(mol);
                mol.Center();
            }

            String keywords = format("""
                    %%NProcShared=28
                    %%mem=50GB
                    %%chk=%s.chk
                                
                    #p PBEH1PBE/def2svp opt integral=superfinegrid freq""", mol.GetTitle());

            c.AddOption("k", OUTOPTIONS, keywords);

            System.out.println(c.WriteString(mol));

        } catch (Exception e) {
            System.err.println("Exception caught => " + e);
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Running scripts.test.openbabel.OBOption...");
        OBOption test = new OBOption();
        test.run();
    }
}
