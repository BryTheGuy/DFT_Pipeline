package edu.uoregon.hms;

import java.util.HashSet;
import java.util.LinkedList;

public class Settings {
    private static int LINE_LENGTH_OF_INTEREST;
    private static int TEXT_BLOCK_ENDS;
    private static int WHITE_TILL_TEXT;
    private static int LINES_IN_FILE_HEADER;  // number of lines in the header of the file

    private static LinkedList<String> stringLineList = new LinkedList<>();
    private static HashSet<String> stringMoleculeNames = new HashSet<>();

    public static int getWhiteTillText() {return WHITE_TILL_TEXT;}
    // public static int getLengthOfBlankLine() {return LENGTH_OF_BLANK_LINE;}
    // public static int getLenOfTextBlock() {return LEN_OF_TEXT_BLOCK;}
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
}