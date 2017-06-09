package ru.zont.kancalc;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		System.out.println("Getting CH for "+this+" ID:"+id+" C:"+craft);
		try {
			Document inf = Jsoup.connect("http://kancolle-db.net/ship/"+id+".html").get();
			Elements tr = inf.getElementsByTag("tr");
			for (int i=0; i<tr.size(); i++) {
				if (tr.get(i).getElementsByAttributeValue("class", "ship").size()>0) {
					if (tr.get(i).getElementsByAttributeValue("class", "ship").get(0).text().equals(craft)) {
						for (int j=0; j<tr.get(i).childNodeSize(); j++)
							if (tr.get(i).getElementsContainingText("%").size()>0)
								craftchance = Double.valueOf(tr.get(i).getElementsContainingText("%").get(1).text().substring
										(0, tr.get(i).getElementsContainingText("%").get(1).text().length()-1));
					}
						
				}
				
			}
			if (craftchance == -1)
				JOptionPane.showMessageDialog(null, "Chance of craft hasn't found for native reciepe, defined in kanmusuList.xml ("+
						craft+ ")\nPlease contact developers to fix it.", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR WITH COMMUNICATING KCDB", JOptionPane.ERROR_MESSAGE);
		}
		System.out.println(craftchance+"%");
		return craftchance;
	}
}
