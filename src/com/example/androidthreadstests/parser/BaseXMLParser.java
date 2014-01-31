package com.example.androidthreadstests.parser;

import com.example.androidthreadstests.exceptions.ParseException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public abstract class BaseXMLParser<Result> extends BaseParser<Result> {
	private XMLHandler mHandler;

	public BaseXMLParser() {
		mHandler = new XMLHandler();
	}

    @Override
    public Result parse(InputStream inputData) throws ParseException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(inputData, mHandler);
        } catch (SAXException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (ParserConfigurationException e) {
            return null;
        }
        return getResult();
    }

    /**
     *Called from base parser when starting a new element in the xml
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     *
     *
     */
	protected abstract void OnStartElement(String uri, String localName, String qName, Attributes attributes);

    /**
     * Called from base parser when ending an element in the xml
     * @param uri
     * @param localName
     * @param qName
     * @return void
     *
     */
	protected abstract void OnEndElement(String uri, String localName, String qName);

    /**
     *  Called when receiving characters from an xml element
     * @param ch
     * @param start
     * @param length
     * @return void
     *
     */
	protected abstract void OnCharacters(char[] ch, int start, int length);
	
	private class XMLHandler extends DefaultHandler{
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			OnStartElement(uri, localName, qName, attributes);
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			super.endElement(uri, localName, qName);
			OnEndElement(uri, localName, qName);
		}
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);
            OnCharacters(ch, start, length);
		}

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }
}
