package scripts.test.openbabel;

import org.jetbrains.annotations.TestOnly;
import org.openbabel.*;

@TestOnly
public class OBTest {
 public OBTest()
 {
   System.loadLibrary("openbabel_java");
 }

 public void run()
 {
   OBConversion c = new OBConversion();
   OBMol mol = new OBMol();

   c.SetInFormat("smi");
   c.ReadString(mol, "c1ccccc1");

   System.out.println("Benzene has " + mol.NumAtoms() + " atoms.");
 }

 public static void main(String[] args)
 {
   System.out.println("Running scripts.test.openbabel.OBTest...");

   OBTest test = new OBTest();

   test.run();
 }
}