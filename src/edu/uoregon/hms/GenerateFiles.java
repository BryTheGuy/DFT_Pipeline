package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.LinkedList;

import static java.lang.String.format;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.openbabel.OBConversion.Option_type.OUTOPTIONS;

public class GenerateFiles {
    public void jobType(OBMol mol, @NotNull String template) {
        jobType(mol, template, "gau");
    }

    public void jobType(OBMol mol, @NotNull String template, String fileFormat) {
        Redox redox = new Redox();
        switch (template) {
            case "opt" -> fileBuilder(mol, "opt", template, fileFormat);
            case "solv" -> fileBuilder(mol, "geom=checkpoint SCRF", template, fileFormat);
            case "pos_ion" -> fileBuilder(redox.removeElectron(mol), "geom=checkpoint", template, fileFormat);
            case "pos_solv" -> fileBuilder(redox.removeElectron(mol), "geom=checkpoint SCRF", template, fileFormat);
            case "neg_ion" -> fileBuilder(redox.addElectron(mol), "geom=checkpoint", template, fileFormat);
            case "neg_solv" -> fileBuilder(redox.addElectron(mol), "geom=checkpoint SCRF", template, fileFormat);
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

    private void fileBuilder(OBMol mol, String calcOption, String calcName, String fileFormat) {
        try {
            OBConversion conv = new OBConversion();

            conv.SetOutFormat(fileFormat);

            String keywords;
            if (calcName.equals("opt")) {
                keywords = format(Settings.getFileHeader() + Settings.getOptions(),
                        mol.GetTitle() + '-' + calcName,
                        Settings.getFunctional(),
                        Settings.getBasisSet(),
                        calcOption);
            }
            else {
                keywords = format(Settings.getFileHeaderChk() + Settings.getOptions(),
                        mol.GetTitle() + '-' + calcName,
                        mol.GetTitle(),
                        Settings.getFunctional(),
                        Settings.getBasisSet(),
                        calcOption);
            }
            conv.AddOption("k", OUTOPTIONS, keywords);

            String molName = mol.GetTitle().replace(' ', '_');

            String fileName = molName + '-' + calcName + ".inp";

            Path writePath = Settings.getOutputPath().resolve(Paths.get("molecule", molName, fileName));

            conv.WriteFile(mol, String.valueOf(writePath));

            System.out.println(conv.WriteString(mol));

        } catch (Exception e) {
            System.err.println("Failed to generate gaussian input file for " + mol.GetTitle());
            throw new RuntimeException(e);
        }
    }

//    public void makeDirs(String name) {
//        for (String s : Arrays.asList("/opt/", "/ip/", "/solv/", "/ox/")) {
//            new File("../molecules/" + name + s).mkdirs();
//        }
//    }

    public void makeDirs(String molName) {
        new File("./molecules", molName).mkdirs();
    }

    public void makeGaussList(String fileTitle, LinkedList<String> fileNames) {

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
            FileWriter fw = new FileWriter(new File(Settings.getOutputPath().toString() + fileTitle, "q-tala-gauss"));
            fw.write(String.valueOf(builder));
            fw.close();
        } catch (IOException e) {
            System.err.println("Failed to write 'q-tala-gauss' file");
            throw new RuntimeException(e);
        }
    }

    public static void pythonSubmit() {
        InputStream inputStream = GenerateFiles.class.getResourceAsStream("src/edu/uoregon/hms/resources/run_slurm.py");
        Path copiedFile = Settings.getOutputPath().resolve("submit.py");

        try {
            assert inputStream != null;
            Files.copy(inputStream, copiedFile, REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
