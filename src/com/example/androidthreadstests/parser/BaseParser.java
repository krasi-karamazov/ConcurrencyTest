package com.example.androidthreadstests.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class BaseParser<T> {
	
	private XMLHandler mHandler;
	private List<T> items;
	public BaseParser() {
		mHandler = new XMLHandler();
	}
	
	public List<T> parseData(InputStream xmlData) {
		items = new ArrayList<T>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(xmlData, mHandler);
		} catch (SAXException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (ParserConfigurationException e) {
			return null;
		}
		return items;
	}
	
	protected abstract void OnStartElement(String uri, String localName, String qName, Attributes attributes);
	protected abstract T OnEndElement(String uri, String localName, String qName);
	protected abstract void OnCharachters(char[] ch, int start, int length);
	
	private class XMLHandler extends DefaultHandler{
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			OnStartElement(uri, localName, qName, attributes);
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			super.endElement(uri, localName, qName);
			T item = OnEndElement(uri, localName, qName);
			if(item != null){
				items.add(item);
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);
			OnCharachters(ch, start, length);
		}
	}
}
