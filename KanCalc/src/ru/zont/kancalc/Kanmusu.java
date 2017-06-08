package ru.zont.kancalc;

public class Kanmusu {
	int id;
	String type;
	String name;
	String jpname;
	String craft;
	double craftchance;
	Drop drop;
	int nextlevel;
	int index;
	int 
	
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
	
	
}
