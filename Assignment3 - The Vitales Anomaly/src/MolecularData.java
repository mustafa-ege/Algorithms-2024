import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules
    private boolean[] marked;

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        ArrayList<MolecularStructure> structures = new ArrayList<>();

        marked = new boolean[molecules.size()];

        for (int v = 0; v < molecules.size(); v++) {
            for (String wId : molecules.get(v).getBonds()) {
                int wIndex = moleculeFinder(wId, molecules);
                molecules.get(wIndex).addArrivingBond(molecules.get(v).toString());
            }
        }
        cc(structures, molecules);

        return structures;
    }
    private void cc(ArrayList<MolecularStructure> structures, List<Molecule> molecules){
        int structureCount = 0;
        marked = new boolean[molecules.size()];
        for (int m = 0; m < molecules.size(); m++){
            if (!marked[m]) {
                structures.add(new MolecularStructure());
                dfs(molecules.get(m), molecules, m, structures, structureCount);
                structureCount++;
            }
        }
        for (MolecularStructure struct : structures) {
            struct.getMolecules().sort(null);
        }
    }
    public void dfs(Molecule molecule, List<Molecule> molecules, int v, ArrayList<MolecularStructure> structures, int count){
        marked[v] = true;
        structures.get(count).addMolecule(molecule);
        for (String bond : molecule.getCompleteBonds()) {
            int moleculeIndex = moleculeFinder(bond, molecules);
            Molecule nextMolecule = molecules.get(moleculeIndex);
            if (!marked[moleculeIndex])
                dfs(nextMolecule,  molecules, moleculeIndex, structures, count);
        }
    }
    private int moleculeFinder(String moleculeID, List<Molecule> molecules) {
        for (int moleculeIndex = 0; moleculeIndex < molecules.size(); moleculeIndex++){
            if (molecules.get(moleculeIndex).toString().equals(moleculeID))
                return moleculeIndex;
        }
        return -1;
    }
    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {

        /* YOUR CODE HERE */
        System.out.println(molecularStructures.size() + " molecular structures have been discovered in "+species+".");
        for (int i = 0 ; i < molecularStructures.size(); i++) {
            System.out.print("Molecules in Molecular Structure "+ (i+1) +": [");
            for (int j = 0; j < molecularStructures.get(i).getMolecules().size(); j++){
                System.out.print(molecularStructures.get(i).getMolecules().get(j));
                if (j < molecularStructures.get(i).getMolecules().size()-1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();

        /* YOUR CODE HERE */
        for (MolecularStructure structure : targeStructures) {
            if (!sourceStructures.contains(structure)) {
                anomalyList.add(structure);
            }
        }

        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {

        /* YOUR CODE HERE */
        System.out.println("Molecular structures unique to Vitales individuals: ");
        for (MolecularStructure structure : molecularStructures){
            List<Molecule> molecules = structure.getMolecules();
            System.out.print("[");
            for (int i = 0; i < molecules.size(); i++) {
                System.out.print(molecules.get(i));
                if (i < molecules.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }

}
