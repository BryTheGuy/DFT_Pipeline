package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;
import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

public class StructureConverter {

    public OBMol fromCml(@NotNull String cml, @NotNull String outFormat) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        if (conv.SetInAndOutFormats("cml", outFormat)) {
            conv.ReadString(mol, cml);
            builder.Build(mol);
            mol.Center();
            return mol;
        }
        return null;
    }

    public OBMol fromSMILES(@NotNull String smi, @NotNull String outFormat) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        if (conv.SetInAndOutFormats("smi", outFormat)) {
            conv.ReadString(mol, smi);
            mol.AddHydrogens();
            builder.Build(mol);
            mol.Center();
            return mol;
        }
        return null;
    }
}
