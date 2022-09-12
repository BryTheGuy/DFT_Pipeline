package edu.uoregon.hms;

import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

public class StructureConverter {

    public String fromCml(String cml, String outFormat) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        if (conv.SetInAndOutFormats("cml", outFormat)) {
            conv.ReadString(mol, cml);
            builder.Build(mol);
            mol.Center();
            return conv.WriteString(mol);
        }
        return null;
    }

    public String fromSMILES(String smi, String outFormat) {
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
}
