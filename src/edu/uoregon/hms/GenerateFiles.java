package edu.uoregon.hms;

import org.jetbrains.annotations.NotNull;
import org.openbabel.OBMol;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateFiles {
    public void genFile(OBMol mol, @NotNull String template) { //FIXME: Need a OBMol not coords
        Path templateInput;
        final Path inputSolv = Paths.get("gaussian_input_solv");
        switch (template) {
            case "solv" -> templateInput = inputSolv;
            case "opt" -> templateInput = Paths.get("gaussian_input_opt");
            // TODO: make copy of mol
            // TODO: get charge and multiplicity
            // TODO: increase charge on copy (maybe opt) write to file or whatever
            case "solv_ox" -> {
                templateInput = inputSolv;
            }
            default -> throw new IllegalStateException("Unexpected value: " + template);
        }

    }

    private void makeFile(String coords, String templateInput, boolean oxidized) {

    }

    private void chargeChecker(OBMol mol, boolean oxidized) {
        if (oxidized) {

        }
    }
}
