package com.example.androidthreadstests.parser;

import org.xml.sax.Attributes;

import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.utils.Constants;

public class FlikrParser extends BaseParser<GalleryItem> {

	private GalleryItem mGalleryItem;
	@Override
	protected void OnStartElement(String uri, String localName, String qName, Attributes attributes) {
		if(qName.equalsIgnoreCase("photo")){
			mGalleryItem = new GalleryItem();
			for(int i = 0; i < attributes.getLength(); i++) {
				String attrValue = attributes.getValue(i);
				String attrName = attributes.getQName(i);
				if(attrName.equalsIgnoreCase("id")){
					mGalleryItem.setId(attrValue);
				}else if(attrName.equalsIgnoreCase("secret")){
					mGalleryItem.setSecret(attrValue);
				}else if(attrName.equalsIgnoreCase("server")){
					mGalleryItem.setServer(attrValue);
				}else if(attrName.equalsIgnoreCase("farm")){
					mGalleryItem.setFarm(attrValue);
				}else if(attrName.equalsIgnoreCase("title")){
					mGalleryItem.setTitle(attrValue);
				}
			}
		}else if(qName.equalsIgnoreCase("photos")) {
			try{
				Constants.NUM_PAGES = Integer.valueOf(attributes.getValue("pages"));
			}catch(NumberFormatException e) {
				Constants.NUM_PAGES = 0;
			}
		}
	}

	@Override
	protected GalleryItem OnEndElement(String uri, String localName, String qName) {
		if(qName.equalsIgnoreCase("photo")){
			return mGalleryItem;
		}
		return null;
	}

	@Override
	protected void OnCharachters(char[] ch, int start, int length) {
	}

}
