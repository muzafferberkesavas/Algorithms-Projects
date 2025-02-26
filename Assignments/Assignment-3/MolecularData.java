import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

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
        ArrayList<Molecule> markedIDs = new ArrayList<>();
        ArrayList<String> markedIDsString = new ArrayList<>();
        for (int index = 0; index < molecules.size(); index++) {
            if (markedIDsString.contains(molecules.get(index).getId())) {
                continue;
            }
            MolecularStructure molecularStructure = new MolecularStructure();
            dfs(molecules.get(index),markedIDs);
            for (Molecule molecule : markedIDs){
                int isContain = structures.stream()
                                .filter(molecularStructure1 -> molecularStructure1.hasMolecule(molecule.getId()))
                                        .findFirst()
                                                .map(structures::indexOf)
                                                        .orElse(-1);
                if (isContain == -1){
                    markedIDsString.add(molecule.getId());
                    molecularStructure.addMolecule(molecule);
                }else {
                    for (Molecule molecule1 : markedIDs){
                        if (!(structures.get(isContain).hasMolecule(molecule1.getId()))){
                            structures.get(isContain).addMolecule(molecule1);
                        }
                    }
                    molecularStructure.getMolecules().clear();
                }
            }
            if (!molecularStructure.getMolecules().isEmpty()){
                structures.add(molecularStructure);
            }
            markedIDs.clear();
        }
        return structures;
    }

    public void dfs(Molecule molecule,ArrayList<Molecule> markedIDs){
        markedIDs.add(molecule);
        for (String bond : molecule.getBonds()){
            int isContain = markedIDs.stream()
                    .filter(molecule1 -> molecule1.getId().equals(bond))
                    .findFirst()
                    .map(markedIDs::indexOf)
                    .orElse(-1);

            if (isContain == -1){
                int index = molecules.stream()
                        .filter(molecule1 -> molecule1.getId().equals(bond))
                        .findFirst()
                        .map(molecules::indexOf)
                        .orElse(-1);
                if (index != -1){
                    dfs(molecules.get(index),markedIDs);
                }
            }
        }
    }

    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {

     int size = molecularStructures.size();
     System.out.printf(size + " molecular structures have been discovered in %s.\n",species);
     for (int index = 0; index < size; index++) {
         System.out.println("Molecules in Molecular Structure " + (index + 1) + ": " + molecularStructures.get(index).toString());
     }

    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {

        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();

        for (MolecularStructure target : targeStructures){
            boolean isDistinct = true;
            for (MolecularStructure source : sourceStructures){
                if (target.equals(source)){
                    isDistinct = false;
                }
            }
            if (isDistinct){
                anomalyList.add(target);
            }
        }


        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {

        System.out.println("Molecular structures unique to Vitales individuals:");
        if (!molecularStructures.isEmpty()){
            for (MolecularStructure molecularStructure : molecularStructures){
                System.out.println(molecularStructure.toString());
            }
        }
    }
}
