package ru.zont.kancalc;

import java.io.IOException;
import java.util.ArrayList;

public class Kanmusu {
	int id = -1;
	String type = "?";
	String name;
	String jpname;
	String oname;
	String cls;
	String craft;
	private double craftchance = -1;
	boolean gotDrops = false;
	ArrayList<Map> drops = new ArrayList<>();
	int nextlevel;
	ArrayList<Craft> crafts = new ArrayList<>();

	int index;
	int nid;
	int[] ids;
	int fuel;
	int ammo;
	int[] slots;
	
	Kanmusu(String type) {
		this.type = type;
	}
	
	Kanmusu(String name, int id, int nid) {
		this.name = name;
		this.jpname = "?";
		this.oname = "?";
		this.id = id;
		this.nid = nid;
	}
	
	public static class Map {
		String id;
		String name;
		ArrayList<Node> nodes = new ArrayList<>();
		
		public static class Node {
			String name;
			double chance;
		}
	}
	
	public static class Craft {
		String reciepe = "0/0/0/0";
		double chance = 0;
		int entries = -1;
	}

	@Override
	public String toString() {
		return jpname+" ("+name+")";
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}
	
	public Craft getCraft(String craft) {
		for (int i=0; i<crafts.size(); i++)
			if (craft.equals(crafts.get(i).reciepe))
				return crafts.get(i);
		return new Craft();
	}

	public double getCraftchance() {
		if (craftchance >= 0)
			return craftchance;
		if (craft == null) {
			craftchance = 0;
			return 0;
		}
		craftchance = KCDB.getCC(this, craft);
		return craftchance;
	}

	public ArrayList<String> getMaps() throws IOException {
		if (!gotDrops) {
			drops = KCDB.getDrops(this);
			gotDrops = true;
		}
		ArrayList<String> res = new ArrayList<>();
		for (int i=0; i<drops.size(); i++) {
			String map = drops.get(i).name;
			boolean was = false;
			for (int j=0; j<res.size(); j++)
				if (res.get(j).equals(map))
					was = true;
			if (!was)
				res.add(map);
		}
		return res;
	}

	public ArrayList<String> getNodes(String map) {
		ArrayList<String> res = new ArrayList<>();
		for (int i=0; i<drops.size(); i++)
			if (drops.get(i).name.equals(map))
				for (int j=0; j<drops.get(i).nodes.size(); j++)
					res.add(drops.get(i).nodes.get(j).name);
		return res;
	}

	public double getDropChance(String map, String node) {
		for (int i=0; i<drops.size(); i++)
			for (int j=0; j<drops.get(i).nodes.size(); j++)
				if (drops.get(i).nodes.get(j).name.equals(node) && drops.get(i).name.equals(map))
					return drops.get(i).nodes.get(j).chance;
		return -1;
	}
}
