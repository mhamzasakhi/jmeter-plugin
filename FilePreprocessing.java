
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;

import java.io.StringWriter;
import java.io.Writer;
import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class FilePreprocessing {
    
    ArrayList <ThreadGroup> threadGroups = new ArrayList<>();
    public FilePreprocessing()
    {

    }

    public void TesterFunction(String[] args) 
    {
        FilePreprocessing parser = new FilePreprocessing();

        String file = "BuyPets.jmx";
        parser.readXML(file, "name", "value");

        System.out.println("Thread Groups in JMX File");
        ArrayList <ThreadGroup> threadGroups = parser.getThreadGroups();
        for(int i=0; i<threadGroups.size(); i++)
        {
            System.out.println(threadGroups.get(i).toString());
        }
    }

    public String readXML(String file, String name, String value) 
    {
        String output = "";
        file += ".jmx"; // add extension
        try 
        {   //String file = "input_files/BuyPets2.jmx";
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            output = getDataFromDoc(document, name, value);
        } 
        catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) 
        {
            ex.printStackTrace(System.out);
        }
        return output;
    }

    private String getDataFromDoc (Document document, String name, String value) throws DOMException, XPathExpressionException 
    {
        String output="";
        // Find the stringProp from ThreadGroup
        NodeList threadGroup = document.getElementsByTagName("ThreadGroup");
        Node single_tg;
        for(int tg_id = 0; true; tg_id++)
        {   
            single_tg = threadGroup.item(tg_id);
            if (single_tg == null)
            {
                break;
            }
            Node threadGroupName = single_tg.getAttributes().getNamedItem("testname");
            output = findThreadGroupBody(single_tg, document, name, value);
            //Node num_threads = findThreadGroupBody(single_tg, document);
            ThreadGroup threadGroup_custom = new ThreadGroup(tg_id, threadGroupName.getTextContent(), "");
            threadGroups.add(threadGroup_custom);
        }
        return output;
    }

    private String findThreadGroupBody(Node rootNode, Document document, String inputName, String inputValue)
    {
        Writer output = new StringWriter();
        String name = "";
        String value = "";

        Element rootElement = (Element) rootNode;
        NodeList nodeListStringProp = rootElement.getElementsByTagName("stringProp");
        for(int i=0; i<nodeListStringProp.getLength(); i++)
        {
            name = nodeListStringProp.item(i).getAttributes().getNamedItem("name").getTextContent();
            if (name.equals(inputName)) 
            {
                //old value
                //value = nodeListStringProp.item(i).getTextContent();    
                
                // update the value
                nodeListStringProp.item(i).setTextContent(inputValue);
            }
            //System.out.println(name+ " " +value);
        }

        try
        {
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer xformer = factory.newTransformer();
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //Writer output = new StringWriter();
            xformer.transform(new DOMSource(document), new StreamResult(output));
            //System.out.println(output.toString());   
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return output.toString();
    }

    public int getThreadGroupsSize()
    {
        return this.threadGroups.size();
    }

    public ArrayList<ThreadGroup> getThreadGroups()
    {
        return this.threadGroups;
    }
}
