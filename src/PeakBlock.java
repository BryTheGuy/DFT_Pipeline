import java.util.List;

/**
 * Class with builder for
 */
public class PeakBlock {
    private final int peakNum;
    private final float retentionTime;
    private final float peakArea;
    private final List<String> candidateNames;
    private final List<Molecule> candidateMolecules;

    public static class Builder {

        private int peakNum;
        private float retentionTime;
        private float peakArea;
        private List<String> candidateNames;
        private List<Molecule> candidateMolecules;

        public Builder withPeakNum(int peakNum) {
            this.peakNum = peakNum;
            return this;
        }

        public Builder withRetentionTime(float retentionTime) {
            this.retentionTime = retentionTime;
            return this;
        }

        public Builder withPeakArea(float peakArea) {
            this.peakArea = peakArea;
            return this;
        }

        public Builder withCandidateNames(List<String> candidateNames) {
            this.candidateNames = candidateNames;
            return this;
        }

        public Builder withCandidateMolecules(List<Molecule> candidateMolecules) {
            this.candidateMolecules = candidateMolecules;
            return this;
        }

        public PeakBlock createPeakBlock() {
            return new PeakBlock(peakNum, retentionTime, peakArea, candidateNames, candidateMolecules);
        }
    }

    private PeakBlock(int peakNum, float retentionTime, float peakArea, List<String> candidateNames, List<Molecule> candidateMolecules) {

        this.peakNum = peakNum;
        this.retentionTime = retentionTime;
        this.peakArea = peakArea;
        this.candidateNames = candidateNames;
        this.candidateMolecules = candidateMolecules;
    }

    public int getPeakNum() {
        return peakNum;
    }

    public float getRetentionTime() {
        return retentionTime;
    }

    public float getPeakArea() {
        return peakArea;
    }

    public List<String> getCandidateNames() {
        return candidateNames;
    }
    public List<Molecule> getCandidateMolecules() {
        return candidateMolecules;
    }
}
