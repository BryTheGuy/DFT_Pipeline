package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.net.URLDecoder.decode;

/**
 * @author Brycen Falzone <brycenf@uoregon.edu>
 * @version 0.3                 (current version number of program)
 * @since 0.1          (the version of the package this class was first added to)
 */

public class Locator {
//    private static String fileName = null;

    public static void findFile(String fileName) {
        Path cwd = Paths.get(System.getProperty("user.dir"));

    }

    /**
     * @return execution path of where the .jar was called
     */
    public static String getExecutionPath() {
        try {
            String executionPath = System.getProperty("user.dir");
            System.out.println("Executing at =>" + executionPath.replace("\\", "/"));
            return executionPath;
        } catch (Exception e) {
            System.err.println("Exception caught =>" + e.getMessage());
        }
        return null;
    }

    /**
     * @return absolute file path
     */
    public static @NotNull String getFilePath() {
        return getFilePath("hendon_fresh_1.txt");
    }

    /**
     * @param fileName Name of target file in execution path dir
     * @return absolute file path
     */
    @SuppressWarnings("ReassignedVariable")
    public static @NotNull String getFilePath(String fileName) {
//        String absolute = getSource();
        // FIXME add fileName variable to "getExecutionPath()"
        String absolute = getExecutionPath(/*fileName*/); //FIXME figure out how to get proper execution path
        assert absolute != null;
        absolute = absolute.substring(0, absolute.length() - 1);
        absolute = absolute.substring(0, absolute.lastIndexOf("/") + 1);
        String filePath = absolute + fileName;
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) { // Check if of is windows
            filePath = filePath.replace("/", "\\\\");
            if (filePath.contains("file:\\\\")) {
                filePath = filePath.replace("file:\\\\", "");
            }
        } else if (filePath.contains("file:")) {
            filePath = filePath.replace("file:", "");
        }
        return filePath; // theoretical file path to file named fileName in executed directory
    }

    public String getSource() {
        String path = Locator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return decode(path, StandardCharsets.UTF_8);
    }

//    public String getFileName() {
//        return fileName;
//    }

//    public static void setFileName(String fileName) {
//        Locator.fileName = fileName;
//    }
}
