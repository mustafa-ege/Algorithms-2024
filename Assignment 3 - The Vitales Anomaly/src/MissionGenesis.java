import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
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
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            Element root = document.getDocumentElement();

            NodeList humanList = root.getElementsByTagName("HumanMolecularData");
            if (humanList.getLength() > 0) {
                molecularDataHuman = processInput((Element) humanList.item(0));
            }

            NodeList vitalesList = root.getElementsByTagName("VitalesMolecularData");
            if (vitalesList.getLength() > 0) {
                molecularDataVitales = processInput((Element) vitalesList.item(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private MolecularData processInput(Element element) {
        List<Molecule> molecularDataList = new ArrayList<>();

        NodeList moleculeList = element.getElementsByTagName("Molecule");
        for (int i = 0; i < moleculeList.getLength(); i++) {
            Element moleculeElement = (Element) moleculeList.item(i);

            String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();
            int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());

            NodeList bondList = moleculeElement.getElementsByTagName("MoleculeID");
            List<String> bonds = new ArrayList<>();
            for (int j = 0; j < bondList.getLength(); j++) {
                Element bondElement = (Element) bondList.item(j);
                bonds.add(bondElement.getTextContent());
            }

            Molecule molecule = new Molecule(id, bondStrength, bonds);
            molecularDataList.add(molecule);
        }
        MolecularData molecularData = new MolecularData(molecularDataList);

        return molecularData;
    }
}