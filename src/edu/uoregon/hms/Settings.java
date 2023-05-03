package edu.uoregon.hms;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Settings {
    private static int LINE_LENGTH_OF_INTEREST;
    private static int TEXT_BLOCK_ENDS;
    private static int WHITE_TILL_TEXT;
    private static int LINES_IN_FILE_HEADER;  // number of lines in the header of the file

    private static LinkedList<String> stringLineList = new LinkedList<>();
    private static HashSet<String> stringMoleculeNames = new HashSet<>();
    private static ArrayList<String> calcTypes = new ArrayList<>(Arrays.asList("opt", "pos_ion", "solv", "pos_solv", "neg_ion", "neg_solv"));

    private static String FUNCTIONAL = "B3LYP";
    private static String BASIS_SET = "6-311+G";
    private static String FILE_HEADER = """
                    %%NProcShared=28
                    %%mem=50GB
                    %%chk=%s.chk
                    
                    """;
    private static String FILE_HEADER_CHK = """
                    %%NProcShared=28
                    %%mem=50GB
                    %%chk=%s.chk
                    %%oldchk=%s-opt.chk
                    
                    """;
    private static String OPTIONS = "#p %s/%s %s integral=superfinegrid freq";

    private static String SLURM_SUBMIT = """
            #!/bin/bash
            #SBATCH --partition={0}       ### Partition (short, long, fat, longfat)
            #SBATCH --job-name={1}      ### Job Name
            #SBATCH --time={2}        ### WallTime
            #SBATCH --nodes=1               ### Number of Nodes (14 cores per CPU, 2 CPU per node)
            #SBATCH --ntasks-per-node={3, number, integer}    ### Number of tasks (MPI processes)
            #SBATCH --account={4}  ### account

            module load intel-mpi
            module load mkl
            module load gaussian
            
            """;
    private static String PARTITION;
    private static String RUN_TIME;
    private static int NODES;
    private static int TASKS_PER_NODE;
    private static String ACCOUNT;

    private static Path INPUT_PATH;
    private static Path OUTPUT_PATH = Paths.get(System.getProperty("user.dir"));

    public static int getWhiteTillText() {return WHITE_TILL_TEXT;}
    public static int getLineLengthOfInterest() {return LINE_LENGTH_OF_INTEREST;}
    public static int getLinesInFileHeader() {return LINES_IN_FILE_HEADER;}
    public static int getTextBlockEnds() {return TEXT_BLOCK_ENDS;}

    public static HashSet<String> getStringMoleculeNames() {
        return stringMoleculeNames;
    }

    public static void setStringMoleculeNames(HashSet<String> stringMoleculeNames) {
        Settings.stringMoleculeNames = stringMoleculeNames;
    }

    public static LinkedList<String> getStringLineList() {
        return stringLineList;
    }

    public static void setStringLineList(LinkedList<String> stringLineList) {
        Settings.stringLineList = stringLineList;
    }

    public static void addStringLineList(String addString) {
        getStringLineList().add(addString);
    }

    // The length of lines that contain a CAS number
    public static void setLineLengthOfInterest() {setLineLengthOfInterest(73);}

    public static void setLineLengthOfInterest(int LINE_LENGTH_OF_INTEREST) {
        Settings.LINE_LENGTH_OF_INTEREST = LINE_LENGTH_OF_INTEREST;}

    // Index of the end of the name text block
    public static void setTextBlockEnds() {setTextBlockEnds(51);}

    public static void setTextBlockEnds(int TEXT_BLOCK_ENDS) {
        Settings.TEXT_BLOCK_ENDS = TEXT_BLOCK_ENDS;}

    // Number of white characters in front of the text block
    public static void setWhiteTillText() {setWhiteTillText(17);}

    public static void setWhiteTillText(int WHITE_TILL_TEXT) {
        Settings.WHITE_TILL_TEXT = WHITE_TILL_TEXT;}
    // Number of lines in file header to skip
    public static void setLinesInFileHeader() {setLinesInFileHeader(20);}

    public static void setLinesInFileHeader(int LINES_IN_FILE_HEADER) {
        Settings.LINES_IN_FILE_HEADER = LINES_IN_FILE_HEADER;}

    public static String getFunctional() {return FUNCTIONAL;}

    public static void setFunctional(String FUNCTIONAL) {
        Settings.FUNCTIONAL = FUNCTIONAL;
    }

    public static String getBasisSet() {return BASIS_SET;}

    public static void setBasisSet(String BASIS_SET) {
        Settings.BASIS_SET = BASIS_SET;
    }

    public static String getFileHeader() {return FILE_HEADER;}

    public static void setFileHeader(String FILE_HEADER) {
        Settings.FILE_HEADER = FILE_HEADER;
    }

    public static String getFileHeaderChk() {return FILE_HEADER_CHK;}

    public static void setFileHeaderChk(String FILE_HEADER_CHK) {
        Settings.FILE_HEADER_CHK = FILE_HEADER_CHK;
    }

    public static String getOptions() {return OPTIONS;}

    public static ArrayList<String> getCalcTypes() {
        return calcTypes;
    }


    public static void setCalcTypes(ArrayList<String> calcTypes) {
        Settings.calcTypes = calcTypes;
    }

    public static void addCalcTypes(String calcType) {
        getCalcTypes().add(calcType);
    }

    public static void addCalcTypes(int index, String calcType) {
        getCalcTypes().add(index, calcType);
    }

    public static String getSlurmSubmit() {
        return SLURM_SUBMIT;
    }

    public static void setSlurmSubmit(String slurmSubmit) {
        SLURM_SUBMIT = slurmSubmit;
    }

    public static String getPartition() {
        return PARTITION;
    }

    public static void setPartition(String PARTITION) {
        Settings.PARTITION = PARTITION;
    }

    public static String getRunTime() {
        return RUN_TIME;
    }

    public static void setRunTime(String runTime) {
        RUN_TIME = runTime;
    }

    public static int getNodes() {
        return NODES;
    }

    public static void setNodes(int NODES) {
        Settings.NODES = NODES;
    }

    public static int getTasksPerNode() {
        return TASKS_PER_NODE;
    }

    public static void setTasksPerNode(int tasksPerNode) {
        TASKS_PER_NODE = tasksPerNode;
    }

    public static String getAccount() {
        return ACCOUNT;
    }

    public static void setAccount(String ACCOUNT) {
        Settings.ACCOUNT = ACCOUNT;
    }

    public static void setOptions(String Options) {
        Settings.OPTIONS = Options;
    }

    public static Path getFilePath() {
        return INPUT_PATH;
    }

    public static void setFilePath(Path filePath) {
        Settings.INPUT_PATH = filePath;
    }

    public static void setFilePath(String filePath) {
        Settings.INPUT_PATH = Paths.get(filePath).toAbsolutePath().normalize();
    }

    public static Path getOutputPath() {
        return OUTPUT_PATH;
    }

    public static void setOutputPath(String outputPath) {
        setOutputPath(Paths.get(outputPath).toAbsolutePath().normalize());
    }

    public static void setOutputPath(Path outputPath) {
        OUTPUT_PATH = outputPath;
    }
    
}