import edu.uoregon.hms.Settings;

public class TestMolecule {

    public static void run(String moleculeName) {
        // Set up Molecule
        Molecule molecule = new Molecule(moleculeName);

        molecule.setNameNoSpace();
        molecule.genSmi();
        molecule.genCml();

        // Set up Settings

        Settings.setFunctional("B3LYP");
        Settings.setBasisSet("6-311G");

//        try {
//            molecule.genStructure("xyz");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        molecule.setFormat("gau");
//
//        try {
//            molecule.genStructure();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            molecule.genMol();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        molecule.genFile("opt");

//        molecule.genDirs();

//        molecule.genAllFiles();

//        molecule.copyPy();

        System.out.println("Molecule name: " + moleculeName);
        System.out.println("The SMILES is: " + molecule.getSmi());
//        System.out.print("The CML is: ");
//        System.out.println(molecule.getCml());
//        System.out.println("The structure in " + molecule.getFormat() + " format.");
//        System.out.println(molecule.getStructure());
//        System.out.println("OBMol object: " + molecule.getMol());

//        molecule.defaultRun();
    }
    public static void main(String[] args) {

        run("caffeine");
        run("2,4,6-trinitrotoluene");

    }
}
