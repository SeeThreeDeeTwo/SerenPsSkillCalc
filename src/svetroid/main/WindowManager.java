package svetroid.main;

import java.awt.Choice;
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
	JMenu mnHelp;
	JMenuItem mntmAbout;

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

	public WindowManager() {
		initialize();
	}

	private void initialize() {

		Vars.loadIcons();
		Vars.loadFonts();
		Vars.setFormats();

		// Main window

		main = new Window("Main", "SerenPS - Skill Calculator", 555, 400, null, false, true);
		main.start();

		menuBar = new JMenuBar();
		main.getFrame().setJMenuBar(menuBar);

		mnFile = new JMenu("Options");
		menuBar.add(mnFile);

		mntmReset = new JMenuItem("Double XP Mode");
		mnFile.add(mntmReset);
		
				mnHelp = new JMenu("Help");
				menuBar.add(mnHelp);
				
						mntmAbout = new JMenuItem("About");
						mnHelp.add(mntmAbout);

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
				doubleXpMode();
			}
		});

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

	public void doubleXpMode() {
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
		lblYourExperienceString.setText("DOUBLE XP");
		lblYourExperience.setText("ACTIVE");
		Experience.XP_rate = 2.00;
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
	public void restGUI() {
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
		Experience.XP_rate = 15.00;
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