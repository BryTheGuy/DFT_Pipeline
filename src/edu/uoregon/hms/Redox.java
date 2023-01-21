package edu.uoregon.hms;

import org.openbabel.OBMol;

public class Redox {
    public Redox() {
        System.loadLibrary("openbabel_java");
    }

    public OBMol increaseCharge(OBMol molInput) {

        OBMol mol = new OBMol(molInput); // make copy of molecule
        StructureOptimizer opt = new StructureOptimizer();

        mol.SetTotalCharge(mol.GetTotalCharge() + 1); // Increase charge
        opt.molConjugateGradient(mol); // optimize new molecule

        return mol; // Return new molecule
    }
}
