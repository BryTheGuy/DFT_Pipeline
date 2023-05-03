import edu.uoregon.hms.GenerateFiles;
import edu.uoregon.hms.NameConverter;
import edu.uoregon.hms.Settings;
import edu.uoregon.hms.StructureConverter;
import org.jetbrains.annotations.NotNull;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Molecule {
    private String name;
    private String name_no_space;
    private String smi;
    private String cml;
    private OBMol mol;
    private String structure;
    private String format;

    public Molecule(String name) {
        this.name = name;
    }

    public Molecule(String name, String smi) {
        this.name = name;
        this.smi = smi;
    }

    public OBMol getMol() {
        return mol;
    }

    public String getSmi() {
        return smi;
    }

    public String getCml() {
        return cml;
    }

    public String getName() {
        return name;
    }

    public String getNoSpaceName() {
        return name_no_space;
    }

    public String getStructure() {
        return structure;
    }

    public String getFormat() {
        return format;
    }

    public void setSmi(String smi) {
        this.smi = smi;
    }

    public void setCml(String cml) {
        this.cml = cml;
    }

    public void setMol(OBMol mol) {
        this.mol = mol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameNoSpace() {
        this.name_no_space = name.replace(' ', '_');
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void genCml() {
        if (cml == null) {
            setCml(NameConverter.nameToCML(name));
        }
    }

    public void genSmi() {
        if (smi == null) {
            setSmi(NameConverter.nameToSmi(name));
        }
    }

    public void genStructure() throws Exception {
        if (format == null) {
            throw new Exception("Please declare a format before generating a structure");
        } else {
            genStructure(format);
        }
    }

    public void genStructure(String format) throws Exception {
        StructureConverter structureConverter = new StructureConverter();
        System.loadLibrary("openbabel_java");
        if (cml != null) {
            setStructure(structureConverter.fromCmlToFormat(cml, format));
        } else if (smi != null) {
            setStructure(structureConverter.fromSmilesToFormat(smi, format));
        } else {
            throw new Exception("Please generate CML or SMILES before structure");
        }
    }

    public void genMol() throws Exception {
        StructureConverter structureConverter = new StructureConverter();
        if (cml != null) {
            setMol(structureConverter.fromCmlToMol(cml));
        } else if (smi != null) {
            setMol(structureConverter.fromSmilesToMol(smi));
        } else {
            throw new Exception("Please generate CML or SMI before creating open babel molecule, \n" +
                    "or use built in open babel OBConversion to convert between files.");
        }
    }

    public void genFileTest() {
        OBConversion conv = new OBConversion();
        conv.SetOutFormat("gau");
        conv.WriteFile(getMol(), "output.txt");
    }

    public void newFile() {
        newFile(getMol(), getFormat(), Paths.get("output.txt"));
    }

    public void newFile(Path path) {
        newFile(getMol(), getFormat(), path);
    }

    public void newFile(String format) {
        newFile(getMol(), format, Paths.get("output.txt"));
    }

    public void newFile(String format, Path path) {
        newFile(getMol(), format, path);
    }

    public void newFile(OBMol mol, Path path) {
        newFile(mol, getFormat(), path);
    }

    public void newFile(@NotNull OBMol mol, @NotNull String fileFormat, @NotNull Path path) {
        OBConversion conv = new OBConversion();
        conv.SetOutFormat(fileFormat);
        conv.WriteFile(mol, String.valueOf(path));
    }

    public void genFile(String template) {
        GenerateFiles generateFiles = new GenerateFiles();

        generateFiles.jobType(getMol(), template);
    }

    public void genAllFiles() {
        genDirs();

        GenerateFiles generateFiles = new GenerateFiles();
        LinkedList<String> fileNames = new LinkedList<>();

        String fileTitle = getNoSpaceName();

        for (String type : Settings.getCalcTypes()) {
            generateFiles.jobType(getMol(), type);
            fileNames.add(fileTitle + '-' + type);
        }
        generateFiles.makeGaussList(fileTitle, fileNames);

        copyPy();
    }

    public void genDirs() {
        GenerateFiles generateFiles = new GenerateFiles();

        generateFiles.makeDirs(getNoSpaceName());
    }

    public void copyPy() {
        GenerateFiles.pythonSubmit();
    }

    public void defaultRun() {
        setNameNoSpace();

        genSmi();

        genCml();

        setFormat("gau");

        try {
            genStructure();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            genMol();
        } catch (Exception e) {
            e.printStackTrace();
        }

        genAllFiles();
    }
}
