package edu.uoregon.hms;

import org.openbabel.OBMol;

public class Redox {
    public Redox() {
        System.loadLibrary("openbabel_java");
    }

    public OBMol increaseCharge(OBMol molInput) {

        OBMol mol = new OBMol(molInput); // make copy of molecule

        mol.SetTotalCharge(mol.GetTotalCharge() + 1); // Increase charge

        return mol; // Return new molecule
    }

    public OBMol increaseMultiplicity(OBMol molInput) {

        OBMol mol = new OBMol(molInput); // make copy of molecule

        mol.SetTotalSpinMultiplicity(mol.GetTotalSpinMultiplicity() + 1); //Increase multiplicity

        return mol; // Return new molecule
    }

    public OBMol addElectron(OBMol molInput) {

        OBMol mol = new OBMol(molInput); // make copy of molecule

        return decreaseCharge(increaseMultiplicity(mol));
    }

    public OBMol decreaseCharge(OBMol molInput) {

        OBMol mol = new OBMol(molInput); // make copy of molecule

        mol.SetTotalCharge(mol.GetTotalCharge() - 1); // decrease charge by one

        return mol; // return new molecule
    }

    public OBMol removeElectron(OBMol molInput) {

        OBMol mol = new OBMol(molInput);

        return increaseCharge(increaseMultiplicity(mol));
    }
}
