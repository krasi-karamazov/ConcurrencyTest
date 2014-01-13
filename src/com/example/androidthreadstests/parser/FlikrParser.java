package com.example.androidthreadstests.parser;

import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.utils.Constants;

import org.xml.sax.Attributes;

import java.util.LinkedList;
import java.util.List;

public class FlikrParser extends BaseParser<List<GalleryItem>> {

	private GalleryItem mGalleryItem;

    @Override
    protected LinkedList<GalleryItem> generateEmptyResult() {
        return new LinkedList<GalleryItem>();
    }

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
	protected void OnEndElement(String uri, String localName, String qName) {
		if(qName.equalsIgnoreCase("photo")){
            getResult().add(mGalleryItem);
		}
	}

    @Override
    protected void OnCharacters(char[] ch, int start, int length) {

    }
}
