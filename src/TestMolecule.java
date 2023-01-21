public class TestMolecule {
    public static void main(String[] args) {

        Molecule molecule = new Molecule("caffeine");

        molecule.genSmi();
        molecule.genCml();

        try {
            molecule.genStructure("xyz");
        } catch (Exception e) {
            e.printStackTrace();
        }

        molecule.setFormat("gau");

        try {
            molecule.genStructure();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            molecule.genMol();
        } catch (Exception e) {
            e.printStackTrace();
        }

        molecule.makeFile("opt");

        System.out.println("The SMILES is: " + molecule.getSmi());
        System.out.print("The CML is: ");
        System.out.println(molecule.getCml());
        System.out.println("The structure in " + molecule.getFormat() + " format.");
        System.out.println(molecule.getStructure());
        System.out.println("OBMol object: " + molecule.getMol());
    }
}
