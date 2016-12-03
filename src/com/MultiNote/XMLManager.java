/*
 * XMLManager.java
 *
 * 2013
 *
 * Created by Marc-Alexandre Blanchard - all right reserved ©
 *
 */
package com.MultiNote;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Crée par Marc-Alexandre Blanchard
 */
final class XMLManager
{

    /**
     * A non initialise INSTANCE of a XMLManager
     */
    private static XMLManager XMLManager_INSTANCE = null;

    private XMLManager()
    {
    }

    public static synchronized XMLManager getInstance()
    {
        if (XMLManager_INSTANCE == null)
        {
            XMLManager_INSTANCE = new XMLManager();
        }
        return XMLManager_INSTANCE;
    }

    /**
     *
     * @param stgs the settings to save
     */
    public synchronized void saveSettingsToXML(ArrayList<Settings> AList)
    {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            //Root
            Element rootElement = doc.createElement("ColorNote");
            doc.appendChild(rootElement);

            //Settings
            for (int i = 0; i < AList.size(); i++)
            {
                Element settings = doc.createElement("Settings");
                rootElement.appendChild(settings);

                // set attribute to settings element
                Attr attr = doc.createAttribute("id");
                attr.setValue(Integer.toString(AList.get(i).getId()));
                settings.setAttributeNode(attr);

                Element bgr = doc.createElement("bgr");
                bgr.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getBackgroundRed())));
                settings.appendChild(bgr);

                Element bgg = doc.createElement("bgg");
                bgg.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getBackgroundGreen())));
                settings.appendChild(bgg);

                Element bgb = doc.createElement("bgb");
                bgb.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getBackgroundBlue())));
                settings.appendChild(bgb);

                Element fgr = doc.createElement("fgr");
                fgr.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getForegroundRed())));
                settings.appendChild(fgr);

                Element fgg = doc.createElement("fgg");
                fgg.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getForegroundGreen())));
                settings.appendChild(fgg);

                Element fgb = doc.createElement("fgb");
                fgb.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getForegroundBlue())));
                settings.appendChild(fgb);

                Element lx = doc.createElement("lx");
                lx.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getLocationX())));
                settings.appendChild(lx);

                Element ly = doc.createElement("ly");
                ly.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getLocationY())));
                settings.appendChild(ly);

                Element w = doc.createElement("w");
                w.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getWidth())));
                settings.appendChild(w);

                Element h = doc.createElement("h");
                h.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getHeight())));
                settings.appendChild(h);

                Element opl = doc.createElement("opl");
                opl.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getOpacityLVL())));
                settings.appendChild(opl);

                Element fs = doc.createElement("fs");
                fs.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getFontSize())));
                settings.appendChild(fs);

                Element ns = doc.createElement("ns");
                ns.appendChild(doc.createTextNode(Integer.toString(AList.get(i).getNotificationState())));
                settings.appendChild(ns);

                Element ct = doc.createElement("content");
                ct.appendChild(doc.createTextNode(AList.get(i).getContentTXT()));
                settings.appendChild(ct);
            }
            //Sauvegarde du fichier
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Parameters.FILENAME));
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException ex)
        {
        }
        catch (TransformerConfigurationException ex)
        {
        }
        catch (TransformerException ex)
        {
        }
    }

    /**
     *
     * @return an instance of settings extract from xml
     */
    public synchronized ArrayList<Settings> getSettingsFromXML()
    {
        try
        {
            File fXmlFile = new File(Parameters.FILENAME);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            ArrayList<Settings> resultat = new ArrayList<>();
            NodeList nList = doc.getElementsByTagName("Settings");
            for (int i = 0; i < nList.getLength(); i++)
            {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;
                    Settings temp = new Settings(
                            Integer.parseInt(eElement.getElementsByTagName("bgr").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("bgg").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("bgb").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("fgr").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("fgg").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("fgb").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("lx").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("ly").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("w").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("h").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("fs").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("ns").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("opl").item(0).getTextContent()),
                            eElement.getElementsByTagName("content").item(0).getTextContent());
                    temp.setId(Integer.parseInt(eElement.getAttribute("id")));
                    resultat.add(temp);
                }
            }
            return resultat;

        }
        catch (ParserConfigurationException | SAXException | IOException ex)
        {
        }
        return null;
    }
}
