package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

public class StructureConverter {
    public StructureConverter()
    {
        System.loadLibrary("openbabel_java");
    }

    public OBMol fromCmlToMol(@NotNull String cml) {
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

    public OBMol fromSmilesToMol(@NotNull String smi) {
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

    public @Nullable String fromSmilesToFormat(@NotNull String smi, @NotNull String outFormat) {
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

    public  @Nullable String fromCmlToFormat(@NotNull String cml, @NotNull String outFormat) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        if (conv.SetInAndOutFormats("cml", outFormat)) {
            conv.ReadString(mol, cml);
            mol.AddHydrogens();
            // builder.Build(mol);
            // mol.Center();
            return conv.WriteString(mol);
        }
        return null;
    }

    public String fromMolToFormat(OBMol mol, @NotNull String outFormat) {
        OBConversion conv = new OBConversion();
//        OBBuilder builder = new OBBuilder();
//        builder.Build(mol);
        conv.SetOutFormat(outFormat);
        return conv.WriteString(mol);
    }

    public OBMol fromFormatToMol(String inFormat, String molString) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        conv.SetInFormat(inFormat);
        conv.ReadString(mol, molString);
        return mol;
    }
}
