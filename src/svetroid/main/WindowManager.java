package svetroid.main;

import java.awt.Choice;
//import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class WindowManager {

	Window main;
	Window itemXP;

	SkillManager s = new SkillManager();
	User user;

	// Main components

	JMenuBar menuBar;
	JMenu mnFile;
	JMenuItem mntmReset;
	JMenuItem mntmReset2;

	Choice choiceSkill;

	List list;

	JLabel lblTotalLevel_Title;
	JLabel lblTotalXP_Title;
	JLabel lblTotalLevel;
	JLabel lblTotalXP;

	JLabel lblListXP;
	JLabel lblItemsToTarget;
	JLabel lblCalculate;

	JLabel lblYourLevelString;
	JLabel lblYourLevel;
	JLabel lblYourExperienceString;
	JLabel lblYourExperience;
	JCheckBox chckbxPrestige;

	JButton btnCalculate;
	JButton btnItemXP;

	Choice choiceUserOrXP;
	Choice choiceTargetLevelOrXP;

	JFormattedTextField textFieldTargetLevel;
	JFormattedTextField textFieldTargetXP;
	JFormattedTextField textFieldUsername;
	JFormattedTextField textFieldYourSkillXP;

	MouseListener mlTargetLevel;
	MouseListener mlTargetXP;
	MouseListener mlTextField;

	// Item XP components

	JFormattedTextField textFieldItemAmount;
	JLabel lblItemXP;
	JButton btnCalcXP;
	private JMenu mnSkillGuides;
	private JMenuItem mntmAgility;
	private JMenuItem mntmCooking;
	private JMenuItem mntmCrafting;
	private JMenuItem mntmFiremaking;
	private JMenuItem mntmFishing;
	private JMenuItem mntmFletching;
	private JMenuItem mntmFarming;
	private JMenuItem mntmHerblore;
	private JMenuItem mntmMining;
	private JMenuItem mntmRunecrafting;
	private JMenuItem mntmSlayer;
	private JMenuItem mntmSmithing;
	private JMenuItem mntmSummoning;
	private JMenuItem mntmThieving;
	private JMenu mnClueScrolls;
	private JMenu mnWorldMap;
	private JMenuItem mntmMaps;
	private JMenuItem mntmCoordinates;
	private JMenuItem mntmEmoteClues;
	private JMenuItem mntmHunting;
	private JMenuItem mnOSRSmap;

	public WindowManager() {
		initialize();
	}

	private void initialize() {

		Vars.loadIcons();
		Vars.loadFonts();
		Vars.setFormats();

		// Main window

		main = new Window("Main", "SerenPS - Assistance Utility", 555, 400, null, false, true);
		main.start();

		menuBar = new JMenuBar();
		main.getFrame().setJMenuBar(menuBar);

		mnFile = new JMenu("Options");
		menuBar.add(mnFile);

		mntmReset = new JMenuItem("Double XP Mode");
		mnFile.add(mntmReset);
		mntmReset2 = new JMenuItem("Reset");
		mnFile.add(mntmReset2);
						
						mnSkillGuides = new JMenu("Skill Guides");
						menuBar.add(mnSkillGuides);
						
						mntmAgility = new JMenuItem("Agility");
						mnSkillGuides.add(mntmAgility);
						
						mntmCooking = new JMenuItem("Cooking");
						mnSkillGuides.add(mntmCooking);
						
						mntmCrafting = new JMenuItem("Crafting");
						mnSkillGuides.add(mntmCrafting);
						
						mntmFarming = new JMenuItem("Farming");
						mnSkillGuides.add(mntmFarming);
						
						mntmFiremaking = new JMenuItem("Firemaking");
						mnSkillGuides.add(mntmFiremaking);
						
						mntmFishing = new JMenuItem("Fishing");
						mnSkillGuides.add(mntmFishing);
						
						mntmFletching = new JMenuItem("Fletching");
						mnSkillGuides.add(mntmFletching);
						
						mntmHerblore = new JMenuItem("Herblore");
						mnSkillGuides.add(mntmHerblore);
						
						mntmHunting = new JMenuItem("Hunting");
						mnSkillGuides.add(mntmHunting);
						
						mntmMining = new JMenuItem("Mining");
						mnSkillGuides.add(mntmMining);
						
						mntmRunecrafting = new JMenuItem("Runecrafting");
						mnSkillGuides.add(mntmRunecrafting);
						
						mntmSlayer = new JMenuItem("Slayer");
						mnSkillGuides.add(mntmSlayer);
						
						mntmSmithing = new JMenuItem("Smithing");
						mnSkillGuides.add(mntmSmithing);
						
						mntmSummoning = new JMenuItem("Summoning");
						mnSkillGuides.add(mntmSummoning);
						
						mntmThieving = new JMenuItem("Thieving");
						mnSkillGuides.add(mntmThieving);
						
						mnClueScrolls = new JMenu("Clue Scrolls");
						menuBar.add(mnClueScrolls);
						
						mntmCoordinates = new JMenuItem("Coordinate Clues");
						mnClueScrolls.add(mntmCoordinates);
						
						mntmEmoteClues = new JMenuItem("Emote Clues");
						mnClueScrolls.add(mntmEmoteClues);
						
						mntmMaps = new JMenuItem("Map Clues");
						mnClueScrolls.add(mntmMaps);
						
						mnWorldMap = new JMenu("World Map");
						menuBar.add(mnWorldMap);
						
						mnOSRSmap = new JMenuItem("OSRS Map");
						mnWorldMap.add(mnOSRSmap);

		lblTotalLevel_Title = new JLabel("Total Level:");
		main.add(lblTotalLevel_Title, 10, 300, 65, 20, false);

		lblTotalXP_Title = new JLabel("Total XP:");
		main.add(lblTotalXP_Title, 10, 320, 65, 20, false);

		lblTotalLevel = new JLabel("N/A");
		main.add(lblTotalLevel, 87, 300, 92, 20, false);

		lblTotalXP = new JLabel("N/A");
		main.add(lblTotalXP, 87, 320, 92, 20, false);

		lblListXP = new JLabel("");
		main.add(lblListXP, 320, 66, 65, 16);
		
		JCheckBox chckbxPrestige = new JCheckBox("Prestiged");
		chckbxPrestige.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		    	double rate = Experience.XP_rate;
		        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
		            Experience.XP_rate = rate * 0.40;
		        } else {//checkbox has been deselected
		        	Experience.XP_rate = rate * 1.00;
		        	};
		    }
		});
		chckbxPrestige.setEnabled(true);
		main.add(chckbxPrestige, 121, 14, 97, 23);
		chckbxPrestige.setVisible(true);

		lblItemsToTarget = new JLabel("");
		lblItemsToTarget.setHorizontalAlignment(SwingConstants.CENTER);
		main.add(lblItemsToTarget, 87, 249, 250, 36);

		lblCalculate = new JLabel("");
		lblCalculate.setHorizontalAlignment(SwingConstants.CENTER);
		main.add(lblCalculate, 121, 215, 183, 41);

		lblYourLevelString = new JLabel("Level:");
		main.add(lblYourLevelString, 10, 14, 65, 20);

		lblYourLevel = new JLabel("N/A");
		main.add(lblYourLevel, 74, 14, 50, 20);

		lblYourExperienceString = new JLabel("Experience:");
		main.add(lblYourExperienceString, 10, 41, 65, 20);

		lblYourExperience = new JLabel("N/A");
		main.add(lblYourExperience, 74, 41, 75, 20);

		
		list = new List();
		main.add(list, 391, 40, 149, 300);

		choiceSkill = new Choice();
		main.add(choiceSkill, 391, 10, 149, 24);
		choiceSkill.add("Select one");
		s.addSkillItemsToMenu(choiceSkill);

		textFieldTargetLevel = new JFormattedTextField(Vars.formatterLevel);
		main.add(textFieldTargetLevel, 129, 105, 50, 22, true);

		textFieldTargetXP = new JFormattedTextField(Vars.formatterXP);
		main.add(textFieldTargetXP, 129, 105, 90, 22, false);

		textFieldYourSkillXP = new JFormattedTextField(Vars.formatterXP);
		main.add(textFieldYourSkillXP, 129, 67, 90, 22);

		textFieldUsername = new JFormattedTextField();
		textFieldUsername.setText("");
		//main.add(textFieldUsername, 129, 67, 90, 22);

		choiceTargetLevelOrXP = new Choice();
		main.add(choiceTargetLevelOrXP, 10, 105, 114, 24);
		choiceTargetLevelOrXP.setFocusable(false);
		choiceTargetLevelOrXP.add("Target Level");
		choiceTargetLevelOrXP.add("Target XP");

		choiceUserOrXP = new Choice();
		choiceUserOrXP.setFocusable(false);
		choiceUserOrXP.add("Experience");
		choiceUserOrXP.add("Username");

		btnCalculate = new JButton("Calculate");
		main.add(btnCalculate, 169, 181, 89, 23);

		btnItemXP = new JButton("Item XP");
		main.add(btnItemXP, 10, 181, 89, 23);

		// Item XP window

		itemXP = new Window("ItemXP", "Item XP", 250, 400, null, false, false);
		itemXP.setPos(main.getPosX() + main.getWidth() - 4, main.getPosY());
		itemXP.start();

		textFieldItemAmount = new JFormattedTextField(Vars.formatterXP);
		itemXP.add(textFieldItemAmount, 75, 100, 90, 22);

		lblItemXP = new JLabel("N/A");
		itemXP.add(lblItemXP, 50, 150, 150, 20);
		lblItemXP.setHorizontalAlignment(SwingConstants.CENTER);

		btnCalcXP = new JButton("Calculate");
		itemXP.add(btnCalcXP, 75, 200, 89, 23);

		doAction();
		main.update();
		itemXP.update();
	}

	public void doAction() {

		// Main Action Listeners

		MouseAdapter mlBtnItemXP = new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						itemXP.setVisible(true);
					}
				});
			}
		};
		
		btnItemXP.addMouseListener(mlBtnItemXP);

		mntmReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double prst = Experience.XP_rate;
				lblYourExperienceString.setText("DOUBLE XP");
				lblYourExperience.setText("ACTIVE");
				Experience.XP_rate = prst*2.00;
				mntmReset.setEnabled(false);
			}
		});
		mntmReset2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetGUI();
			}
		});
		
		// Guides Menu ------------
		
		mntmCooking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/1144-1-99-cooking-guide"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		mntmCrafting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/1202-1-99-crafting-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		mntmFiremaking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/1129-1-99-firemaking-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		/* mntmFishing.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        try{ 
		            String url = ""; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		}); */
		mntmFletching.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/1205-1-99-fletching-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
	/*	mntmFarming.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = ""; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});		*/
		mntmHerblore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/986-1-99-herblore-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		mntmHunting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/1198-1-99-hunter-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		mntmMining.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/934-1-99-mining-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		mntmRunecrafting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/937-1-99-runecrafting-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		mntmSlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/1206-comprehensive-slayer-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		/* mntmSmithing.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/1205-1-99-fletching-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		}); */
		mntmSummoning.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/632-1-99-summoning-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		mntmThieving.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://community.serenps.com/index.php?/topic/977-1-99-thieving-guide/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		});
		 mnOSRSmap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://OSRSmap.com/"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		}); 
		 mntmMaps.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://runescape.wikia.com/wiki/Treasure_Trails/Guide/Maps"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		}); 
		 mntmCoordinates.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://runescape.wikia.com/wiki/Treasure_Trails/Guide/Coordinates"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		}); 
		 mntmEmoteClues.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
		        // TODO add your handling code here:
		        try{ 
		            String url = "http://runescape.wikia.com/wiki/Treasure_Trails/Guide/Emotes"; 
		            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url)); 
		          } 
		          catch (java.io.IOException e) { 
		              System.out.println(e.getMessage()); 
		          } 
		    }
		}); 
		
		// Guides Menu End -----------------
		
		list.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				for (int i = 0; i < Vars.skillList.size(); i++) {
					s.updateList(Vars.skillList.get(i), choiceSkill, list, lblListXP);
					list.requestFocus();
				}
				list.setFocusable(false);
				list.setFocusable(true);
			}
		});

		choiceSkill.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (choiceSkill.getItem(choiceSkill.getSelectedIndex()).equalsIgnoreCase("Select one")) {
					list.removeAll();
					lblYourLevel.setText("N/A");
					lblYourExperience.setText("N/A");
				}
				if (choiceUserOrXP.getItem(choiceUserOrXP.getSelectedIndex()).equals("Username")) {
					updateUserStats(user, choiceSkill, lblYourLevel, lblYourExperience);
				} else if (choiceUserOrXP.getItem(choiceUserOrXP.getSelectedIndex()).equals("Experience")) {
					lblYourLevel.setText(Integer.toString(Experience.yourCurrentLevel));
					lblYourExperience.setText(NumberFormat.getIntegerInstance().format(Experience.yourCurrentXP));
				}
				lblCalculate.setText("");
				lblItemsToTarget.setText("");
				for (int i = 0; i < Vars.skillList.size(); i++) {
					s.updateChoices(Vars.skillList.get(i), choiceSkill, list);
					lblListXP.setText("");
				}
				choiceSkill.setFocusable(false);
				choiceSkill.setFocusable(true);
			}
		});

		textFieldTargetLevel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					Vars.formatterLevel.setAllowsInvalid(true);
					Vars.formatterXP.setAllowsInvalid(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					Vars.formatterLevel.setAllowsInvalid(false);
					Vars.formatterXP.setAllowsInvalid(false);
				}
			}
		});
		textFieldTargetLevel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textFieldTargetLevel.getText().isEmpty()) {
					Experience.yourTargetLevel = Integer.parseInt(textFieldTargetLevel.getText().replace(",", ""));
				}
			}
		});
		mlTargetLevel = new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JTextField tf = (JTextField) e.getSource();
						int offset = tf.viewToModel(e.getPoint());
						tf.setCaretPosition(offset);
					}
				});
			}
		};
		textFieldTargetLevel.addMouseListener(mlTargetLevel);

		textFieldTargetXP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					Vars.formatterLevel.setAllowsInvalid(true);
					Vars.formatterXP.setAllowsInvalid(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					Vars.formatterLevel.setAllowsInvalid(false);
					Vars.formatterXP.setAllowsInvalid(false);
				}
			}
		});
		textFieldTargetXP.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textFieldTargetXP.getText().isEmpty()) {
					Experience.yourTargetXP = Integer.parseInt(textFieldTargetXP.getText().replace(",", ""));
				}
			}
		});
		mlTargetXP = new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JTextField tf = (JTextField) e.getSource();
						int offset = tf.viewToModel(e.getPoint());
						tf.setCaretPosition(offset);
					}
				});
			}
		};
		textFieldTargetXP.addMouseListener(mlTargetXP);

		textFieldYourSkillXP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					Vars.formatterLevel.setAllowsInvalid(true);
					Vars.formatterXP.setAllowsInvalid(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					Vars.formatterLevel.setAllowsInvalid(false);
					Vars.formatterXP.setAllowsInvalid(false);
				}
			}
		});
		textFieldYourSkillXP.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textFieldYourSkillXP.getText().isEmpty() && choiceUserOrXP.getItem(choiceUserOrXP.getSelectedIndex()).equals("Experience")) {
					Experience.yourCurrentXP = Long.parseLong(textFieldYourSkillXP.getText().replace(",", ""));
					Experience.setLevelFromXP();
					lblYourLevel.setText(Integer.toString(Experience.yourCurrentLevel));
					lblYourExperience.setText(NumberFormat.getIntegerInstance().format(Experience.yourCurrentXP));
				}
			}
		});
		mlTextField = new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JTextField tf = (JTextField) e.getSource();
						int offset = tf.viewToModel(e.getPoint());
						tf.setCaretPosition(offset);
					}
				});
			}
		};
		textFieldYourSkillXP.addMouseListener(mlTextField);

		textFieldUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					user.getUserStats(user.getUsername());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				updateUserStats(user, choiceSkill, lblYourLevel, lblYourExperience);
				lblTotalLevel.setText(Integer.toString(Experience.yourTotalLevel));
				lblTotalXP.setText(NumberFormat.getIntegerInstance().format(Experience.yourTotalXP));
				lblTotalLevel_Title.setVisible(true);
				lblTotalXP_Title.setVisible(true);
				lblTotalLevel.setVisible(true);
				lblTotalXP.setVisible(true);
			}
		});
		textFieldUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				user = new User(textFieldUsername.getText());
			}
		});
		textFieldUsername.addMouseListener(mlTextField);

		choiceTargetLevelOrXP.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (choiceTargetLevelOrXP.getItem(choiceTargetLevelOrXP.getSelectedIndex()).equals("Target Level")) {
					textFieldTargetXP.setVisible(false);
					Vars.formatterXP.setAllowsInvalid(true);
					textFieldTargetXP.setText("");
					Experience.yourTargetXP = 0;
					Vars.formatterXP.setAllowsInvalid(false);
					textFieldTargetLevel.setVisible(true);
				} else if (choiceTargetLevelOrXP.getItem(choiceTargetLevelOrXP.getSelectedIndex()).equals("Target XP")) {
					textFieldTargetLevel.setVisible(false);
					Vars.formatterLevel.setAllowsInvalid(true);
					textFieldTargetLevel.setText("");
					Experience.yourTargetLevel = 0;
					Vars.formatterLevel.setAllowsInvalid(false);
					textFieldTargetXP.setVisible(true);
				}
			}
		});

		choiceUserOrXP.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (choiceUserOrXP.getItem(choiceUserOrXP.getSelectedIndex()).equals("Experience")) {
					textFieldUsername.setVisible(false);
					textFieldUsername.setText("");
					lblYourLevel.setText("N/A");
					lblYourExperience.setText("N/A");
					lblItemsToTarget.setText("");
					lblCalculate.setText("");
					lblTotalLevel_Title.setVisible(false);
					lblTotalXP_Title.setVisible(false);
					lblTotalLevel.setText("");
					lblTotalXP.setText("");
					textFieldYourSkillXP.setVisible(true);
					textFieldYourSkillXP.requestFocus();
				} else if (choiceUserOrXP.getItem(choiceUserOrXP.getSelectedIndex()).equals("Username")) {
					Vars.formatterXP.setAllowsInvalid(true);
					textFieldYourSkillXP.setVisible(false);
					textFieldYourSkillXP.setText("");
					Vars.formatterXP.setAllowsInvalid(false);
					lblYourLevel.setText("N/A");
					lblYourExperience.setText("N/A");
					lblItemsToTarget.setText("");
					lblCalculate.setText("");
					textFieldUsername.setVisible(true);
					textFieldUsername.requestFocus();
				}
			}
		});
		main.add(choiceUserOrXP, 10, 66, 114, 24);

		btnCalculate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (choiceUserOrXP.getItem(choiceUserOrXP.getSelectedIndex()).equals("Username")) {
					updateUserStats(user, choiceSkill, lblYourLevel, lblYourExperience);
				} else if (choiceUserOrXP.getItem(choiceUserOrXP.getSelectedIndex()).equals("Experience")) {
					Experience.setLevelFromXP();
				}
				if (choiceTargetLevelOrXP.getItem(choiceTargetLevelOrXP.getSelectedIndex()).equals("Target Level")) {
					Experience.setTargetXPFromTargetLevel();
				} else if (choiceTargetLevelOrXP.getItem(choiceTargetLevelOrXP.getSelectedIndex()).equals("Target XP")) {
					Experience.yourTargetXP = Integer.parseInt(textFieldTargetXP.getText());
				}
				for (int i = 0; i < Vars.skillList.size(); i++) {
					s.calculateLogic(Vars.skillList.get(i), choiceSkill, list, textFieldYourSkillXP, textFieldUsername, textFieldTargetLevel, textFieldTargetXP, lblCalculate, lblItemsToTarget);
				}
			}
		});

		// Item XP ActionListeners

		textFieldItemAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					Vars.formatterLevel.setAllowsInvalid(true);
					Vars.formatterXP.setAllowsInvalid(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					Vars.formatterLevel.setAllowsInvalid(false);
					Vars.formatterXP.setAllowsInvalid(false);
				}
			}
		});
		textFieldItemAmount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textFieldItemAmount.getText().isEmpty()) {
					for (int i = 0; i < list.getItemCount(); i++) {
						if (list.isIndexSelected(i)) {
							double xp = Vars.getItem(list.getItem(i).toString()).getExp() * Integer.parseInt(textFieldItemAmount.getText().replaceAll(",", ""));
							lblItemXP.setText(NumberFormat.getNumberInstance().format(xp) + " XP");
						}
					}

				}

			}
		});
		mlTargetXP = new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JTextField tf = (JTextField) e.getSource();
						int offset = tf.viewToModel(e.getPoint());
						tf.setCaretPosition(offset);
					}
				});
			}
		};
		textFieldItemAmount.addMouseListener(mlTargetXP);

		main.update();
		itemXP.update();
	}

	public void resetGUI() {
		Vars.formatterLevel.setAllowsInvalid(true);
		Vars.formatterXP.setAllowsInvalid(true);
		textFieldYourSkillXP.setText("");
		textFieldTargetXP.setText("");
		textFieldTargetLevel.setText("");
		textFieldUsername.setText("");
		lblListXP.setText("");
		lblCalculate.setText("");
		lblItemsToTarget.setText("");
		lblYourLevel.setText("N/A");
		lblYourExperience.setText("N/A");
		lblYourExperienceString.setText("Experience");
		Experience.XP_rate = 1.00;
		mntmReset.setEnabled(true);
		list.removeAll();
		choiceSkill.select(0);
		if (choiceUserOrXP.getItem(choiceUserOrXP.getSelectedIndex()).equals("Username")) {
			textFieldUsername.requestFocus();
		} else if (choiceUserOrXP.getItem(choiceUserOrXP.getSelectedIndex()).equals("Experience")) {
			textFieldYourSkillXP.requestFocus();
		}
		lblTotalLevel_Title.setVisible(false);
		lblTotalXP_Title.setVisible(false);
		lblTotalLevel.setVisible(false);
		lblTotalXP.setVisible(false);
		Vars.formatterLevel.setAllowsInvalid(false);
		Vars.formatterXP.setAllowsInvalid(false);
	}

	public void updateUserStats(User user, Choice choiceSkill, JLabel lblYourLevel, JLabel lblYourExperience) {
		try {
			user.setSelectedSkill(choiceSkill.getItem(choiceSkill.getSelectedIndex()));
			user.getUserStats(user.getUsername());
			if (choiceSkill.getSelectedItem().equalsIgnoreCase("Select one")) {
				lblYourLevel.setText("N/A");
				lblYourExperience.setText("N/A");
			} else {
				lblYourLevel.setText(Integer.toString(user.getSkillLevel(choiceSkill.getItem(choiceSkill.getSelectedIndex()))));
				lblYourExperience.setText(NumberFormat.getIntegerInstance().format(Experience.yourCurrentXP));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}