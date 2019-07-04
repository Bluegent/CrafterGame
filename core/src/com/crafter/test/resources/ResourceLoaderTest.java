package com.crafter.test.resources;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.crafter.resources.Resource;
import com.crafter.resources.Resource.Requisite;
import com.crafter.resources.ResourceLoader;

public class ResourceLoaderTest {

	private ResourceLoader mLoader;
	HashMap<Integer,Resource> mCatalog;
	
	@Before
	public void setUp(){
		mCatalog = new HashMap<Integer,Resource>();
		mLoader = new ResourceLoader(mCatalog);
	}
	
	@Test
	public void testRequisite_Success() {
		String JSON = "{\"test\":[{ \"resource\": \"iron_bar\", \"count\": 3 },{ \"resource\": \"wood\", \"count\": 5 }]}";
		JsonValue array = new JsonReader().parse(JSON).get("test");
		ArrayList<Requisite> reqs = mLoader.getRequisites(array);
		assertEquals(2,reqs.size());
		assertEquals(3,reqs.get(0).mCount);
		assertEquals(5,reqs.get(1).mCount);	
		assertEquals(mCatalog.get("iron_bar".hashCode()),reqs.get(0).mResource);
		assertEquals(mCatalog.get("iron_bar".hashCode()),reqs.get(1).mResource);
	}
	
	@Test 
	public void testRequisite_Emtpy() {
		String JSON = "{\"test\":[]}";
		JsonValue array = new JsonReader().parse(JSON).get("test");
		ArrayList<Requisite> reqs = mLoader.getRequisites(array);
		assertEquals(0,reqs.size());
	}
	
	@Test
	public void testLoader_Success() {
		String JSON = "{\"iron_bar\": {\"name\": \"Iron Bar\",\"sprite\": \"iron.png\",\"production\": 1,\"requisites\": [{ \"resource\": \"iron_ore\", \"count\": 2 }]}}";
		JsonValue value = new JsonReader().parse(JSON).get("iron_bar");	
		Resource resource = mLoader.getResource(value);
		assertNotNull(resource);
		assertEquals("iron_bar".hashCode(),resource.hashCode());
		assertEquals("Iron Bar",resource.getDisplayName());
		assertEquals(1,resource.getBaseProduction());
		assertEquals("iron.png",resource.getSpriteFileName());
	}
	
	@Test
	public void testLoader_WholeDocument() {
		JsonValue value = new JsonReader().parse(new FileHandle("assets/JSON/test/resources.json"));
		mLoader.parseResource(value);
		assertEquals(4,mCatalog.size());
		assertEquals("Iron Bar",mCatalog.get("iron_bar".hashCode()).getDisplayName());		
	}

}
