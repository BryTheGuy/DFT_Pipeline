package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;
import org.openbabel.OBMol;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateFiles {
    public void jobType(OBMol mol, @NotNull String template) {
        Redox redox = new Redox();
        final Path inputSolvation = Paths.get("src/edu/uoregon/hms/resources/gaussian_input_solv");
        final Path inputOptimize = Paths.get("src/edu/uoregon/hms/resources/gaussian_input_opt");
        switch (template) {
            case "opt" -> makeFile(mol, inputOptimize, "opt");
            case "solv" -> makeFile(mol, inputSolvation, "solv");
            case "opt_ox" -> makeFile(redox.increaseCharge(mol), inputOptimize, "opt_ox");
            case "solv_ox" -> makeFile(redox.increaseCharge(mol), inputSolvation, "solv_ox");
            default -> throw new IllegalStateException("Unexpected value: " + template);
        }
    }

    private void makeFile(OBMol mol, Path templateInput, String file) {

        StructureConverter sc = new StructureConverter();
        String coords = sc.fromMolToFormat(mol, "gau");

        try
        {
            PrintWriter printWriter = new PrintWriter(file);
            try (BufferedReader buffRead = new BufferedReader(new FileReader(templateInput.toFile())))
            {
                String line = buffRead.readLine();
                while (line != null) {
                    printWriter.println(line);
                    line = buffRead.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try (BufferedReader reader = new BufferedReader(new StringReader(coords)))
            {
                reader.readLine();
                String line = reader.readLine();
                while (line != null) {
                    printWriter.println(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            printWriter.flush();
            printWriter.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
