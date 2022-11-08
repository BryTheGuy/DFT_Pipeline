package edu.uoregon.hms;

import org.openbabel.OBMol;

public class Redox {
    // TODO: make copy of mol
    // TODO: get charge and multiplicity
    // TODO: increase charge on copy (maybe opt) write to file or whatever
    public static void increaseCharge(OBMol mol) {
        OBMol mol1 = mol;
        int charge = mol1.GetTotalCharge();
        long multiplicity = mol1.GetTotalSpinMultiplicity();
        mol1.SetTotalCharge(char);
    }
}
