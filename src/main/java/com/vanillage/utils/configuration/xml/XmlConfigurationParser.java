package com.vanillage.utils.configuration.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.vanillage.utils.configuration.ConfigurationParser;
import com.vanillage.utils.configuration.conversion.ConfigurationElement;
import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerRegistry;
import com.vanillage.utils.configuration.exceptions.ConfigurationParserException;

public final class XmlConfigurationParser extends ConfigurationParser {
    public XmlConfigurationParser() {

    }

    public XmlConfigurationParser(ConfigurationDeserializerRegistry configurationDeserializerRegistry) {
        super(configurationDeserializerRegistry);
    }

    @Override
    public Object parse(File file) throws ConfigurationParserException {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        try {
            return parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConfigurationParserException(e);
        }
    }

    @Override
    public Object parse(String string) throws ConfigurationParserException {
        if (string == null) {
            throw new IllegalArgumentException("string cannot be null");
        }

        try {
            return parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(string))));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConfigurationParserException(e);
        }
    }

    private Object parse(Document document) {
        document.getDocumentElement().normalize();
        return parse(document.getDocumentElement());
    }

    private Object parse(Element element) {
        ConfigurationSerialization configurationSerialization = null;
        NodeList nodeList = element.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Object object = parse((Element) node);

                if (configurationSerialization == null) {
                    configurationSerialization = new ConfigurationSerialization(element.hasAttribute("type") ? element.getAttribute("type") : null);
                }

                if (/*object != null && object.getClass().equals(ConfigurationElement.class)*//*class ConfigurationElement is final*/object instanceof ConfigurationElement) {
                    configurationSerialization.getMap().put(((ConfigurationElement<?, ?>) object).getKey(), ((ConfigurationElement<?, ?>) object).getValue());
                } else {
                    configurationSerialization.getMap().put(node.getNodeName(), object);
                }
            }
        }

        if (configurationSerialization == null) {
            configurationSerialization = new ConfigurationSerialization(element.hasAttribute("type") ? element.getAttribute("type") : null, element.getTextContent());
        }

        return getConfigurationDeserializerRegistry().deserialize(configurationSerialization);
    }
}
