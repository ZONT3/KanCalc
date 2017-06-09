package ru.zont.kancalc;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Core {
	
	static final String kmlistDir = "/kanmusuList.xml";
	
	enum UpdateState {unknown, ood, utd}
	
	private static int[] diff = new int[99];
	
	public static final String version = "0.3.0";
	public static UpdateState update = UpdateState.unknown;
	public static String newVersion = "?";
	
	static DocumentBuilder db;
	static Document kmlistFile;
	static Node root;
	static NodeList kms;
	static boolean craftscheck = false;
	
	public static ArrayList<Kanmusu> kmlist = new ArrayList<>();
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
			switch (args[i]) {
			case "-cc":
				craftscheck = true;
				break;

			default:
				break;
			}
		}
		
		db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		kmlistFile = db.parse(Core.class.getResourceAsStream(kmlistDir));
		root = kmlistFile.getDocumentElement();
		kms = root.getChildNodes();
		
		initLevels();
		initKMlist();
		if (craftscheck)
			checkCrafts();
		Ui.init();
	}

	private static void checkCrafts() {
		for (int i=0; i<kmlist.size(); i++)
			kmlist.get(i).getCraftchance();
	}

	private static void initKMlist() throws ParserConfigurationException, SAXException, IOException {
		for (int i = 0; i<kms.getLength(); i++) {
			Kanmusu kmsu = getKanmusuFromFile(i);
			if (kmsu != null)
				kmlist.add(kmsu);
		}
	}
	
	public static Kanmusu getKanmusu(String name) {
		// Хуйня какая-то, не робит
		// O, заробила, магия
		for (int i = 0; i<kmlist.size(); i++)
			if (kmlist.get(i).name.equals(name))
				return kmlist.get(i);
		return null;
	}
	
	public static Kanmusu getKanmusu(int id) {
		for (int i = 0; i<kmlist.size(); i++) {
			if (kmlist.get(i).id == id)
				return kmlist.get(i);
		}
		return null;
	}

	private static Kanmusu getKanmusuFromFile(int i) throws ParserConfigurationException, SAXException, IOException {
		Node km = kms.item(i);
		if (km.getNodeName() == "ship") {
			Element kmE = (Element) km;
			Kanmusu kanmusu = new Kanmusu(kmE.getAttribute("type"));
			NodeList kmps = km.getChildNodes();
			for (int j = 0; j < kmps.getLength(); j++) {
				if (kmps.item(j).getNodeType() == Node.ELEMENT_NODE) {
					Element kmp = (Element) kmps.item(j);
					//System.out.println(kmp.getNodeName()+"="+kmp.getTextContent());
					switch (kmp.getNodeName()) {
					case "id":
						kanmusu.id = Integer.valueOf(kmp.getTextContent());
						break;
					case "name":
						kanmusu.name = kmp.getTextContent();
						break;
					case "nameJP":
						if (kmp.hasAttribute("original")) {
							kanmusu.oname = kmp.getAttribute("original");
							kanmusu.jpname = kmp.getTextContent();
						} else {
							kanmusu.jpname = kmp.getTextContent();
							kanmusu.oname = kanmusu.jpname;
						}
						break;
					case "craft":
						kanmusu.setCraft(kmp.getTextContent());
						break;
					case "stats":
						for (int k = 0; k<kmp.getChildNodes().getLength(); k++) {
							if (kmp.getChildNodes().item(k).getNodeType() == Node.ELEMENT_NODE) {
								Element kmpst = (Element) kmp.getChildNodes().item(k);
								switch (kmpst.getNodeName()) {
								case "fuel":
									kanmusu.fuel = Integer.valueOf(kmpst.getTextContent());
									break;
								case "ammo":
									kanmusu.ammo = Integer.valueOf(kmpst.getTextContent());
									break;
								case "slots":
									kanmusu.slots = new int[Integer.valueOf(kmpst.getAttribute("count"))];
									for (int l = 0; l < kmpst.getChildNodes().getLength(); l++) {
										if (kmpst.getChildNodes().item(l).getNodeType() == Node.ELEMENT_NODE) {
											Element kmsl = (Element) kmpst.getChildNodes().item(l);
											if (kmsl.getNodeName() == "slot")
												kanmusu.slots[Integer.valueOf(kmsl.getAttribute("id"))] = Integer.valueOf(kmsl.getTextContent());
										}
									}
									break;
								default:
									break;
								}
							}
						}
						break;
					case "remodel":
						// TODO
						break;
					default:
						break;
					}
				}
			}
			return kanmusu;
			
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
		if (kanmusu.craft == "unbuildable")
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

	public static double getCCfromKCDB(Kanmusu kanmusu) {
		double res = -1;
		System.out.println("Getting CH for "+kanmusu+" ID:"+kanmusu.id+" C:"+kanmusu.craft);
		try {
			org.jsoup.nodes.Document inf = Jsoup.connect("http://kancolle-db.net/ship/"+kanmusu.id+".html").get();
			org.jsoup.select.Elements tr = inf.getElementsByTag("tr");
			for (int i=0; i<tr.size(); i++) {
				if (tr.get(i).getElementsByAttributeValue("class", "ship").size()>0) {
					if (tr.get(i).getElementsByAttributeValue("class", "ship").get(0).text().equals(kanmusu.craft)) {
						for (int j=0; j<tr.get(i).childNodeSize(); j++)
							if (tr.get(i).getElementsContainingText("%").size()>0)
								res = Double.valueOf(tr.get(i).getElementsContainingText("%").get(1).text().substring
										(0, tr.get(i).getElementsContainingText("%").get(1).text().length()-1));
					}
						
				}
				
			}
			if (res == -1)
				Ui.err("Chance of craft "+kanmusu+" hasn't found for native reciepe, defined in kanmusuList.xml ("+
						kanmusu.craft+ ")\nPlease contact developers to fix it.", "ERROR");
		} catch (IOException e) {
			Ui.err(e.getMessage(), "ERROR WITH COMMUNICATING KCDB");
		}
		System.out.println(res+"%");
		return res;
	}
	
}
