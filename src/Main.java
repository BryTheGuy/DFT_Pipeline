import edu.uoregon.hms.FileInterpreter;
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
    public static void main(@NotNull String[] args) {
        System.out.println("Main running...");

        Options options = new Options();

        /*
        Add CLI options here:

        Option *NAME* = new Option( *OPTION SHORT* , *OPTION LONG* , *HAS ARGUMENTS* , *DESCRIPTION* );
        *NAME*.setRequired( 'BOOLEAN' );
        options.addOption( *NAME* );
         */

        // Help Option
        Option help = new Option("h", "help", false, "Prints this message");
        help.setRequired(false);
        options.addOption(help);

        // Check Environment Option
        Option condaEnv = new Option(null, "check", false, "Checks if environment is setup correctly");
        condaEnv.setRequired(false);
        options.addOption(condaEnv);

        // File Input Option
        Option fileInput = new Option("i", "input", true, "input file path");
        fileInput.setRequired(false);
        fileInput.setArgName("file");
        options.addOption(fileInput);

        // Output Directory Option
        Option outputDir = new Option("o", "output-dir", true, "output directory path (Default: Current Dir)");
        outputDir.setRequired(false);
        outputDir.setArgName("dir");
        options.addOption(outputDir);

        // Display Version Option
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

            /*
            Process CLI arguments here
             */

            // help
            if (cmd.hasOption(help)) {
                formatter.printHelp("NAME", header, options, footer, true);
                System.exit(0);
            }

            // version
            if (cmd.hasOption(version)) {
                System.out.printf("Version %s$n", VERSION);
                System.exit(0);
            }

            // conda environment
            if (cmd.hasOption(condaEnv)) {
                checkEnv();
                System.exit(0);
            }

            // input file checking
            if (cmd.hasOption(fileInput)) {
                checkInputFile(Path.of(cmd.getOptionValue(fileInput)));
            }

            // output directory checking
            if (cmd.hasOption(outputDir)) {
                checkOutputPath(Path.of(cmd.getOptionValue(outputDir)));
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("NAME", options);
            System.exit(0);
        }

        setup();
        FileInterpreter.readToList();
        FileInterpreter.checkLines();
//        FileInterpreter.checker();

        for (String name : Settings.getStringMoleculeNames()) {
//            String smi = NameConverter.nameToSmi(name);
            Molecule mol = new Molecule(name.strip());
//            mol.defaultRun();
        }
//        GenerateFiles.pythonSubmit();
    }
    private static void setup() {
        Settings.setLineLengthOfInterest();
        Settings.setTextBlockEnds();
        Settings.setWhiteTillText();
        Settings.setLinesInFileHeader();
    }

    private static void checkEnv() {
        Path condaPrefix = Path.of(System.getenv("CONDA_PREFIX"));
        //TODO: Check if using 'base' conda environment
        Path condaEnvName = condaPrefix.getFileName();
        System.out.printf("Found conda environment: %s", condaEnvName);
        Path obabelFile = condaPrefix.resolve("bin/obabel");
        boolean isObabelExecutable = Files.isRegularFile(obabelFile) & Files.isReadable(obabelFile) & Files.isExecutable(obabelFile);
        System.out.printf("Found obabel executable: %s", isObabelExecutable);
        if (!isObabelExecutable) {
            System.err.println("It is recommended to install openbabel in conda with 'conda install -c conda-forge openbabel' in a separate environment.");
        }
    }

    private static void checkInputFile(Path inputPath) {
        if (!Files.exists(inputPath)) {
            System.err.printf("The system path %s does not exist", inputPath);
            System.exit(0);
        }

        if (Files.isRegularFile(inputPath) & Files.isReadable(inputPath)) {
            try {
                Settings.setFilePath(inputPath.toRealPath());
            } catch (IOException e) {
                System.err.println();
            }
        } else {
            System.err.printf("%s is not regular or readable", inputPath);
            System.exit(0);
        }
    }

    private static void checkOutputPath(Path outputPath) {
        if (!Files.exists(outputPath) & !Files.isDirectory(outputPath)) {
            System.err.printf("Cannot reach output directory %s", outputPath);
            System.exit(0);
        }
        System.out.println("Output: " + outputPath);
        Settings.setOutputPath(outputPath + "/molecule/");
    }
}