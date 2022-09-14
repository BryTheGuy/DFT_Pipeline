package edu.uoregon.hms;

import org.openbabel.OBBuilder;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class OutputGenerator {
    public void genGaussianFiles() throws FileNotFoundException { //FIXME
        try (PrintWriter pw = new PrintWriter("tempFileName.inp")) {
            try (InputStream in = getClass().getResourceAsStream("/gaussian_input_opt.txt")) {
                try {
                    assert in != null;
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                        reader.skip(52);
                        String line = reader.readLine();
                        while (line != null) {
                            pw.println(line);
                            line = reader.readLine();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void genStructure(String cml, String molName) {
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        OBBuilder builder = new OBBuilder();
        String fileName = molName + "/" + molName + ".gau";
        if(conv.SetInAndOutFormats("cml", "gau")) {
            conv.ReadString(mol, cml);
            mol.AddHydrogens();
            builder.Build(mol);
            conv.WriteFile(mol, Locator.getFilePath(fileName));
            conv.CloseOutFile();
        }
    }
}
