package com.crafter.resources;

import java.util.ArrayList;

public class Resource {
	public static class Requisite {
		public int mCount;
		public Resource mResource;
		public Requisite(Resource resource, int count)
		{
			mResource = resource;
			mCount = count;
		}
	}
	private int mID;
	private String mDisplayName;
	private String mSpriteFileName;
	private int mBaseProduction;
	private ArrayList<Requisite> mRequisites;
	
	public Resource( String name, String displayName,String spriteFile, int baseProduction, ArrayList<Requisite> requisites)
	{
		mID = name.hashCode();
		mDisplayName = displayName;
		mBaseProduction = baseProduction;
		mRequisites = requisites;
		mSpriteFileName = spriteFile;
	}
	
	@Override
	public int hashCode(){return mID;}
	public String getDisplayName() { return mDisplayName;}
	public int getBaseProduction() { return mBaseProduction;}
	public String getSpriteFileName() { return mSpriteFileName; }
	
	
}
