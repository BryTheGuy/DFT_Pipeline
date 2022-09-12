package scripts.test.openbabel;

import org.openbabel.*;

public class OBConverge {
    public OBConverge()
    {
        System.loadLibrary("openbabel_java");
    }

    public void run() {
        run("""
                19

                O         -2.94019        0.79385       -0.81847
                C         -1.66929        0.79385       -0.81847
                C         -0.90929        0.02130        0.24734
                C         -0.40059       -0.91202        1.17740
                C          1.05619       -0.59545        1.49619
                C          1.91095       -0.47442        0.25105
                O          1.22471        0.37768       -0.81004
                C          0.41235       -0.31489        0.30157
                O         -0.78407        0.15948       -0.29838
                C         -0.73363        0.83082       -1.54864
                C          1.63265       -1.57540        2.34649
                H         -1.13429        1.33769       -1.56875
                H         -0.95707       -1.72370        1.59740
                H          1.43349       -0.47229        2.48985
                H          1.76743        0.81985       -1.52413
                H         -0.12465        1.70606       -1.45920
                H         -0.31510        0.17903       -2.28682
                H         -1.72311        1.11323       -1.84201
                H          2.25353       -2.06466        3.06761""");
    }

    public void run(String xyz) // Default is vanillin
    {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        conv.SetInAndOutFormats("xyz", "xyz");
        conv.ReadString(mol, xyz);
        builder.Build(mol);
        OBForceField GAFF = OBForceField.FindForceField("GAFF");
        if (!GAFF.SetupPointers()) {
            System.err.println("ERROR: could not setup pointers.");
        }
        if (!GAFF.Setup(mol)) {
            System.err.println("ERROR: could not setup force field.");
        }
        GAFF.ConjugateGradients(1000);
        mol.Center();
        System.out.println(conv.WriteString(mol));
        System.out.println("done");
    }

    public static void main(String[] args)
    {
        System.out.println("Running scripts.test.openbabel.OBConverge...");
        OBConverge test = new OBConverge();
        test.run();
    }
}
