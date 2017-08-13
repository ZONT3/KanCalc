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
	
	public static final String version = "1.1";
	public static UpdateState update = UpdateState.unknown;
	public static String newVersion = "?";

	static boolean craftscheck = false;
	static boolean nogui = false;
	
	public static ArrayList<Kanmusu> kmlist = new ArrayList<>();
	public static ArrayList<Kanmusu> kmlistAM = new ArrayList<>();
	
	public static String[] ranks = {"S", "A", "B", "C", "D"};
	public static String[] farmMaps = {"1-5", "3-2", "4-3", "5-3"};
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		for (int i = 0; i < args.length; i++) {
//			String ar = args[i];
//			String par = "";
//			if (ar.lastIndexOf("=") >=0) {
//				par = ar.substring(ar.lastIndexOf("=")+1);
//				ar = ar.substring(0, ar.lastIndexOf("="));
//			}
			switch (args[i]) {
			case "-cval":
				craftscheck = true;
				break;
			case "-nogui":
				nogui = true;
				break;
			case "-help":
			case "-?":
				System.out.println("COMMAND LINE COMMANDS\n\n"
						+ "-cval\t\tValidate native crafts from kmlist.xml\n"
						+ "-nogui\t\tDon't initialze gui. If future operations hasn't defined, app will quit\n"
						+ "-add=%id%\tTemporary adds kanmusu to list with given id, nid=-1 and with no other proporties\n"
						+ "-cc=%id%\tGet craft chance of kanmusu with given id\n"
						+ "-ccn=%name%\tGet craft chance of kanmusu with given name\n"
						+ "-dl=%id%\tGet drop list for kanmusu with given id\n"
						+ "-dln=%name%\tGet drop list for kanmusu with given name\n"
						+ "-cdl=%craft%\tGet list of kanmusu wich crafts with given reciepe\n"
						+ "-help/-?\tYou know.\n"
						+ "\n");
			default:
				break;
			}
		}
		
		
		
		initLevels();
		kmlist = KMParser.getKMList();
		kmlistAM = KMParser.getKMList(true);
		if (craftscheck)
			checkCrafts();
		kmsort(kmlist);
		kmsort2(kmlistAM);
		if (!nogui)
			Ui.init();
		
		
		
		for (int i = 0; i < args.length; i++) {
			String ar = args[i];
			String par = "";
			if (ar.lastIndexOf("=") >=0) {
				par = ar.substring(ar.lastIndexOf("=")+1);
				ar = ar.substring(0, ar.lastIndexOf("="));
			}
			switch (ar) {
			case "-add":
				kmlist.add(new Kanmusu("ID"+par, Integer.valueOf(par), -1));
				break;
			case "-cc":
				if (getKanmusu(Integer.valueOf(par), kmlist) != null)
					KCDB.getCC(getKanmusu(Integer.valueOf(par), kmlist), getKanmusu(Integer.valueOf(par), kmlist).craft);
				else
					System.out.println("Given Kanmusu doesn't exists in our DB");
				break;
			case "-ccn":
				if (getKanmusu(par, kmlist) != null)
					KCDB.getCC(getKanmusu(par, kmlist), getKanmusu(par, kmlist).craft);
				else
					System.out.println("Given Kanmusu doesn't exists in our DB");
				break;
			case "-dl":
				KCDB.getDrops(getKanmusu(Integer.valueOf(par), kmlist));
				break;
			case "-dln":
				KCDB.getDrops(getKanmusu(par, kmlist));
				break;
			case "-cdl":
				KCDB.getCraftDrops(par);
				break;
			default:
				break;
			}
		}
	}
	
	

	public static void kmsort(ArrayList<Kanmusu> list) {
		list.sort(new Comparator<Kanmusu>() {
			@Override
			public int compare(Kanmusu arg0, Kanmusu arg1) {
				return arg0.nid-arg1.nid;
			}
		});
	}
	
	public static void kmsort2(ArrayList<Kanmusu> list) {
		list.sort(new Comparator<Kanmusu>() {
			@Override
			public int compare(Kanmusu arg0, Kanmusu arg1) {
				return arg0.name.compareTo(arg1.name);
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

	public static int getBattlesLeft(String lvls, String map, String rank) {
		double basexp = 0;
		switch (map) {
		case "3-2":
			basexp = 320;
			break;
		case "1-5":
			basexp = 150;
			break;
		case "4-3":
			basexp = 330;
			break;
		case "5-3":
			basexp = 400;
			break;
		default:
			break;
		}
		double rankmult = 1;
		switch (rank) {
		case "S":
			rankmult = 1.2;
			break;
		case "A":
			rankmult = 1;
			break;
		case "B":
			rankmult = 1;
			break;
		case "C":
			rankmult = 0.8;
			break;
		case "D":
			rankmult = 0.7;
			break;
		default:
			break;
		}
		
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
		
		double cxp = 0;
		double rxp = 0;
		for (int ii=0; ii<slvl; ii++)
			cxp+=diff[ii];
		for (int ii=0; ii<llvl; ii++)
			rxp+=diff[ii];
		return (int) ((rxp-cxp)/(basexp*rankmult*3.0))+1;
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
	
	public static class Consumption {
		int fuel = 0;
		int ammo = 0;
	}

	public static Consumption getConsumption(Kanmusu[] setup, String map) {
		Consumption res = new Consumption();
		for (int i = 0; i < setup.length; i++) {
			res.fuel += setup[i].fuel*0.2;
			res.ammo += setup[i].ammo*0.2;
		}
		return res;
	}
	
}
