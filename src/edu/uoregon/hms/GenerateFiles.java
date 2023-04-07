package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.LinkedList;

import static java.lang.String.format;
import static org.openbabel.OBConversion.Option_type.OUTOPTIONS;

public class GenerateFiles {
    public void jobType(OBMol mol, @NotNull String template) {
        Redox redox = new Redox();
        final Path inputSolvation = Paths.get("src/edu/uoregon/hms/resources/gaussian_input_solv");
        final Path inputOptimize = Paths.get("src/edu/uoregon/hms/resources/gaussian_input_opt");
        switch (template) {
            case "opt" -> fileBuilder(mol, "opt");
            case "solv" -> fileBuilder(mol, "geom=checkpoint SCRF");
            case "ip" -> fileBuilder(redox.addElectron(mol), "geom=checkpoint");
            case "ox" -> fileBuilder(redox.addElectron(mol), "geom=checkpoint SCRF");
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

    private void fileBuilder(OBMol mol, String calcType) {
        try {
            OBConversion conv = new OBConversion();

            String keywords = format(Settings.getFileHeader() + Settings.getOptions(), mol.GetTitle(),
                    Settings.getFunctional(), Settings.getBasisSet(), calcType);

            conv.AddOption("k", OUTOPTIONS, keywords);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public void makeDirs(String name) {
//        for (String s : Arrays.asList("/opt/", "/ip/", "/solv/", "/ox/")) {
//            new File("../molecules/" + name + s).mkdirs();
//        }
//    }

    public void makeDirs(String molName) {
        for (String s : Settings.getCalcTypes()) {
            new File("../molecules/" + molName).mkdirs();
        }
    }

    public void makeGauss(LinkedList<String> fileNames) {

        String partition = Settings.getPartition();
        String job_name = "project";
        String time = Settings.getRunTime();
        int nodes = Settings.getNodes();
        String account = Settings.getAccount();

        String output = MessageFormat.format(Settings.getSlurmSubmit(), partition, job_name, time, nodes, account);

        StringBuilder builder = new StringBuilder(output);

        for (String name : fileNames) {
            builder.append(String.format("g09 < %s.inp > %s.out\n", name, name));
        }
        builder.append('\n');
        builder.append('\n');

        try {
            FileWriter fw = new FileWriter("q-tala-gauss");
            fw.write(String.valueOf(builder));
            fw.close();
        } catch (IOException e) {
            System.err.println("Failed to write 'q-tala-gauss' file");
            throw new RuntimeException(e);
        }

    }
}
