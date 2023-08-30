import edu.uoregon.hms.GenerateFiles;
import edu.uoregon.hms.Settings;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    final static String VERSION = "0.1";
    static Path OUTPUT_PATH = Paths.get("./").toAbsolutePath();
    public static void main(String @NotNull [] args) {
        System.out.println("Main running...");
        System.loadLibrary("openbabel_java");

        Options options = new Options();

        Option help = new Option("h", "help", false, "Prints this message");
        help.setRequired(false);
        options.addOption(help);

        Option fileInput = new Option("i", "input", true, "input file path");
        fileInput.setRequired(false);
        fileInput.setArgName("file");
        options.addOption(fileInput);

        Option outputDir = new Option("o", "output-dir", true, "output directory path (Default: Current Dir)");
        outputDir.setRequired(false);
        outputDir.setArgName("dir");
        options.addOption(outputDir);

        Option version = new Option("V", "version", false, "Version of program");
        version.setRequired(false);
        options.addOption(version);

        String header = "Converts GC-MS into computational chemistry input files\n\n";
        String footer = "Currently in development. Things my not work as expected.";

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            Path inputPath = Paths.get(cmd.getOptionValue(fileInput));

            if (cmd.hasOption(help)) {
                formatter.printHelp("NAME", header, options, footer, true);
                System.exit(0);
            }

            if (cmd.hasOption(version)) {
                System.out.printf("Version %s$n", VERSION);
                System.exit(0);
            }

            if (!Files.exists(inputPath)) {
                System.err.println("System path does not exist: " + inputPath);
                System.exit(0);
            }

            if (Files.isRegularFile(inputPath) & Files.isReadable(inputPath)) {
                Settings.setFilePath(inputPath.toRealPath());
            } else {
                System.err.println("File is not regular or readable: " + inputPath);
                System.exit(0);
            }

            if (cmd.hasOption(outputDir)) {
                Path outputPath = Paths.get(cmd.getOptionValue(outputDir)).toRealPath();
                System.out.println("Output: " + outputPath);
                Settings.setOutputPath(outputPath + "/molecule/");

                if (!Files.exists(outputPath) & !Files.isDirectory(outputPath)) {
                    System.err.println("Cannot reach output directory: " + outputPath);
                    System.exit(0);
                }
            }

        } catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("NAME", options);
            System.exit(0);
        }

        setup();
//        GCMSInterpreter.readToList();
//        GCMSInterpreter.checkLines();
//        GCMSInterpreter.checker();

        for (String name : Settings.getStringMoleculeNames()) {
//            String smi = NameConverter.nameToSmi(name);
            Molecule mol = new Molecule(name.strip());
            mol.defaultRun();
        }
        GenerateFiles.pythonSubmit();
    }
    private static void setup() {
        Settings.setLineLengthOfInterest();
        Settings.setTextBlockEnds();
        Settings.setWhiteTillText();
        Settings.setLinesInFileHeader();
    }
}