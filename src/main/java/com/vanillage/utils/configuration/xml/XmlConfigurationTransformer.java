package com.vanillage.utils.configuration.xml;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.vanillage.utils.configuration.ConfigurationTransformer;
import com.vanillage.utils.configuration.conversion.ConfigurationElement;
import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;
import com.vanillage.utils.configuration.exceptions.ConfigurationTransformerException;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerRegistry;

public final class XmlConfigurationTransformer extends ConfigurationTransformer {
    public XmlConfigurationTransformer() {

    }

    public XmlConfigurationTransformer(ConfigurationSerializerRegistry configurationSerializerRegistry) {
        super(configurationSerializerRegistry);
    }

    @Override
    public void transform(Object object, File file) throws ConfigurationTransformerException {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(2));
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(transformToDocument(object)), new StreamResult(file));
        } catch (TransformerFactoryConfigurationError | TransformerException | ParserConfigurationException e) {
            throw new ConfigurationTransformerException(e);
        }
    }

    @Override
    public String transform(Object object) throws ConfigurationTransformerException {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(2));
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Writer writer = new StringWriter();
            transformer.transform(new DOMSource(transformToDocument(object)), new StreamResult(writer));
            return writer.toString();
        } catch (TransformerFactoryConfigurationError | TransformerException | ParserConfigurationException e) {
            throw new ConfigurationTransformerException(e);
        }
    }

    private Document transformToDocument(Object object) throws ParserConfigurationException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element element = document.createElement("configuration");
        transform(object, element);
        document.appendChild(element);
        return document;
    }

    private void transform(Object object, Element element) {
        ConfigurationSerialization configurationSerialization = getConfigurationSerializerRegistry().serialize(object);

        if (configurationSerialization == null) {
            throw new IllegalStateException("configurationSerialization cannot be null");
        }

        if (configurationSerialization.getType() != null) {
            element.setAttribute("type", configurationSerialization.getType());
        }

        if (configurationSerialization.getValue() != null) {
            element.appendChild(element.getOwnerDocument().createTextNode(configurationSerialization.getValue()));
            return;
        }

        for (Entry<Object, Object> entry : configurationSerialization.getMap().entrySet()) {
            Object value = null;
            Element child = null;
            boolean validString = false;

            if (/*entry.getKey() != null && entry.getKey().getClass().equals(String.class)*//*class String is final*/entry.getKey() instanceof String) {
                try {
                    child = element.getOwnerDocument().createElement((String) entry.getKey());
                    value = entry.getValue();
                    validString = true;
                } catch (DOMException e) {

                }
            }

            if (!validString) {
                child = element.getOwnerDocument().createElement("element");
                value = new ConfigurationElement<Object, Object>(entry.getKey(), entry.getValue());
            }

            transform(value, child);
            element.appendChild(child);
        }
    }
}
