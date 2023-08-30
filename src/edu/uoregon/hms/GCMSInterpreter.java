package edu.uoregon.hms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;


public class GCMSInterpreter {
    static int countParse = 0;
    static int countAddList = 0;

    public static void loadGCMSFile(Path gcmsPath) {
        //TODO add a load function to take file and run this class *maybe*
    }

    /**
     * Loads Settings.INPUT_PATH into ArrayList using BufferReader
     * @return ArrayList of all lines in file
     */
    public static ArrayList<String> readToList() {
        Path gcmsPath = Settings.getFilePath();
        ArrayList<String> lines;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(gcmsPath.toFile()));
            lines = new ArrayList<>(reader.lines().toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;

    }

    /**
     * Determine how many lines of header are in file
     * @param lines ArrayList of lines in text file
     * @return number of lines in header or -1
     */
    public static int contentFinder(ArrayList<String> lines) {
        for (String line : lines) {
            if (line.contains("___")) {
                return lines.indexOf(line);
            }
        }
        return -1; //FIXME return 0 instead of -1 to indicate file is already trimmed?
    }

    /**
     * Removes header of file
     * @param lines    ArrayList of lines
     * @param sepIndex zero based index of lines to cut.
     * @return ArrayList of trimmed lines
     */
    public static List<String> trimHeader(ArrayList<String> lines, int sepIndex) {
        if (sepIndex < 0) {
            throw new IndexOutOfBoundsException("sepIndex = " + sepIndex);
        }
        return lines.subList(sepIndex + 1, lines.size() - 1);
    }

    /**
     * Checks if Character in column 3 is a digit
     * @param line Line to be checked
     * @return boolean true if digit, false is not.
     */
    public static boolean isPeakLine(String line) {
        if (line.length() > 2) {
            return Character.isDigit(line.charAt(2));
        }
        return false;
    }

    /**
     * Looks for peak line then integrates until the next peak line
     * @param lines Lines of text to search
     * @return ArrayList of lines in a peak block
     */
    public static List<String> peakBlocker(ArrayList<String> lines) { //TODO add functionality for multiple peak blocks
        Iterator<String> line = lines.iterator();
        while (line.hasNext()) {
            String currentLine = line.next();
            if (isPeakLine(currentLine)) {
                List<String> peak = new ArrayList<>();
                do {
                    peak.add(currentLine);
                    currentLine = line.next();
                } while (!isPeakLine(currentLine));
                return peak;
            }
        }
        return new ArrayList<>(0);
    }

    public static void setMoleculeNames(String moleculeName) {
        // System.out.println(moleculeName);
        Settings.getStringMoleculeNames().add(moleculeName);
    }
    public static void checker() {
        Scanner userInputNames = new Scanner(System.in);
        System.out.println("Check set? (y/n)");
        String answerName = userInputNames.nextLine();
        if (answerName.equals("y")) {
            System.out.println("\033[1;36m----------------------------------------------------------------------------------------\033[0m");
            for (String i : Settings.getStringMoleculeNames()) {
                System.out.println(i);
            }
            System.out.println("\033[1;32mDone\033[0m");
            System.out.println("Molecules found: " + Settings.getStringMoleculeNames().size());
            System.out.println("counted through parseLine: " + countParse);
        }
        Scanner userInputList = new Scanner(System.in);
        System.out.println("Check list? (y/n)");
        String answerList = userInputList.nextLine();
        if (answerList.equals("y")) {
            System.out.println("\033[1;36m----------------------------------------------------------------------------------------\033[0m");
            for (String i : Settings.getStringLineList()) {
                System.out.println(i);
            }
            System.out.println("\033[1;32mDone\033[0m");
            System.out.println("List entries: " + Settings.getStringLineList().size());
            System.out.println("counted through Add to List: " + countAddList);
        }
    }

    public static void checker(HashSet<String> set) {
        Scanner userInputNames = new Scanner(System.in);
        System.out.println("Check set? (y/n)");
        String answerName = userInputNames.nextLine();
        if (answerName.equals("y")) {
            System.out.println("\033[1;36m----------------------------------------------------------------------------------------\033[0m");
            for (String i : set) {
                System.out.println(i);
            }
            System.out.println("\033[1;32mDone\033[0m");
            System.out.println("Molecules found: " + set.size());
            System.out.println("counted through parseLine: " + countParse);
        }
    }

    public static void checker(Set<String> stringSet, List<String> stringList) {
        Scanner userInputNames = new Scanner(System.in);
        System.out.println("Check set? (y/n)");
        String answerName = userInputNames.nextLine();
        if (answerName.equals("y")) {
            System.out.println("\033[1;36m----------------------------------------------------------------------------------------\033[0m");
            for (String i : stringSet) {
                System.out.println(i);
            }
            System.out.println("\033[1;32mDone\033[0m");
            System.out.println("Molecules found: " + stringSet.size());
            System.out.println("counted through parseLine: " + countParse);
        }
        Scanner userInputList = new Scanner(System.in);
        System.out.println("Check list? (y/n)");
        String answerList = userInputList.nextLine();
        if (answerList.equals("y")) {
            System.out.println("\033[1;36m----------------------------------------------------------------------------------------\033[0m");
            for (String i : stringList) {
                System.out.println(i);
            }
            System.out.println("\033[1;32mDone\033[0m");
            System.out.println("List entries: " + stringList.size());
            System.out.println("counted through Add to List: " + countAddList);
        }
    }
}
