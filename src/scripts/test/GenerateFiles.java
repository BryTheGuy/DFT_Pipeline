package scripts.test;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateFiles {
    @SuppressWarnings("ReassignedVariable")
    public static void genFile(String coords, @NotNull String template) throws FileNotFoundException {
        Path templateInput;
        switch (template) {
            case "solv" -> templateInput = Paths.get("gaussian_input_solv");
            case "opt" -> templateInput = Paths.get("gaussian_input_opt");
            default -> throw new IllegalStateException("Unexpected value: " + template);
        }

        PrintWriter printWriter = new PrintWriter("gua_output");

        try (BufferedReader buffRead = new BufferedReader(new FileReader(templateInput.toString()))) {
            String line = buffRead.readLine();
            while (line != null) {
                printWriter.println(line);
                line = buffRead.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new StringReader(coords))) {
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

    }
}
