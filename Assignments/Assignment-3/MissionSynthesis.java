import java.util.*;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();

        List<Molecule> humanWeakestBondMolecules = new ArrayList<>();
        List<Molecule> diffWeakestBondMolecules = new ArrayList<>();

        for (MolecularStructure molecularStructure : humanStructures){
            humanWeakestBondMolecules.add(molecularStructure.getMoleculeWithWeakestBondStrength());
        }
        for (MolecularStructure molecularStructure : diffStructures){
            diffWeakestBondMolecules.add(molecularStructure.getMoleculeWithWeakestBondStrength());
        }

        humanWeakestBondMolecules.sort((Comparator.comparingInt(Molecule::getBondStrength)));

        diffWeakestBondMolecules.sort((Comparator.comparingInt(Molecule::getBondStrength)));

        Molecule humanWeakestBondMolecule = humanWeakestBondMolecules.get(0);
        Molecule diffWeakestBondMolecule = diffWeakestBondMolecules.get(0);


        System.out.println("Typical human molecules selected for synthesis: "+ humanWeakestBondMolecules.toString());
        System.out.println("Vitales molecules selected for synthesis: " + diffWeakestBondMolecules.toString());
        System.out.println("Synthesizing the serum...");

        int i = 0;
        int j = 0;
        Molecule mainMolecule;

        if (humanWeakestBondMolecule.getBondStrength() > diffWeakestBondMolecule.getBondStrength()){
            mainMolecule = diffWeakestBondMolecule;
            j++;
        }else {
            mainMolecule = humanWeakestBondMolecule;
            i++;
        }
        for (int index = 1; index < humanWeakestBondMolecules.size() + diffWeakestBondMolecules.size(); index++){
            if (i != humanWeakestBondMolecules.size() && humanWeakestBondMolecules.get(i).getBondStrength() < diffWeakestBondMolecules.get(j).getBondStrength()){
                Molecule from = humanWeakestBondMolecules.get(i);
                double weight = (double) (mainMolecule.getBondStrength() + from.getBondStrength()) / 2;
                Bond bond = new Bond(mainMolecule,from,weight);
                serum.add(bond);
                i++;
            } else if (j != diffWeakestBondMolecules.size() && diffWeakestBondMolecules.get(j).getBondStrength() < humanWeakestBondMolecules.get(i).getBondStrength()) {
                Molecule from = diffWeakestBondMolecules.get(j);
                double weight = (double) (mainMolecule.getBondStrength() + from.getBondStrength()) / 2;
                Bond bond = new Bond(mainMolecule,from,weight);
                serum.add(bond);
                j++;
            }else {
                while (i < humanWeakestBondMolecules.size()){
                    Molecule from = humanWeakestBondMolecules.get(i);
                    double weight = (double) (mainMolecule.getBondStrength() + from.getBondStrength()) / 2;
                    Bond bond = new Bond(mainMolecule,from,weight);
                    serum.add(bond);
                    i++;
                }
                while (j < diffWeakestBondMolecules.size()){
                    Molecule from = diffWeakestBondMolecules.get(j);
                    double weight = (double) (mainMolecule.getBondStrength() + from.getBondStrength()) / 2;
                    Bond bond = new Bond(mainMolecule,from,weight);
                    serum.add(bond);
                    j++;
                }
                break;
            }
        }
        serum.sort(Comparator.comparingDouble(Bond::getWeight));

        return serum;
    }

    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {

        double total = 0;

        for (Bond bond : serum){
            Molecule to = bond.getTo();
            Molecule from = bond.getFrom();
            double weight = bond.getWeight();
            total += weight;
            if (to.compareTo(from) < 0){
                System.out.printf("Forming a bond between "+to.getId()+" - "+from.getId()+" with strength %.2f \n",weight);
            }else {
                System.out.printf("Forming a bond between "+from.getId()+" - "+to.getId()+" with strength %.2f \n",weight);
            }
        }
        System.out.printf("The total serum bond strength is %.2f \n",total);

    }
}
