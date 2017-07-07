package ru.zont.kancalc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ru.zont.kancalc.Kanmusu.Map;

public class Core {	
	enum UpdateState {unknown, ood, utd}
	
	private static int[] diff = new int[99];
	
	public static final String version = "0.4.1";
	public static UpdateState update = UpdateState.unknown;
	public static String newVersion = "?";

	static boolean craftscheck = false;
	static int add = -1;
	
	public static ArrayList<Kanmusu> kmlist = new ArrayList<>();
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		for (int i = 0; i < args.length; i++) {
			String ar = args[i];
			String par = "";
			if (ar.lastIndexOf("=") >=0) {
				par = ar.substring(ar.lastIndexOf("=")+1);
				ar = ar.substring(0, ar.lastIndexOf("="));
			}
			System.out.println("arg="+ar+" par="+par);
			switch (ar) {
			case "-cc":
				craftscheck = true;
				break;
			case "-add":
				add = Integer.valueOf(par);
				break;
			default:
				break;
			}
		}
		
		
		
		initLevels();
		kmlist = KMParser.getKMList();
		if (craftscheck)
			checkCrafts();
		if (add != -1)
			kmlist.add(new Kanmusu("Added by args", add, -1));
		kmsort(kmlist);
		Ui.init();
	}
	
	

	public static void kmsort(ArrayList<Kanmusu> list) {
		list.sort(new Comparator<Kanmusu>() {
			@Override
			public int compare(Kanmusu arg0, Kanmusu arg1) {
				return arg0.nid-arg1.nid;
			}
		});
	}



	private static void checkCrafts() {
		Ui.CCCi();
		for (int i=0; i<kmlist.size(); i++) {
			double ch = kmlist.get(i).getCraftchance();
			Ui.CCCc(kmlist.size(), i, "N:\""+kmlist.get(i).name+"\" C:"+kmlist.get(i).craft+" V:"+ch);
		}
		Ui.CCCe();
	}
	
	public static Kanmusu getKanmusu(String name, ArrayList<Kanmusu> list) {
		// Хуйня какая-то, не робит
		// O, заробила, магия
		for (int i = 0; i<list.size(); i++)
			if (list.get(i).name.equals(name) || list.get(i).jpname.equals(name) || list.get(i).oname.equals(name))
				return list.get(i);
		return null;
	}
	
	public static Kanmusu getKanmusu(int id, ArrayList<Kanmusu> list) {
		for (int i = 0; i<list.size(); i++) {
			if (list.get(i).id == id)
				return list.get(i);
		}
		return null;
	}

	private static void initLevels() {
		for (int i=0; i<51; i++)
			diff[i] = i*100;
		int c = 5200;
		for (int i=51; i<60; i++) {
			diff[i] = c;
			c+=200;
		}
		for (int i=60; i<70; i++) {
			diff[i] = c;
			c+=300;
		}
		for (int i=70; i<80; i++) {
			diff[i] = c;
			c+=400;
		}
		for (int i=80; i<90; i++) {
			diff[i] = c;
			c+=500;
		}
		diff[90] = c;
		diff[91] = 20000;
		diff[92] = 22000;
		diff[93] = 25000;
		diff[94] = 30000;
		diff[95] = 40000;
		diff[96] = 60000;
		diff[97] = 90000;
		diff[98] = 148500;
	}

	public static double getSumChance(double chance, int tries) {
		chance /= 100;
		double mtch = chance;
		for (int i=0; i<tries-1; i++)
			chance = (mtch+chance-mtch*chance);
		chance*=100;
		return chance;
	}

	public static int getSortiresLeft(String lvls, int basexp, double rankmult) {
		int slvl;
		int llvl;
		int i = 0;
		while (true) {
			if(lvls.charAt(i) == '-')
				break;
			i++;
		}
		slvl = Integer.parseInt(lvls.substring(0, i));
		llvl = Integer.parseInt(lvls.substring(i+1));
		
		int cxp = 0;
		int rxp = 0;
		for (int ii=0; ii<slvl; ii++)
			cxp+=diff[ii];
		for (int ii=0; ii<llvl; ii++)
			rxp+=diff[ii];
		return (int) ((rxp-cxp)/(basexp*rankmult*3));
	}

	public static String getPrice(int tries, Kanmusu kanmusu) {
		if (kanmusu.craft.equals("unbuildable"))
			return kanmusu.craft;
		int i = 0;
		while (kanmusu.craft.charAt(i) != '/')
				i++;
		int fuel = Integer.parseInt(kanmusu.craft.substring(0, i));
		int ii = i+1;
		while (kanmusu.craft.charAt(ii) != '/')
			ii++;
		int ammo = Integer.parseInt(kanmusu.craft.substring(i+1, ii));
		i = ii+1;
		while (kanmusu.craft.charAt(i) != '/')
			i++;
		int steel = Integer.parseInt(kanmusu.craft.substring(ii+1, i));
		int boux = Integer.parseInt(kanmusu.craft.substring(i+1));
		return fuel*tries+"/"+ammo*tries+"/"+steel*tries+"/"+boux*tries;
	}


	static int mapPresent(Kanmusu.Map map, ArrayList<Map> list) {
		for (int i=0; i<list.size(); i++) {
			if (map.id.equals(list.get(i).id))
				return i;
		}
		return -1;
	}
	
}
