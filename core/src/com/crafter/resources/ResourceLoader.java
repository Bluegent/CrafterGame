package com.crafter.resources;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.JsonValue;
import com.crafter.resources.Resource.Requisite;
import com.crafter.utils.JSONValues;

public class ResourceLoader {
	private static String DEFAULT_SPRITE = "default.png";
	private HashMap<Integer, Resource> mCatalog;
	public ResourceLoader(HashMap<Integer, Resource> catalog){
		mCatalog = catalog;
	}
	
	public void parseResource(JsonValue resources) {
		for(int i = 0; i< resources.size; ++i) {
			getResource(resources.get(i));
		}
	}
	
	public int getIntValue(JsonValue value, String name, int defautValue)
	{
		int res = defautValue;
		try {
			res = value.getInt(name);
		}catch(Exception e){}
		return res;
	}
	
	public String getStringValue(JsonValue value, String name, String defautValue) {
		String res = defautValue;
		try {
			res = value.getString(name);
		}catch(Exception e){}
		return res;
	}	
	
	public Resource getResource( JsonValue value){
		String displayName = value.getString(JSONValues.NAME);
		String spriteFile = getStringValue(value,JSONValues.SPRITE_FILE,DEFAULT_SPRITE);
		int production = getIntValue(value,JSONValues.PRODUCTION,1);
		int duration = getIntValue(value,JSONValues.PRODUCTION_TIME,1);
		int tier = getIntValue(value,JSONValues.TIER,1);
		Resource res =  new Resource(value.name,displayName,spriteFile,production,getRequisites(value.get(JSONValues.REQUISITES)),tier,duration);
		mCatalog.put(res.hashCode(),res);
		return res;
	}
	
	public ArrayList<Requisite> getRequisites(JsonValue array)
	{
		ArrayList<Requisite> requisites = new ArrayList<Requisite>();
		if(array == null)
			return requisites;
		
		for(int i = 0; i< array.size; ++i) {
			int hash = array.get(i).getString(JSONValues.REQUISITE_NAME).hashCode();
			int count = getIntValue(array.get(i),JSONValues.REQUISITE_COUNT,1);
			Resource res = mCatalog.get(hash);
			requisites.add(new Requisite(res,count));
			
		}
		return requisites;
		
	}
}
