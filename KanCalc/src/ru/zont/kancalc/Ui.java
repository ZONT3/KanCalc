package ru.zont.kancalc;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Ui {
	static boolean connectErr = false;

	static private double farm_rankmult = 1;

	static JFrame ccc_frame = new JFrame("Validating crafts..");
	static JProgressBar ccc_pb = new JProgressBar();
	static JTextPane ccc_tp_ = new JTextPane();

	static JFrame main_frame = new JFrame("ChanceCalc v." + Core.version);
	static JButton main_bt_smuch = new JButton("SUM");
	static JButton main_farm_bt = new JButton("XP Farm");
	static JButton main_bt_cc = new JButton("Craft Chance");
	static JButton main_bt_dc = new JButton("Drop Chance");
	static JButton main_bt_ci = new JButton("Craft Droplist");
	static JTextPane tp_newver = new JTextPane();

	static JFrame sumch_frame = new JFrame("SUM chance");
	static JTextField sumch_tf_tries = new JTextField("tries..");
	static JTextField sumch_tf_chance = new JTextField("50");
	static JTextPane sumch_tp_prec = new JTextPane();
	static JButton sumch_bt_go = new JButton("GO");
	static JTextPane sumch_tp_result = new JTextPane();

	static JFrame cc_frame = new JFrame("Chance of craft");
	static JComboBox<Object> cc_cb_ship = new JComboBox<>(Core.kmlist.toArray());
	static JTextField cc_tf_tries = new JTextField("Tries");
	static JTextPane cc_tp_craft = new JTextPane();
	static JButton cc_bt_go = new JButton("GO");
	static JTextPane cc_tp_chance = new JTextPane();

	static String[] ranks = { "S", "A", "B", "C", "D" };
	static JFrame farm_frame = new JFrame("FarmCalc");
	static JButton farm_bt_go = new JButton("GO");
	static JTextField farm_tf_lvls = new JTextField("1-75");
	static JTextField farm_tf_basexp = new JTextField("320");
	static JComboBox<String> mb_farm_rank = new JComboBox<>(ranks);
	static JTextField farm_tf_cons_t = new JTextField("20");
	static JTextField farm_tf_cons_a = new JTextField("30");
	static JTextPane farm_tp_result = new JTextPane();

	static String[] defaultMaps = { "---" };
	static JFrame dc_frame = new JFrame("Chance of drop");
	static JComboBox<Object> cb_dc_ship = new JComboBox<>(Core.kmlist.toArray());
	static JTextField tf_dc_tries = new JTextField("Tries");
	static JComboBox<Object> cb_dc_maps = new JComboBox<>(defaultMaps);
	static JComboBox<Object> cb_dc_nodes = new JComboBox<>(defaultMaps);
	static JTextPane dc_tp_result = new JTextPane();
	static JButton dc_bt_go = new JButton("GO");
	// static JTextPane dc_tp_chance1 = new JTextPane();

	static JFrame ci_frame = new JFrame("Craft Droplst");
	static JTextField ci_tf_fuel = new JTextField(3);
	static JTextField ci_tf_ammo = new JTextField(3);
	static JTextField ci_tf_steel = new JTextField(3);
	static JTextField ci_tf_boux = new JTextField(3);
	static JComboBox<Object> ci_cb_result = new JComboBox<>();
	static JTextPane ci_tp_chance = new JTextPane();
	static JButton ci_bt_go = new JButton("GO");

	static eHandler listener = new eHandler();
	static weHandler wlistener = new weHandler();

	public static void init() {
		main_frame.setLayout(new FlowLayout());
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setSize(400, 130);
		main_frame.setResizable(false);
		main_frame.setVisible(true);
		main_frame.setLocationRelativeTo(null);
		main_bt_smuch.addActionListener(listener);
		main_bt_cc.addActionListener(listener);
		main_farm_bt.addActionListener(listener);
		main_bt_dc.addActionListener(listener);
		main_bt_ci.addActionListener(listener);
		main_bt_cc.setEnabled(false);
		main_bt_dc.setEnabled(false);
		main_bt_ci.setEnabled(false);
		tp_newver.setText("Connecting...");
		tp_newver.setEditable(false);

		Box b11 = Box.createHorizontalBox();
		b11.add(Box.createHorizontalStrut(300));
		b11.add(main_bt_smuch);
		b11.add(main_farm_bt);
		b11.add(Box.createHorizontalStrut(300));
		Box b12 = Box.createHorizontalBox();
		b12.add(Box.createHorizontalStrut(300));
		b12.add(main_bt_cc);
		b12.add(main_bt_dc);
		b12.add(main_bt_ci);
		b12.add(Box.createHorizontalStrut(300));
		Box b13 = Box.createHorizontalBox();
		b13.add(Box.createHorizontalStrut(300));
		b13.add(tp_newver);
		b13.add(Box.createHorizontalStrut(300));
		main_frame.add(b11);
		main_frame.add(b12);
		main_frame.add(b13);

		// ---------------------------------------CC
		cc_frame.setLayout(new FlowLayout());
		cc_frame.addWindowListener(wlistener);
		cc_frame.setSize(450, 130);
		cc_frame.setResizable(false);
		sumch_tp_result.setEditable(false);
		cc_tp_craft.setEditable(false);
		cc_tp_chance.setEditable(false);
		cc_bt_go.addActionListener(listener);
		cc_cb_ship.addActionListener(listener);
		cc_cb_ship.setSelectedItem(Core.getKanmusu("Yuudachi", Core.kmlist));
		Box cb1 = Box.createHorizontalBox();
		cb1.add(Box.createHorizontalStrut(300));
		cb1.add(cc_cb_ship);
		cb1.add(Box.createHorizontalStrut(300));
		Box cb2 = Box.createHorizontalBox();
		cb2.add(Box.createHorizontalStrut(300));
		cb2.add(cc_tf_tries);
		cb2.add(cc_tp_craft);
		cb2.add(Box.createHorizontalStrut(300));
		Box cb3 = Box.createHorizontalBox();
		cb3.add(Box.createHorizontalStrut(300));
		cb3.add(cc_bt_go);
		cb3.add(cc_tp_chance);
		cb3.add(Box.createHorizontalStrut(300));
		cc_frame.add(cb1);
		cc_frame.add(cb2);
		cc_frame.add(cb3);

		// -----------------------------------FARM
		farm_frame.setLayout(new FlowLayout());
		farm_frame.addWindowListener(wlistener);
		farm_frame.setSize(300, 140);
		farm_frame.setResizable(false);
		farm_bt_go.addActionListener(listener);
		farm_tp_result.setEditable(false);
		Box fb1 = Box.createHorizontalBox();
		fb1.add(Box.createHorizontalStrut(150));
		fb1.add(farm_tf_lvls);
		fb1.add(farm_tf_basexp);
		fb1.add(mb_farm_rank);
		fb1.add(Box.createHorizontalStrut(150));
		Box fb2 = Box.createHorizontalBox();
		fb2.add(Box.createHorizontalStrut(150));
		fb2.add(farm_bt_go);
		fb2.add(farm_tp_result);
		fb2.add(Box.createHorizontalStrut(150));
		Box fb3 = Box.createHorizontalBox();
		fb3.add(Box.createHorizontalStrut(150));
		fb3.add(farm_tf_cons_t);
		fb3.add(farm_tf_cons_a);
		fb3.add(Box.createHorizontalStrut(150));
		farm_frame.add(fb1);
		farm_frame.add(fb3);
		farm_frame.add(fb2);

		// ---------------------------------------SMUCH
		sumch_frame.setLayout(new FlowLayout());
		sumch_frame.addWindowListener(wlistener);
		sumch_frame.setSize(300, 130);
		sumch_frame.setResizable(false);
		sumch_tp_prec.setText("%");
		sumch_tp_prec.setEditable(false);
		Box b1 = Box.createHorizontalBox();
		b1.add(Box.createHorizontalStrut(150));
		b1.add(sumch_tf_chance);
		b1.add(sumch_tp_prec);
		b1.add(Box.createHorizontalStrut(150));
		Box b2 = Box.createHorizontalBox();
		b2.add(Box.createHorizontalStrut(150));
		b2.add(sumch_tf_tries);
		b2.add(Box.createHorizontalStrut(150));
		Box b3 = Box.createHorizontalBox();
		b3.add(Box.createHorizontalStrut(150));
		b3.add(sumch_bt_go);
		b3.add(Box.createHorizontalStrut(5));
		b3.add(sumch_tp_result);
		b3.add(Box.createHorizontalStrut(150));
		sumch_bt_go.addActionListener(listener);
		sumch_frame.add(b1);
		sumch_frame.add(b2);
		sumch_frame.add(b3);

		// ---------------------------------------------DC
		dc_frame.setLayout(new FlowLayout());
		dc_frame.addWindowListener(wlistener);
		dc_frame.setSize(350, 150);
		dc_frame.setResizable(false);
		dc_tp_result.setEditable(false);
		// dc_tp_chance1.setEditable(false);
		dc_bt_go.addActionListener(listener);
		cb_dc_maps.addActionListener(listener);
		cb_dc_ship.addActionListener(listener);
		// cb_dc_nodes.addActionListener(listener);
		cb_dc_ship.setSelectedItem(Core.getKanmusu("Yuudachi", Core.kmlist));
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
		// dc3.add(dc_tp_chance1);
		dc3.add(Box.createHorizontalStrut(350));
		Box dc4 = Box.createHorizontalBox();
		dc4.add(Box.createHorizontalStrut(350));
		dc4.add(dc_bt_go);
		dc4.add(dc_tp_result);
		dc4.add(Box.createHorizontalStrut(350));
		dc_frame.add(dc1);
		dc_frame.add(dc2);
		dc_frame.add(dc3);
		dc_frame.add(dc4);
		
		//-------------------------------------------CI
		ci_frame.setLayout(new FlowLayout());
		ci_frame.addWindowListener(wlistener);
		ci_frame.setSize(350, 150);
		ci_frame.setResizable(false);
		ci_tp_chance.setEditable(false);
		ci_cb_result.addActionListener(listener);
		ci_bt_go.addActionListener(listener);
		Box ci1 = Box.createHorizontalBox();
		ci1.add(Box.createHorizontalStrut(350));
		ci1.add(ci_tf_fuel);
		ci1.add(ci_tf_steel);
		ci1.add(Box.createHorizontalStrut(350));		
		Box ci2 = Box.createHorizontalBox();
		ci2.add(Box.createHorizontalStrut(350));
		ci2.add(ci_tf_ammo);
		ci2.add(ci_tf_boux);
		ci2.add(Box.createHorizontalStrut(350));		
		Box ci3 = Box.createHorizontalBox();
		ci3.add(Box.createHorizontalStrut(350));
		ci3.add(ci_bt_go);
		ci3.add(ci_tp_chance);
		ci3.add(Box.createHorizontalStrut(350));		
		Box ci4 = Box.createHorizontalBox();
		ci4.add(Box.createHorizontalStrut(350));
		ci4.add(ci_cb_result);
		ci4.add(Box.createHorizontalStrut(350));
		ci_frame.add(ci1);
		ci_frame.add(ci2);
		ci_frame.add(ci3);
		ci_frame.add(ci4);
		
	}

	static class eHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Kanmusu kanmusu = Core.getKanmusu("Yuudachi", Core.kmlist);

			if (e.getSource() == sumch_bt_go) {
				try {
					sumch_tp_result.setText((int) Core.getSumChance(Integer.parseInt(sumch_tf_chance.getText()),
							Integer.parseInt(sumch_tf_tries.getText())) + "%");
				} catch (Exception er) {
					JOptionPane.showMessageDialog(null, er, "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getSource() == main_bt_smuch || e.getSource() == main_bt_cc || e.getSource() == main_farm_bt
					|| e.getSource() == main_bt_dc || e.getSource() == main_bt_ci) {
				JFrame frame = null;
				if (e.getSource() == main_bt_smuch)
					frame = sumch_frame;
				else if (e.getSource() == main_bt_cc)
					frame = cc_frame;
				else if (e.getSource() == main_farm_bt)
					frame = farm_frame;
				else if (e.getSource() == main_bt_dc)
					frame = dc_frame;
				else if (e.getSource() == main_bt_ci)
					frame = ci_frame;
				frame.setLocation(main_frame.getLocation());
				main_frame.setVisible(false);
				frame.setVisible(true);
			} else if (e.getSource() == farm_bt_go) {
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
				int sts = Core.getSortiresLeft(farm_tf_lvls.getText(), Integer.parseInt(farm_tf_basexp.getText()),
						farm_rankmult);
				farm_tp_result.setText(sts + "sts; " + sts * Integer.parseInt(farm_tf_cons_t.getText()) + "/"
						+ sts * Integer.parseInt(farm_tf_cons_a.getText()) + " F/A");
			} else if (e.getSource() == cc_bt_go) {
				kanmusu = (Kanmusu) cc_cb_ship.getSelectedItem();
				if (kanmusu.craft != "unbuildable") {
					double chance = Core.getSumChance(kanmusu.getCraftchance(),
							Integer.parseInt(cc_tf_tries.getText()));
					chance = (double) Math.rint(1000 * chance) / 1000;
					cc_tp_chance.setText(Core.getPrice(Integer.parseInt(cc_tf_tries.getText()), kanmusu) + " | " + chance + "%");
				}
			} else if (e.getSource() == cc_cb_ship) {
				kanmusu = (Kanmusu) cc_cb_ship.getSelectedItem();
				cc_tp_craft.setText(kanmusu.craft + " | " + kanmusu.getCraftchance() + "%" + " | " + KMParser.getConstTime(kanmusu));
				if (kanmusu.craft == "unbuildable") {
					cc_tp_chance.setText("-----------");
				}
			} else if (e.getSource() == cb_dc_ship) {
				kanmusu = (Kanmusu) cb_dc_ship.getSelectedItem();
				// cb_dc_nodes.removeActionListener(listener);
				cb_dc_maps.removeAllItems();
				try {
					buildList(cb_dc_maps, kanmusu.getMaps());
				} catch (IOException e1) {
					connectErr = true;
				}
				if (!connectErr) {
					main_bt_dc.setEnabled(true);
					main_bt_cc.setEnabled(true);
					main_bt_ci.setEnabled(true);
				}
				tp_newver.setText("Ready.");
			} else if (e.getSource() == cb_dc_maps) {
				kanmusu = (Kanmusu) cb_dc_ship.getSelectedItem();
				String map = (String) cb_dc_maps.getSelectedItem();
				cb_dc_nodes.removeAllItems();
				buildList(cb_dc_nodes, kanmusu.getNodes(map));
				// cb_dc_nodes.addActionListener(listener);
			} else if (e.getSource() == dc_bt_go) {
				kanmusu = (Kanmusu) cb_dc_ship.getSelectedItem();
				double res = Core.getSumChance(kanmusu.getDropChance(cb_dc_maps.getSelectedItem().toString(),
						cb_dc_nodes.getSelectedItem().toString()), Integer.valueOf(tf_dc_tries.getText()));
				res = (Double) Math.rint(res * 1000) / 1000;
				dc_tp_result.setText(res + "%");
			} else if (e.getSource() == cb_dc_nodes) {
				// Кто-то навел порчу, из-за чего это говно не хочет работать.
				// Час пытался избавиться от нее, но бестолку
				// Object map = cb_dc_maps.getSelectedItem();
				// Object node = cb_dc_nodes.getSelectedItem();
				// if (node != null)
				// dc_tp_chance1.setText(kanmusu.getDropChance(map.toString(),
				// node.toString())+"%");
			} else if (e.getSource() == ci_bt_go) {
				String craft = ci_tf_fuel.getText()+"/"+ci_tf_ammo.getText()+"/"+ci_tf_steel.getText()+"/"+ci_tf_boux.getText();
				ci_cb_result.removeAllItems();
				buildListKM(ci_cb_result, KCDB.getCraftDrops(craft));
			} else if (e.getSource() == ci_cb_result) {
				Kanmusu ciSelectedKM = null;
				try {
					ciSelectedKM = (Kanmusu) ci_cb_result.getSelectedItem();
				} catch (Exception e1) {}
				if (ciSelectedKM != null) {
					String craft = ci_tf_fuel.getText()+"/"+ci_tf_ammo.getText()+"/"+ci_tf_steel.getText()+"/"+ci_tf_boux.getText();
					double res = ciSelectedKM.getCraft(craft).chance;
					res = (Double)Math.rint(res*1000)/1000;
					ci_tp_chance.setText(res+"% | "+ciSelectedKM.getCraft(craft).entries+" entries");
				}
			}
		}

		private void buildList(JComboBox<Object> list, ArrayList<String> contentList) {
			if (contentList.size() == 0) {
				list.addItem("---");
				return;
			}
			Object[] content = contentList.toArray();
			Arrays.sort(content);
			for (int i = 0; i < content.length; i++)
				list.addItem(content[i]);
		}
		
		private void buildListKM(JComboBox<Object> list, ArrayList<Kanmusu> contentList) {
			if (contentList == null ||contentList.size() == 0) {
				list.addItem("---");
				return;
			}
			Core.kmsort(contentList);
			for (int i = 0; i < contentList.size(); i++)
				list.addItem(contentList.get(i));
		}

	}

	static class weHandler implements WindowListener {

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			JFrame fr = (JFrame) e.getSource();
			main_frame.setLocation(fr.getLocation());
			main_frame.setVisible(true);
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowOpened(WindowEvent e) {
		}
	}

	public static void err(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public static void CCCi() {
		ccc_frame.setLayout(new FlowLayout());
		ccc_frame.setSize(300, 130);
		ccc_frame.setVisible(true);
		ccc_frame.setResizable(false);
		ccc_frame.setLocationRelativeTo(null);
		ccc_tp_.setEditable(false);
		ccc_pb.setMinimum(0);
		ccc_pb.setMaximum(2);
		ccc_pb.setValue(0);
		Box b1 = Box.createHorizontalBox();
		b1.add(Box.createHorizontalStrut(200));
		b1.add(ccc_pb);
		b1.add(Box.createHorizontalStrut(200));
		Box b2 = Box.createHorizontalBox();
		b2.add(Box.createHorizontalStrut(200));
		b2.add(ccc_tp_);
		b2.add(Box.createHorizontalStrut(200));
		ccc_frame.add(b1);
		ccc_frame.add(b2);
	}

	public static void CCCc(int total, int index, String status) {
		ccc_pb.setMaximum(total - 1);
		ccc_pb.setValue(index);
		ccc_tp_.setText(status);
	}

	public static void CCCe() {
		ccc_frame.setVisible(false);
	}
}
