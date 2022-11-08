import edu.uoregon.hms.GenerateFiles;
import edu.uoregon.hms.NameConverter;
import edu.uoregon.hms.StructureConverter;
import org.jetbrains.annotations.NotNull;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Molecule {
    private String name;
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

    public void genFile() {
        genFile(getMol(), getFormat(), Paths.get("output.txt"));
    }

    public void genFile(Path path) {
        genFile(getMol(), getFormat(), path);
    }

    public void genFile(String format) {
        genFile(getMol(), format, Paths.get("output.txt"));
    }

    public void genFile(String format, Path path) {
        genFile(getMol(), format, path);
    }

    public void genFile(OBMol mol,  Path path) {
        genFile(mol, getFormat(), path);
    }

    public void genFile(@NotNull OBMol mol, @NotNull String format, @NotNull Path path) {
        OBConversion conv = new OBConversion();
        conv.SetOutFormat(format);
        conv.WriteFile(mol, String.valueOf(path));
    }

    public void makeFile(String template) {
        GenerateFiles generateFiles = new GenerateFiles();
        generateFiles.genFile(getMol(), template);
    }
}
