package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;
import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBForceField;
import org.openbabel.OBMol;

public class StructureOptimizer {

    public StructureOptimizer()
    {
        System.loadLibrary("openbabel_java");
    }

    public OBMol xyzConjugateGradient(@NotNull String xyz) {
        return xyzConjugateGradient(xyz, 1000);
    }

    public OBMol xyzConjugateGradient(@NotNull String xyz, int gradient) {
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
        GAFF.ConjugateGradients(gradient);
        mol.Center();
        return mol;
    }

    public OBMol cmlConjugateGradient(@NotNull String cml) {
        return cmlConjugateGradient(cml, 1000);
    }

    public OBMol cmlConjugateGradient(@NotNull String cml, int gradient) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        conv.SetInFormat("cml");
        conv.ReadString(mol, cml);
        OBForceField GAFF = OBForceField.FindForceField("GAFF");
        if (!GAFF.SetupPointers()) {
            System.err.println("ERROR: could not setup pointers.");
        }
        if (!GAFF.Setup(mol)) {
            System.err.println("ERROR: could not setup force field.");
        }
        GAFF.ConjugateGradients(gradient);
        mol.Center();
        return mol;
    }

    public OBMol gauConjugateGradient(@NotNull String gau) {
        return gauConjugateGradient(gau, 1000);
    }

    public OBMol gauConjugateGradient(@NotNull String gau, int gradient) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        conv.SetInFormat("gau");
        conv.ReadString(mol, gau);
        builder.Build(mol);
        OBForceField GAFF = OBForceField.FindForceField("GAFF");
        if (!GAFF.SetupPointers()) {
            System.err.println("ERROR: could not setup pointers.");
        }
        if (!GAFF.Setup(mol)) {
            System.err.println("ERROR: could not setup force field.");
        }
        GAFF.ConjugateGradients(gradient);
        mol.Center();
        return mol;
    }

    public OBMol molConjugateGradient(OBMol mol) {
        return molConjugateGradient(mol, 1000);
    }

    public OBMol molConjugateGradient(OBMol mol, int gradient) {
//        OBBuilder builder = new OBBuilder();
//        builder.Build(mol);
        OBForceField GAFF = OBForceField.FindForceField("GAFF");
        if (!GAFF.SetupPointers()) {
            System.err.println("ERROR: could not setup pointers.");
        }
        if (!GAFF.Setup(mol)) {
            System.err.println("ERROR: could not setup force field.");
        }
        GAFF.ConjugateGradients(gradient);
        mol.Center();
        return mol;
    }
}
