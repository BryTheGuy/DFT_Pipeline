package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author      Brycen Falzone <brycenf@uoregon.edu>
 * @version     %I%, %G%                 (current version number of program)
 * @since       0.3          (the version of the package this class was first added to)
 * @
 */
public class FileInterpreter {
    static int countParse = 0;
    static int countAddList = 0;
    public static void readToList() {
        Path gcmsPath = Settings.getFilePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(gcmsPath.toFile()))) {
            Stream<String> lines = reader.lines().skip(Settings.getLinesInFileHeader());
            lines.forEachOrdered(FileInterpreter::addLineToList);
        } catch (IOException e) {
            System.out.println("Error opening BufferReader for GC-MS file:   " + e);
        }
    }

    private static void addLineToList(@NotNull String line) {
        countAddList ++;
        if (!line.contains("C:")) {
            Settings.getStringLineList().add(line);
        }
    }

    /**
     * There are three cases: <p>
     * 1) CAS line has a non CAS line below it <p>
     * 2) CAS line has CAS line below it <p>
     * 3) CAS line has no line below it
     */
    public static void checkLines() {
        for (int i = 0; i < Settings.getStringLineList().size(); i++) {
            String line = Settings.getStringLineList().get(i);
            // Check if line is a CAS line
            if (line.length() >= Settings.getLineLengthOfInterest()) {
                // Case (2 and 3) Line below is also CAS line or has no line below
                String line2 = Settings.getStringLineList().get(i + 1);
                if (line2.length() >= Settings.getLineLengthOfInterest() || line2.contentEquals(" \n")) {
                    setMoleculeNames(singleLineParse(line));
                } else {
                    String doubleLine = combineLines(line, line2);
                    String checkMeNull = parseLine(doubleLine);
                    if (checkMeNull == null) {
                        String line3 = Settings.getStringLineList().get(i + 2);
                        if (line3.length() < Settings.getLineLengthOfInterest()) {
                            String tripleLine = combineLines(doubleLine, line3);
                            String checkMeNull2 = parseLine(tripleLine);
                            setMoleculeNames(Objects.requireNonNullElse(checkMeNull2, doubleLine));
                        } else {
                            setMoleculeNames(doubleLine);
                        }
                    } else {
                        setMoleculeNames(checkMeNull);
                    }
                }
            }
        }
    }

    public static @NotNull String singleLineParse(@NotNull String inLine) {
        int inLineLength = inLine.substring(0, Settings.getTextBlockEnds()).strip().length();
        String newLine = parseLine(inLine.strip().substring(0, inLineLength));
        if (newLine != null) {
            return newLine;
        }
        return inLine.strip().substring(0, inLineLength);
    }

    /**
     *
     * @param firstLine the first line in the concatenation
     * @param secondLine the second line in the concatenation
     * @return Char String containing line that contains molecule name
     */
    public static String combineLines(@NotNull String firstLine, @NotNull String secondLine) {// TODO: could add recursion maybe
        int lengthFirstLine = firstLine.length();
        int lengthSecondLine = secondLine.length();
        // Case (1) Line has non CAS line below it
        if (lengthSecondLine <= Settings.getLineLengthOfInterest() /* 73 */) {
            // Case (1.a) line has full non CAS line below it
            if (!secondLine.contentEquals(" \n")) {
                if (lengthFirstLine < Settings.getLineLengthOfInterest() /* 73 */) { //has already been passes through once
                    return firstLine.concat(secondLine.stripLeading());
                }
                // FIXME when firstLine has already been combined and is short due to sh
                return firstLine.substring(Settings.getWhiteTillText(), Settings.getTextBlockEnds()).concat(secondLine.stripLeading()).stripLeading();
                // Case (1.b) name takes up three lines
            }
        }
        return firstLine;
    }

    /**
     *
     * @param inLine line that will be parsed for name
     * @return parsed name if successful else return null value
     */
    public static @Nullable String parseLine(@NotNull String inLine) {
        countParse++;
        // inLine = inLine;
        if (inLine.contains("$")) {
            int end$ = inLine.indexOf("$");
            String substring$$ = inLine.substring(0, end$).stripLeading();
            if (substring$$.contains("(CAS)")) {
                int endCAS = substring$$.indexOf("(CAS)");
                return substring$$.substring(0, endCAS).stripLeading();
            } else {
                return substring$$;
            }
        } else if (inLine.contains("(CAS)")) {
            int endCAS = inLine.indexOf("(CAS)");
            return inLine.substring(0, endCAS).stripLeading();
        }
        return null;
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
