package com.project.pr13;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Classe principal que crea un document XML amb informació de llibres i el guarda en un fitxer.
 * 
 * Aquesta classe permet construir un document XML, afegir elements i guardar-lo en un directori
 * especificat per l'usuari.
 */
public class PR131Main {

    private File dataDir;

    /**
     * Constructor de la classe PR131Main.
     * 
     * @param dataDir Directori on es guardaran els fitxers de sortida.
     */
    public PR131Main(File dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * Retorna el directori de dades actual.
     * 
     * @return Directori de dades.
     */
    public File getDataDir() {
        return dataDir;
    }

    /**
     * Actualitza el directori de dades.
     * 
     * @param dataDir Nou directori de dades.
     */
    public void setDataDir(File dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * Mètode principal que inicia l'execució del programa.
     * 
     * @param args Arguments passats a la línia de comandament (no s'utilitzen en aquest programa).
     */
    public static void main(String[] args) {
        String userDir = System.getProperty("user.dir");
        File dataDir = new File(userDir, "data" + File.separator + "pr13");

        PR131Main app = new PR131Main(dataDir);
        app.processarFitxerXML("biblioteca.xml");
    }

    /**
     * Processa el document XML creant-lo, guardant-lo en un fitxer i comprovant el directori de sortida.
     * 
     * @param filename Nom del fitxer XML a guardar.
     */
    public void processarFitxerXML(String filename) {
        if (comprovarIDirCrearDirectori(dataDir)) {
            Document doc = construirDocument();
            File fitxerSortida = new File(dataDir, filename);
            guardarDocument(doc, fitxerSortida);
        }
    }

    /**
     * Comprova si el directori existeix i, si no és així, el crea.
     * 
     * @param directori Directori a comprovar o crear.
     * @return True si el directori ja existeix o s'ha creat amb èxit, false en cas contrari.
     */
    private boolean comprovarIDirCrearDirectori(File directori) {
        if (!directori.exists()) {
            return directori.mkdirs();
        }
        return true;
    }

    /**
     * Crea un document XML amb l'estructura d'una biblioteca i afegeix un llibre amb els seus detalls.
     * 
     * @return Document XML creat o null en cas d'error.
     */
    private static Document construirDocument() {
        // *************** CODI PRÀCTICA **********************/

        // Crea una factoria de constructors de documents
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // Crea un constructor de documents
        DocumentBuilder db = null;

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Crea un nou document XML
        Document doc = db.newDocument();
        // Crea l'element root del document XML
        Element elmRoot = doc.createElement("biblioteca");
        // Afegeix l'element root al document XML
        doc.appendChild(elmRoot);
        // Crea l'element "to"
        Element elmTo = doc.createElement("llibre");

        Element titol = doc.createElement("titol");
        Element autor = doc.createElement("autor");
        Element anyPublicacio = doc.createElement("anyPublicacio");
        Element editorial = doc.createElement("editorial");
        Element genere = doc.createElement("genere");
        Element pagines = doc.createElement("pagines");
        Element disponible = doc.createElement("disponible");

        Text nodeTitolText = doc.createTextNode("El viatge dels venturons");
        Text nodeAutorText = doc.createTextNode("Joan Pla");
        Text nodeAnyText = doc.createTextNode("1998");
        Text nodeEditText = doc.createTextNode("Edicions Mar");
        Text nodeGenereText = doc.createTextNode("Aventura");
        Text nodePaginesText = doc.createTextNode("320");
        Text nodeDispoText = doc.createTextNode("true");

        elmTo.appendChild(titol);
        elmTo.appendChild(autor);
        elmTo.appendChild(anyPublicacio);
        elmTo.appendChild(editorial);
        elmTo.appendChild(genere);
        elmTo.appendChild(pagines);
        elmTo.appendChild(disponible);

        titol.appendChild(nodeTitolText);
        autor.appendChild(nodeAutorText);
        anyPublicacio.appendChild(nodeAnyText);
        editorial.appendChild(nodeEditText);
        genere.appendChild(nodeGenereText);
        pagines.appendChild(nodePaginesText);
        disponible.appendChild(nodeDispoText);

        elmRoot.appendChild(elmTo);
        // Crea un atribut "id"
        Attr attrId = doc.createAttribute("id");
        // Estableix el valor de l'atribut "id"
        attrId.setValue("001");
        // Afegeix l'atribut "id" a l'element "to"
        elmTo.setAttributeNode(attrId);

       return doc;
    }

    /**
     * Guarda el document XML proporcionat en el fitxer especificat.
     * 
     * @param doc Document XML a guardar.
     * @param fitxerSortida Fitxer de sortida on es guardarà el document.
     */
    private static void guardarDocument(Document doc, File fitxerSortida) {
        // *************** CODI PRÀCTICA **********************/
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fitxerSortida);
            
            transformer.transform(source, result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
