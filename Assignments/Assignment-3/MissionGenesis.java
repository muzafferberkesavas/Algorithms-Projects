import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Class representing the mission of Genesis
public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {

        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));
            Element root = doc.getDocumentElement();
            NodeList humanMolecules = root.getElementsByTagName("HumanMolecularData");
            List<Molecule> humanMoleculeList = new ArrayList<>();
            Node moleculeNode = humanMolecules.item(0);
            if (moleculeNode.getNodeType() == Node.ELEMENT_NODE){
                Element molecule = (Element) moleculeNode;
                NodeList moleculeList = molecule.getElementsByTagName("Molecule");
                NodeList IDList = molecule.getElementsByTagName("ID");
                NodeList bondStrengthList = molecule.getElementsByTagName("BondStrength");
                NodeList bonds = molecule.getElementsByTagName("Bonds");
                for (int k = 0; k < moleculeList.getLength(); k++){
                    String ID = IDList.item(k).getTextContent();
                    int bondStrength = Integer.parseInt(bondStrengthList.item(k).getTextContent());
                    List<String> bondList;
                    if (bonds.getLength() > 0){
                        Element bondElement = (Element) bonds.item(k);
                        NodeList bondIDs = bondElement.getElementsByTagName("MoleculeID");
                        String[] bondArray = new String[bondIDs.getLength()];
                        for (int j = 0; j < bondIDs.getLength(); j++){
                            String bondID = bondIDs.item(j).getTextContent();
                            bondArray[j] = bondID;
                        }
                        bondList = Arrays.asList(bondArray);
                    }else {
                        bondList = new ArrayList<>();
                    }
                    Molecule newMolecule = new Molecule(ID,bondStrength,bondList);
                    humanMoleculeList.add(newMolecule);
                }
            }

            molecularDataHuman = new MolecularData(humanMoleculeList);

            NodeList vitalesMolecules = root.getElementsByTagName("VitalesMolecularData");
            List<Molecule> vitalesMoleculeList = new ArrayList<>();
            Node moleculeNode2 = vitalesMolecules.item(0);
            if (moleculeNode2.getNodeType() == Node.ELEMENT_NODE){
                Element molecule = (Element) moleculeNode2;
                NodeList moleculeList = molecule.getElementsByTagName("Molecule");
                NodeList IDList = molecule.getElementsByTagName("ID");
                NodeList bondStrengthList = molecule.getElementsByTagName("BondStrength");
                NodeList bonds = molecule.getElementsByTagName("Bonds");
                for (int k = 0; k < moleculeList.getLength(); k++){
                    String ID = IDList.item(k).getTextContent();
                    int bondStrength = Integer.parseInt(bondStrengthList.item(k).getTextContent());
                    List<String> bondList;
                    if (bonds.getLength() > 0){
                        Element bondElement = (Element) bonds.item(k);
                        NodeList bondIDs = bondElement.getElementsByTagName("MoleculeID");
                        String[] bondArray = new String[bondIDs.getLength()];
                        for (int j = 0; j < bondIDs.getLength(); j++){
                            String bondID = bondIDs.item(j).getTextContent();
                            bondArray[j] = bondID;
                        }
                        bondList = Arrays.asList(bondArray);
                    }else {
                        bondList = new ArrayList<>();
                    }
                    Molecule newMolecule = new Molecule(ID,bondStrength,bondList);
                    vitalesMoleculeList.add(newMolecule);
                }
            }


            molecularDataVitales = new MolecularData(vitalesMoleculeList);

        }catch (Exception e){
            molecularDataHuman = null;
            molecularDataVitales = null;
        }


    }
}
