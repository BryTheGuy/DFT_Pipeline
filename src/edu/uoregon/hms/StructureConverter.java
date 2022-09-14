package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

public class StructureConverter {

    public static OBMol fromCmlToMol(@NotNull String cml) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        if (conv.SetInFormat("cml")) {
            conv.ReadString(mol, cml);
            builder.Build(mol);
            mol.Center();
            return mol;
        }
        return null;
    }

    public static OBMol fromSmilesToMol(@NotNull String smi) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        if (conv.SetInFormat("smi")) {
            conv.ReadString(mol, smi);
            mol.AddHydrogens();
            builder.Build(mol);
            mol.Center();
            return mol;
        }
        return null;
    }

    public static @Nullable String fromSmilesToFormat(@NotNull String smi, @NotNull String outFormat) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        if (conv.SetInAndOutFormats("smi", outFormat)) {
            conv.ReadString(mol, smi);
            mol.AddHydrogens();
            builder.Build(mol);
            mol.Center();
            return conv.WriteString(mol);
        }
        return null;
    }

    public static @Nullable String fromCmlToFormat(@NotNull String cml, @NotNull String outFormat) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        if (conv.SetInAndOutFormats("cml", outFormat)) {
            conv.ReadString(mol, cml);
            mol.AddHydrogens();
            builder.Build(mol);
            mol.Center();
            return conv.WriteString(mol);
        }
        return null;
    }
}
