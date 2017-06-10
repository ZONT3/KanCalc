package ru.zont.kancalc;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Ui {
	
	static private double farm_rankmult = 1;
	
	static JFrame frame_main = new JFrame("ChanceCalc v. " + Core.version);
	static JButton bt_main_smuch = new JButton("SUM");
	static JButton bt_main_farm = new JButton("XP Farm");
	static JButton bt_main_cc = new JButton("Craft Chance");
	static JButton bt_main_dc = new JButton("Drop Chance");
	static JTextPane tp_newver = new JTextPane();
	
	static JFrame frame_sumch = new JFrame("SUM chance");
	static JTextField tf_tries = new JTextField("tries..");
	static JTextField tf_chance = new JTextField("50");
	static JTextPane tp_prec = new JTextPane();
	static JButton bt_go = new JButton("GO");
	static JTextPane tp_result = new JTextPane();
	
	static JFrame frame_cc = new JFrame("Chance of craft");
	static JComboBox<Object> cb_cc_ship = new JComboBox<>(Core.kmlist.toArray());
	static JTextField tf_cc_tries = new JTextField("Tries");
	static JTextPane tp_cc_craft = new JTextPane();
	static JButton bt_cc_go = new JButton("GO");
	static JTextPane tp_cc_price = new JTextPane();
	static JTextPane tp_cc_chance = new JTextPane();
	
	static String[] ranks = {"S", "A", "B", "C", "D"};
	static JFrame frame_farm = new JFrame("FarmCalc");
	static JButton bt_farm_go = new JButton("GO");
	static JTextField tf_farm_lvls = new JTextField("1-75");
	static JTextField tf_farm_basexp = new JTextField("320");
	static JComboBox<String> mb_farm_rank = new JComboBox<>(ranks);
	static JTextField tf_farm_cons_t = new JTextField("20");
	static JTextField tf_farm_cons_a = new JTextField("30");
	static JTextPane tp_farm_result = new JTextPane();
	
	static String[] defaultMaps = {"---"};
	static JFrame frame_dc = new JFrame("Chance of drop");
	static JComboBox<Object> cb_dc_ship = new JComboBox<>(Core.kmlist.toArray());
	static JTextField tf_dc_tries = new JTextField("Tries");
	static JComboBox<Object> cb_dc_maps = new JComboBox<>(defaultMaps);
	static JComboBox<Object> cb_dc_nodes = new JComboBox<>(defaultMaps);
	static JTextPane tp_dc_result = new JTextPane();
	static JButton bt_dc_go = new JButton("GO");
	
	static eHandler listener = new eHandler();
	static weHandler wlistener = new weHandler();

	public static void init() {
		
		
		frame_main.setLayout(new FlowLayout());
		frame_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_main.setSize(300, 130);
		frame_main.setResizable(false);
		frame_main.setVisible(true);
		frame_main.setLocationRelativeTo(null);
		bt_main_smuch.addActionListener(listener);
		bt_main_cc.addActionListener(listener);
		bt_main_farm.addActionListener(listener);
		bt_main_dc.addActionListener(listener);
		tp_newver.setText("Update: "+getUpdateState());
		tp_newver.setEditable(false);
		
		Box b11 = Box.createHorizontalBox();
		b11.add(Box.createHorizontalStrut(150));
		b11.add(bt_main_smuch);
		b11.add(bt_main_farm);
		b11.add(Box.createHorizontalStrut(150));
		Box b12 = Box.createHorizontalBox();
		b12.add(Box.createHorizontalStrut(150));
		b12.add(bt_main_cc);
		b12.add(bt_main_dc);
		b12.add(Box.createHorizontalStrut(150));
		Box b13 = Box.createHorizontalBox();
		b13.add(Box.createHorizontalStrut(150));
		b13.add(tp_newver);
		b13.add(Box.createHorizontalStrut(150));
		frame_main.add(b11);
		frame_main.add(b12);
		frame_main.add(b13);
		
		//---------------------------------------CC
		frame_cc.setLayout(new FlowLayout());
		frame_cc.addWindowListener(wlistener);
		frame_cc.setSize(450, 130);
		frame_cc.setResizable(false);
		tp_result.setEditable(false);
		tp_cc_craft.setEditable(false);
		tp_cc_chance.setEditable(false);
		tp_cc_price.setEditable(false);
		bt_cc_go.addActionListener(listener);
		cb_cc_ship.addActionListener(listener);
		cb_cc_ship.setSelectedItem(Core.getKanmusu("Yuudachi"));
		Box cb1 = Box.createHorizontalBox();
		cb1.add(Box.createHorizontalStrut(300));
		cb1.add(cb_cc_ship);
		cb1.add(Box.createHorizontalStrut(300));		
		Box cb2 = Box.createHorizontalBox();
		cb2.add(Box.createHorizontalStrut(300));
		cb2.add(tf_cc_tries);
		cb2.add(tp_cc_craft);
		cb2.add(Box.createHorizontalStrut(300));
		Box cb3 = Box.createHorizontalBox();
		cb3.add(Box.createHorizontalStrut(300));
		cb3.add(bt_cc_go);
		cb3.add(tp_cc_chance);
		cb3.add(tp_cc_price);
		cb3.add(Box.createHorizontalStrut(300));
		frame_cc.add(cb1);
		frame_cc.add(cb2);
		frame_cc.add(cb3);
		
		//-----------------------------------FARM
		frame_farm.setLayout(new FlowLayout());
		frame_farm.addWindowListener(wlistener);
		frame_farm.setSize(300, 140);
		frame_farm.setResizable(false);
		bt_farm_go.addActionListener(listener);
		tp_farm_result.setEditable(false);
		Box fb1 = Box.createHorizontalBox();
		fb1.add(Box.createHorizontalStrut(150));
		fb1.add(tf_farm_lvls);
		fb1.add(tf_farm_basexp);
		fb1.add(mb_farm_rank);
		fb1.add(Box.createHorizontalStrut(150));
		Box fb2 = Box.createHorizontalBox();
		fb2.add(Box.createHorizontalStrut(150));
		fb2.add(bt_farm_go);
		fb2.add(tp_farm_result);
		fb2.add(Box.createHorizontalStrut(150));
		Box fb3 = Box.createHorizontalBox();
		fb3.add(Box.createHorizontalStrut(150));
		fb3.add(tf_farm_cons_t);
		fb3.add(tf_farm_cons_a);
		fb3.add(Box.createHorizontalStrut(150));
		frame_farm.add(fb1);
		frame_farm.add(fb3);
		frame_farm.add(fb2);
		
		//---------------------------------------SMUCH
		frame_sumch.setLayout(new FlowLayout());
		frame_sumch.addWindowListener(wlistener);
		frame_sumch.setSize(300, 130);
		frame_sumch.setResizable(false);
		tp_prec.setText("%");
		tp_prec.setEditable(false);
		Box b1 = Box.createHorizontalBox();
		b1.add(Box.createHorizontalStrut(150));
		b1.add(tf_chance);
		b1.add(tp_prec);
		b1.add(Box.createHorizontalStrut(150));
		Box b2 = Box.createHorizontalBox();
		b2.add(Box.createHorizontalStrut(150));
		b2.add(tf_tries);
		b2.add(Box.createHorizontalStrut(150));
		Box b3 = Box.createHorizontalBox();
		b3.add(Box.createHorizontalStrut(150));
		b3.add(bt_go);
		b3.add(Box.createHorizontalStrut(5));
		b3.add(tp_result);
		b3.add(Box.createHorizontalStrut(150));
		bt_go.addActionListener(listener);
		frame_sumch.add(b1);
		frame_sumch.add(b2);
		frame_sumch.add(b3);
		
		//---------------------------------------------DC
		frame_dc.setLayout(new FlowLayout());
		frame_dc.addWindowListener(wlistener);
		frame_dc.setSize(350, 150);
		frame_dc.setResizable(false);
		tp_dc_result.setEditable(false);
		bt_dc_go.addActionListener(listener);
		cb_dc_maps.addActionListener(listener);
		cb_dc_ship.addActionListener(listener);
		cb_dc_ship.setSelectedItem(Core.getKanmusu("Yuudachi"));
		Box dc1 = Box.createHorizontalBox();
		dc1.add(Box.createHorizontalStrut(350));
		dc1.add(cb_dc_ship);
		dc1.add(Box.createHorizontalStrut(350));
		Box dc2 = Box.createHorizontalBox();
		dc2.add(Box.createHorizontalStrut(350));
		dc2.add(cb_dc_maps);
		dc2.add(Box.createHorizontalStrut(350));
		Box dc3 = Box.createHorizontalBox();
		dc3.add(Box.createHorizontalStrut(350));
		dc3.add(tf_dc_tries);
		dc3.add(cb_dc_nodes);
		dc3.add(Box.createHorizontalStrut(350));
		Box dc4 = Box.createHorizontalBox();
		dc4.add(Box.createHorizontalStrut(350));
		dc4.add(tp_dc_result);
		dc4.add(bt_dc_go);
		dc4.add(Box.createHorizontalStrut(350));
		frame_dc.add(dc1);
		frame_dc.add(dc2);
		frame_dc.add(dc3);
		frame_dc.add(dc4);
	}
	
	private static String getUpdateState() {
		switch (Core.update) {
		case unknown:
			return Core.newVersion;
		case ood:
			return "New "+Core.newVersion+" avaliable";
		case utd:
			return "None";
		default:
			return "ERROR";
		}
	}

	static class eHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Kanmusu kanmusu = Core.getKanmusu("Yuudachi");
			
			if (e.getSource() == bt_go) {
				try {
					tp_result.setText((int)Core.getSumChance(Integer.parseInt(tf_chance.getText()), Integer.parseInt(tf_tries.getText()))+"%");
				} catch(Exception er) {
					JOptionPane.showMessageDialog(null, er, "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getSource() == bt_main_smuch || e.getSource() == bt_main_cc || e.getSource() == bt_main_farm || e.getSource() == bt_main_dc) {
				JFrame frame = null;
				if (e.getSource() == bt_main_smuch)
					frame = frame_sumch;
				else if (e.getSource() == bt_main_cc)
					frame = frame_cc;
				else if (e.getSource() == bt_main_farm)
					frame = frame_farm;
				else if (e.getSource() == bt_main_dc)
					frame = frame_dc;
				frame.setLocation(frame_main.getLocation());
				frame_main.setVisible(false);
				frame.setVisible(true);
			} else if (e.getSource() == bt_farm_go) {
				switch (mb_farm_rank.getSelectedIndex()) {
				case 0:
					farm_rankmult = 1.2;
					break;
				case 1:
					farm_rankmult = 1;
					break;
				case 2:
					farm_rankmult = 1;
					break;
				case 3:
					farm_rankmult = 0.8;
					break;
				case 4:
					farm_rankmult = 0.7;
					break;
				default:
					break;
				}
				int sts = Core.getSortiresLeft(tf_farm_lvls.getText(), Integer.parseInt(tf_farm_basexp.getText()), farm_rankmult);
				tp_farm_result.setText(sts+"sts; "+sts*Integer.parseInt(tf_farm_cons_t.getText())+"/"+sts*Integer.parseInt(tf_farm_cons_a.getText())+" F/A");
			} else if (e.getSource() == bt_cc_go) {
				kanmusu = (Kanmusu) cb_cc_ship.getSelectedItem();
				if (kanmusu.craft != "unbuildable") {
					double chance = Core.getSumChance(kanmusu.getCraftchance(), Integer.parseInt(tf_cc_tries.getText()));
					chance = (double)Math.rint(1000*chance)/1000;
					tp_cc_price.setText(Core.getPrice(Integer.parseInt(tf_cc_tries.getText()), kanmusu));
					tp_cc_chance.setText(chance+"%");
				}
			} else if (e.getSource() == cb_cc_ship) {
				kanmusu = (Kanmusu) cb_cc_ship.getSelectedItem();
				tp_cc_craft.setText(kanmusu.craft);
				if (kanmusu.craft == "unbuildable") {
					tp_cc_price.setText("-----------");
					tp_cc_chance.setText("-----------");
				}
			} else if (e.getSource() == cb_dc_ship) {
				kanmusu = (Kanmusu) cb_dc_ship.getSelectedItem();
				cb_dc_maps.removeAllItems();
				buildList(cb_dc_maps, kanmusu.getMaps());
			} else if (e.getSource() == cb_dc_maps) {
				kanmusu = (Kanmusu) cb_dc_ship.getSelectedItem();
				String map = (String) cb_dc_maps.getSelectedItem();
				cb_dc_nodes.removeAllItems();
				buildList(cb_dc_nodes, kanmusu.getNodes(map));
			} else if (e.getSource() == bt_dc_go) {
				kanmusu = (Kanmusu) cb_dc_ship.getSelectedItem();
				tp_dc_result.setText(kanmusu.getDropChance(cb_dc_maps.getSelectedItem().toString(), cb_dc_nodes.getSelectedItem().toString())+"%");
			}
		}

		private void buildList(JComboBox<Object> list, Object[] content) {
			Arrays.sort(content);
			for (int i = 0; i < content.length; i++)
				list.addItem(content[i]);
		}
		
	}

	static class weHandler implements WindowListener {

		@Override
		public void windowActivated(WindowEvent e) {}

		@Override
		public void windowClosed(WindowEvent e) {}

		@Override
		public void windowClosing(WindowEvent e) {
			JFrame fr = (JFrame) e.getSource();
			frame_main.setLocation(fr.getLocation());
			frame_main.setVisible(true);
		}

		@Override
		public void windowDeactivated(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowIconified(WindowEvent e) {}

		@Override
		public void windowOpened(WindowEvent e) {}	
	}
	
	public static void err(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
