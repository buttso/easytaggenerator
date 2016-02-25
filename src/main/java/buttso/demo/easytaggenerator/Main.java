/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttso.demo.easytaggenerator;

import com.beust.jcommander.JCommander;
import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author sbutton
 */
public class Main {

    private JCommander jcommander;

    private static String INDENTS[] = {
        "",
        "  ",
        "    ",
        "      ",
        "        "
    };

    public static void main(String[] args) {
        Main m = new Main();
        m.run(args);
    }

    private void run(String[] args) {
        PanelOptions options = parse(args);
        if (options.help) {
            jcommander.usage();
            return;
        }
        String panel = generate(options);
        if (panel != null) {
            write(options, panel);
        }
    }

    private PanelOptions parse(String[] args) {
        PanelOptions options = new PanelOptions();
        jcommander = new JCommander(options, args);
        return options;
    }

    private String generate(PanelOptions options) {
        return generate_via_string(options);
    }

    private String generate_via_string(PanelOptions options) {
        String homeColor = options.getHomeColor();
        String opponentColor = options.getOpponentColor();
        System.out.printf("Generating panel for %s v %s with %s \n",
                options.home,
                options.opponent,
                options.stats.toString());
        StringWriter sw = new StringWriter();
        sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n");
        sw.write(indent(0, "<EasyTagPanel>"));

        sw.write(elementValue(1, "name", options.name.equals("") ? String.format("%s v %s", options.home, options.opponent) : options.name));

        sw.write(elementValue(1, "height", String.valueOf(options.stats.size())));
        sw.write(elementValue(1, "width", "2"));
        sw.write(elementValue(1, "showStatistics", "1"));

        // Add the PUSHER items
        sw.write(indent(1, "<pushers>"));
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < options.stats.size(); y++) {
                String LABEL = String.format("%s %s", options.stats.get(y), x % 2 == 0 ? options.home : options.opponent);
                sw.append(indent(2, String.format("<pusher index=\"%s\">", x == 0 ? y * 2 : y * 2 + 1)));
                sw.append(elementValue(3, "x", String.valueOf(x)));
                sw.append(elementValue(3, "y", String.valueOf(y)));
                sw.append(elementValue(3, "label", options.stats.get(y)));
                //sw.append(elementValue(3, "value", String.format("[%s] %s", x % 2 == 0 ? options.home : options.opponent, options.stats.get(y))));
                //sw.append(elementValue(3, "category", String.format("%s %s", options.stats.get(y), x % 2 == 0 ? options.home : options.opponent)));
                sw.append(elementValue(3, "value", LABEL));
                sw.append(elementValue(3, "category", LABEL));

                sw.append(elementValue(3, "visible", "1"));
                sw.append(elementValue(3, "showHits", "1"));
                sw.append(elementValue(3, "color", String.format("%s", x % 2 == 0 ? homeColor : opponentColor)));
                sw.append(elementValue(3, "duration", "0"));
                sw.append(elementValue(3, "preroll", "0"));
                sw.append(indent(2, "</pusher>"));

            }
        }
        sw.write(indent(1, "</pushers>"));
        sw.write(indent(0, "</EasyTagPanel>"));
        if (options.debug) {
            System.out.println(sw.getBuffer().toString());
        }
        return sw.getBuffer().toString();
    }

    private String indent(int i, String msg) {
        return String.format("%s%s\n", INDENTS[i], msg);
    }

    private String elementValue(int indent, String name, String value) {
        return String.format("%s<%s>%s</%s>\n",
                INDENTS[indent],
                name,
                value,
                name);
    }

    private void write(PanelOptions options, String panel) {
        FileWriter out = null;
        String filename;
        if (!options.name.equals("")) {
            filename = String.format("%s-EasyPanel.xml", options.name);
        } else {
            filename = String.format("%s_%s-EasyPanel.xml", options.home, options.opponent);
        }
        System.out.printf("Writing file %s ...\n", filename);
        try {
            File f = new File(filename);
            out = new FileWriter(f);
            out.write(panel);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


//
//
private void generate_via_dom(PanelOptions options) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("EasyTagPanel");
            doc.appendChild(rootElement);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(options.name));
            rootElement.appendChild(name);

            Element height = doc.createElement("height");
            height.appendChild(doc.createTextNode(String.valueOf(options.stats.size())));
            rootElement.appendChild(height);

            Element width = doc.createElement("width");
            width.appendChild(doc.createTextNode("2"));
            rootElement.appendChild(width);

            Element showStatistics = doc.createElement("showStatistics");
            showStatistics.appendChild(doc.createTextNode("1"));
            rootElement.appendChild(showStatistics);

            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < options.stats.size(); y++) {
                    System.out.printf("x:[%s] y:[%s] stat:[%s]\n",
                            x,
                            y,
                            options.stats.get(y));
                }
            }

            //
            //
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "4");

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(System.out/*new File("C:\\file.xml")*/);

            transformer.transform(source, result);
            System.out.println("\n");

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

    }

}
