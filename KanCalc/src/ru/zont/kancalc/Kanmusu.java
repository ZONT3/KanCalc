package ru.zont.kancalc;


public class Kanmusu {
	int id;
	String type;
	String name;
	String jpname;
	String oname;
	String craft;
	private double craftchance = -1;
	private Drop drop;
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
	
	class Drop {
		String[] map;
		double[] chance;
	}

	@Override
	public String toString() {
		return jpname+" ("+name+")";
	}

	public void setCraft(String craft) {
		this.craft = craft;
	}

	public void setDrop(String[] maps) {
		drop.map = maps;
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
}
