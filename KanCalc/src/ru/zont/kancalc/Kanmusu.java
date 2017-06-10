package ru.zont.kancalc;

import java.util.ArrayList;

public class Kanmusu {
	int id;
	String type;
	String name;
	String jpname;
	String oname;
	String craft;
	private double craftchance = -1;
	boolean gotDrops = false;
	ArrayList<Map> drops = new ArrayList<>();
	int nextlevel;

	int index;
	int nid;
	int[] ids;
	int fuel;
	int ammo;
	int[] slots;
	
	Kanmusu(String type) {
		this.type = type;
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

	@Override
	public String toString() {
		return jpname+" ("+name+")";
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}
	

	public double getCraftchance() {
		if (craftchance >= 0)
			return craftchance;
		if (craft == null) {
			craftchance = 0;
			return 0;
		}
		craftchance = Core.getCCfromKCDB(this);
		return craftchance;
	}

	public Object[] getMaps() {
		if (!gotDrops) {
			drops = Core.getDropsFromKCDB(this);
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
		return res.toArray();
	}

	public Object[] getNodes(String map) {
		ArrayList<String> res = new ArrayList<>();
		for (int i=0; i<drops.size(); i++)
			if (drops.get(i).name.equals(map))
				for (int j=0; j<drops.get(i).nodes.size(); j++)
					res.add(drops.get(i).nodes.get(j).name);
		return res.toArray();
	}
}
