import java.util.*;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans
    private List<Molecule> selectedHumanMolecules;
    private List<Molecule> selectedVitalesMolecules;

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();

        List<Bond> potentialBonds = new ArrayList<>();

        selectedHumanMolecules = new ArrayList<>();
        for (MolecularStructure humanStructure : humanStructures) {
            humanStructure.getMolecules().sort(Comparator.comparing(Molecule::getBondStrength));
            selectedHumanMolecules.add(humanStructure.getMolecules().get(0));
        }
        selectedVitalesMolecules = new ArrayList<>();
        for (MolecularStructure vitalesStructure : diffStructures) {
            vitalesStructure.getMolecules().sort(Comparator.comparing(Molecule::getBondStrength));
            selectedVitalesMolecules.add(vitalesStructure.getMolecules().get(0));
        }
        List<Molecule> allMolecules = selectedHumanMolecules;
        allMolecules.addAll(selectedVitalesMolecules);
        for (int i = 0; i < allMolecules.size(); i++) {
            for (int j = i+1; j < allMolecules.size(); j++){
                double weight = (allMolecules.get(i).getBondStrength() + allMolecules.get(j).getBondStrength()) / 2.0;
                Bond potentBond = new Bond(allMolecules.get(i),allMolecules.get(j),weight);
                potentialBonds.add(potentBond);
            }
        }

        potentialBonds.sort(Comparator.comparing(Bond::getWeight));
        UnionFind unionFind = new UnionFind(allMolecules.size());
        for (Bond bond : potentialBonds) {
            int molecule1Index = allMolecules.indexOf(bond.getTo());
            int molecule2Index = allMolecules.indexOf(bond.getFrom());
            if (!unionFind.connected(molecule1Index, molecule2Index)) {
                unionFind.union(molecule1Index, molecule2Index);
                serum.add(bond);
            }
        }
        return serum;
    }
    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {
        System.out.print("Typical human molecules selected for synthesis: [");
        for (int j = 0; j < selectedHumanMolecules.size(); j++){
            System.out.print(selectedHumanMolecules.get(j));
            if (j < selectedHumanMolecules.size()-1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.print("Vitales molecules selected for synthesis: [");
        for (int j = 0; j < selectedVitalesMolecules.size(); j++){
            System.out.print(selectedVitalesMolecules.get(j));
            if (j < selectedVitalesMolecules.size()-1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println("Synthesizing the serum...");
        Double totalStrength = 0.0;
        for (Bond bond : serum){
            List<String> bonds = new ArrayList<>();
            bonds.add(bond.getTo().toString());
            bonds.add(bond.getFrom().toString());
            bonds.sort(null);
            String weight = String.format("%.2f", bond.getWeight());
            System.out.println("Forming a bond between "+ bonds.get(0) + " - "+ bonds.get(1) + " with strength "+ weight);
            totalStrength += bond.getWeight();
        }
        System.out.println("The total serum bond strength is " + String.format("%.2f", totalStrength));

    }
//// for mst
    class UnionFind {
        private int[] parent;
        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        public int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            return parent[x] = find(parent[x]);
        }
        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }

}
