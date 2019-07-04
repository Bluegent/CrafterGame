package com.crafter.resources;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.JsonValue;
import com.crafter.resources.Resource.Requisite;
import com.crafter.utils.JSONValues;

public class ResourceLoader {
	
	private HashMap<Integer, Resource> mCatalog;
	public ResourceLoader(HashMap<Integer, Resource> catalog){
		mCatalog = catalog;
	}
	
	public void parseResource(JsonValue resources) {
		for(int i = 0; i< resources.size; ++i) {
			getResource(resources.get(i));
		}
	}
	
	public Resource getResource( JsonValue value){
		//name, sprite, production, requisites
		String displayName = value.getString(JSONValues.NAME);
		String spriteFile = value.getString(JSONValues.SPRITE_FILE);
		int production = value.getInt(JSONValues.PRODUCTION);
		Resource res =  new Resource(value.name,displayName,spriteFile,production,getRequisites(value.get(JSONValues.REQUISITES)));
		mCatalog.put(res.hashCode(),res);
		return res;
	
	}
	
	public ArrayList<Requisite> getRequisites(JsonValue array)
	{
		ArrayList<Requisite> requisites = new ArrayList<Requisite>();
		for(int i = 0; i< array.size; ++i) {
			int hash = array.get(i).getString(JSONValues.REQUISITE_NAME).hashCode();
			int count = array.get(i).getInt(JSONValues.REQUISITE_COUNT);
			Resource res = mCatalog.get(hash);
			requisites.add(new Requisite(res,count));
			
		}
		return requisites;
		
	}
}
