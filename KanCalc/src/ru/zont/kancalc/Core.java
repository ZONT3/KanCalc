package ru.zont.kancalc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.Format;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Core {
	
	enum UpdateState {unknown, ood, utd}
	
	private static int[] diff = new int[99];
	
	public static final String version = "0.3.0";
	public static UpdateState update = UpdateState.unknown;
	public static String newVersion = "?";
	
	public static ArrayList<Kanmusu> kmlist = new ArrayList<>();
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		initLevels();
		initKMlist();
		Ui.init();
	}

	private static void initKMlist() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document kmlistFile = db.parse("kanmusuList.xml");
		Node root = kmlistFile.getDocumentElement();
		NodeList kms = root.getChildNodes();
		for (int i = 0; i<kms.getLength(); i++) {
			Node km = kms.item(i);
			if (km.getNodeName() == "ship") {
				Kanmusu kanmusu = new Kanmusu();
				NodeList kmps = km.getChildNodes();
				for (int j = 0; j < kmps.getLength(); j++) {
					Node kmp = kmps.item(j);
					if (kmp.getNodeType() == Node.ATTRIBUTE_NODE) {
						switch (kmp.getNodeName()) {
						case "id":
							kanmusu.id = Integer.valueOf(kmp.getTextContent());
							break;
						case "name":
							kanmusu.name = kmp.getTextContent();
							break;
						case "nameJP":
							kanmusu.jpname = kmp.getTextContent();
							break;
						case "craft":
							kanmusu.setCraft(kmp.getTextContent());
							break;
						default:
							break;
						}
					}
				}
			}
		}
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
	
	public static String getHTTPData(String pageAddress, String codePage) throws Exception {
        StringBuilder sb = new StringBuilder();
        URL pageURL = new URL(pageAddress);
        URLConnection uc = pageURL.openConnection();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        uc.getInputStream(), codePage));
        try {
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }         
        } finally {
            br.close();
        }
        return sb.toString();
    }
	
}
