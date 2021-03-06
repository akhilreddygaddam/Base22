/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import java.awt.Desktop;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.*;
import java.util.*;
public class JavaApplication1 {
    public static void main(String args[]) throws Exception {
        URLConnection uc = new URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3a814702625b307d181ce59815565b49&per_page=1&text=akhil reddy").openConnection();
        DataInputStream dis = new DataInputStream(uc.getInputStream());
        FileWriter fw = new FileWriter(new File("E:\\\\Hello1.xml"));
        String nextline;
        String[] servers = new String[10];
        String[] ids = new String[10];
        String[] secrets = new String[10];
        while ((nextline = dis.readLine()) != null) {
            fw.append(nextline);
        }
        dis.close();
        fw.close();
        String filename = "E:\\\\Hello1.xml";
        XMLInputFactory factory = XMLInputFactory.newInstance();
        System.out.println("FACTORY: " + factory);
        XMLEventReader r = factory.createXMLEventReader(filename, new FileInputStream(filename));
        int i = -1;
        while (r.hasNext()) {
            XMLEvent event = r.nextEvent();
            if (event.isStartElement()) {
                StartElement element = (StartElement) event;
                String elementName = element.getName().toString();
                if (elementName.equals("photo")) {
                    i++;
                    Iterator iterator = element.getAttributes();
                    while (iterator.hasNext()) {
                        Attribute attribute = (Attribute) iterator.next();
                        QName name = attribute.getName();
                        String value = attribute.getValue();
                        System.out.println("Attribute name/value: " + name + "/" + value);
                        if ((name.toString()).equals("server")) {
                            servers[i] = value;
                            System.out.println("Server Value" + servers[0]);
                        }
                        if ((name.toString()).equals("id")) {
                            ids[i] = value;
                        }
                        if ((name.toString()).equals("secret")) {
                            secrets[i] = value;
                        }
                    }
                }
            }
        }
        System.out.println(i);
        String flickrurl = "http://static.flickr.com/" + servers[i] + "/" + ids[i] + "_" + secrets[i] + ".jpg";
        try {
            URI uri = new URI(flickrurl);
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            if (desktop != null) {
                desktop.browse(uri);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (URISyntaxException use) {
            use.printStackTrace();
        }
    }
}