package svetroid.main;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class WindowManager {

	Window main;
	Window itemXP;

	SkillManager s = new SkillManager();
	User user;

	// Main components

	JMenuBar menuBar;
	JMenuItem mntmDoubleXp;
	JMenuItem mntmResetAll;

	JComboBox<String> choiceSkill;

	JList<String> list;
	DefaultListModel<String> listModel;

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

	JComboBox<String> choiceUserOrXP;
	JComboBox<String> choiceTargetLevelOrXP;

	JFormattedTextField textFieldTargetLevel;
	JFormattedTextField textFieldTargetXP;
	JFormattedTextField textFieldUsername;
	JFormattedTextField textFieldYourSkillXP;

	MouseListener caretFollowsClick;

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
		Theme.install(Vars.fontList.isEmpty() ? null : Vars.fontList.get(0));

		caretFollowsClick = new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JTextField tf = (JTextField) e.getSource();
						tf.setCaretPosition(tf.viewToModel2D(e.getPoint()));
					}
				});
			}
		};

		buildMainWindow();
		buildItemXpWindow();

		wireListeners();

		main.update();
		itemXP.update();
	}

	// ------------------------------------------------------------------
	// Main window
	// ------------------------------------------------------------------

	private void buildMainWindow() {
		main = new Window("Main", "SerenPS - Assistance Utility", 660, 520, new BorderLayout(), true, true);
		main.getContentPane().setBackground(Theme.BG_DEEP);

		menuBar = buildMenuBar();
		main.getFrame().setJMenuBar(menuBar);

		main.add(buildHeader(), BorderLayout.NORTH);
		main.add(buildBody(), BorderLayout.CENTER);
	}

	private JMenuBar buildMenuBar() {
		JMenuBar bar = new JMenuBar();
		bar.setBackground(Theme.BG_PANEL);
		bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER));

		JMenu mnOptions = menu("Options");
		mntmDoubleXp = menuItem("Double XP Mode");
		mnOptions.add(mntmDoubleXp);
		mntmResetAll = menuItem("Reset");
		mnOptions.add(mntmResetAll);
		bar.add(mnOptions);

		JMenu mnSkillGuides = menu("Skill Guides");
		for (String skillName : GuideLinks.SKILL_GUIDE_MENU_ORDER) {
			mnSkillGuides.add(guideMenuItem(skillName, GuideLinks.SKILL_GUIDES.get(skillName)));
		}
		bar.add(mnSkillGuides);

		JMenu mnClueScrolls = menu("Clue Scrolls");
		for (String label : GuideLinks.CLUE_SCROLLS.keySet()) {
			mnClueScrolls.add(guideMenuItem(label, GuideLinks.CLUE_SCROLLS.get(label)));
		}
		bar.add(mnClueScrolls);

		JMenu mnWorldMap = menu("World Map");
		for (String label : GuideLinks.WORLD_MAP.keySet()) {
			mnWorldMap.add(guideMenuItem(label, GuideLinks.WORLD_MAP.get(label)));
		}
		bar.add(mnWorldMap);

		return bar;
	}

	private JMenu menu(String label) {
		JMenu m = new JMenu(label);
		Theme.styleMenu(m);
		return m;
	}

	private JMenuItem menuItem(String label) {
		JMenuItem item = new JMenuItem(label);
		Theme.styleMenuItem(item);
		return item;
	}

	private JMenuItem guideMenuItem(String label, String url) {
		JMenuItem item = menuItem(label);
		if (url != null) {
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent a) {
					GuideLinks.open(url);
				}
			});
		}
		return item;
	}

	private JPanel buildHeader() {
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(Theme.BG_PANEL);
		header.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER), new EmptyBorder(14, 20, 14, 20)));

		JLabel title = Theme.heading("SerenPS Skill Calculator", 20);
		JLabel subtitle = Theme.body("Plan your route to the next level");

		JPanel textStack = new JPanel();
		textStack.setOpaque(false);
		textStack.setLayout(new javax.swing.BoxLayout(textStack, javax.swing.BoxLayout.Y_AXIS));
		title.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		subtitle.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		textStack.add(title);
		textStack.add(subtitle);

		header.add(textStack, BorderLayout.WEST);
		return header;
	}

	private JPanel buildBody() {
		JPanel body = new JPanel(new GridBagLayout());
		body.setOpaque(false);
		body.setBorder(new EmptyBorder(16, 16, 16, 16));

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 14, 16);
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.55;
		c.weighty = 0;
		body.add(buildStatsCard(), c);

		c.gridy = 1;
		body.add(buildTargetCard(), c);

		c.gridy = 2;
		body.add(buildActionsAndResults(), c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 0.45;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0, 0);
		body.add(buildSkillCard(), c);

		return body;
	}

	private JPanel buildStatsCard() {
		JPanel card = Theme.card();
		card.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(4, 0, 4, 8);
		c.gridy = 0;

		c.gridx = 0;
		c.gridwidth = 2;
		card.add(Theme.sectionTitle("Your Stats"), c);

		c.gridy = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.weightx = 0;
		choiceUserOrXP = new JComboBox<String>(new String[] { "Experience", "Username" });
		Theme.styleComboBox(choiceUserOrXP);
		choiceUserOrXP.setFocusable(false);
		card.add(choiceUserOrXP, c);

		c.gridx = 1;
		c.weightx = 1;
		textFieldYourSkillXP = new JFormattedTextField(Vars.formatterXP);
		Theme.styleTextField(textFieldYourSkillXP);
		textFieldUsername = new JFormattedTextField();
		textFieldUsername.setText("");
		Theme.styleTextField(textFieldUsername);
		textFieldUsername.setVisible(false);

		JPanel inputSlot = new JPanel(new java.awt.CardLayout());
		inputSlot.setOpaque(false);
		inputSlot.add(textFieldYourSkillXP, "xp");
		inputSlot.add(textFieldUsername, "user");
		card.add(inputSlot, c);
		this.inputSlot = inputSlot;

		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 2;
		c.insets = new Insets(12, 0, 4, 8);
		JPanel statRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
		statRow.setOpaque(false);
		lblYourLevelString = Theme.body("Level:");
		lblYourLevel = Theme.value("N/A");
		lblYourExperienceString = Theme.body("Experience:");
		lblYourExperience = Theme.value("N/A");
		statRow.add(pair(lblYourLevelString, lblYourLevel));
		statRow.add(pair(lblYourExperienceString, lblYourExperience));
		chckbxPrestige = new JCheckBox("Prestiged");
		Theme.styleCheckBox(chckbxPrestige);
		statRow.add(chckbxPrestige);
		card.add(statRow, c);

		c.gridy = 3;
		c.insets = new Insets(10, 0, 0, 8);
		JPanel totalRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
		totalRow.setOpaque(false);
		lblTotalLevel_Title = Theme.body("Total Level:");
		lblTotalLevel = Theme.value("N/A");
		lblTotalXP_Title = Theme.body("Total XP:");
		lblTotalXP = Theme.value("N/A");
		totalRow.add(pair(lblTotalLevel_Title, lblTotalLevel));
		totalRow.add(pair(lblTotalXP_Title, lblTotalXP));
		card.add(totalRow, c);
		this.totalRow = totalRow;
		totalRow.setVisible(false);
		lblTotalLevel_Title.setVisible(false);
		lblTotalXP_Title.setVisible(false);
		lblTotalLevel.setVisible(false);
		lblTotalXP.setVisible(false);

		return card;
	}

	private JPanel inputSlot;
	private JPanel totalRow;
	private JPanel targetSlot;

	private JPanel pair(JLabel labelPart, JLabel valuePart) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
		p.setOpaque(false);
		p.add(labelPart);
		p.add(valuePart);
		return p;
	}

	private JPanel buildTargetCard() {
		JPanel card = Theme.card();
		card.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(4, 0, 4, 8);
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 2;
		card.add(Theme.sectionTitle("Target"), c);

		c.gridy = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.weightx = 0;
		choiceTargetLevelOrXP = new JComboBox<String>(new String[] { "Target Level", "Target XP" });
		Theme.styleComboBox(choiceTargetLevelOrXP);
		choiceTargetLevelOrXP.setFocusable(false);
		card.add(choiceTargetLevelOrXP, c);

		c.gridx = 1;
		c.weightx = 1;
		textFieldTargetLevel = new JFormattedTextField(Vars.formatterLevel);
		Theme.styleTextField(textFieldTargetLevel);
		textFieldTargetXP = new JFormattedTextField(Vars.formatterXP);
		Theme.styleTextField(textFieldTargetXP);
		textFieldTargetXP.setVisible(false);

		JPanel targetSlot = new JPanel(new java.awt.CardLayout());
		targetSlot.setOpaque(false);
		targetSlot.add(textFieldTargetLevel, "level");
		targetSlot.add(textFieldTargetXP, "xp");
		card.add(targetSlot, c);
		this.targetSlot = targetSlot;

		return card;
	}

	private JPanel buildActionsAndResults() {
		JPanel wrap = new JPanel(new BorderLayout(0, 12));
		wrap.setOpaque(false);

		JPanel buttons = new JPanel(new GridLayout(1, 2, 12, 0));
		buttons.setOpaque(false);
		btnItemXP = Theme.secondaryButton("Item XP");
		btnCalculate = Theme.primaryButton("Calculate");
		buttons.add(btnItemXP);
		buttons.add(btnCalculate);
		wrap.add(buttons, BorderLayout.NORTH);

		JPanel results = Theme.card();
		results.setLayout(new javax.swing.BoxLayout(results, javax.swing.BoxLayout.Y_AXIS));
		lblCalculate = new JLabel(" ");
		lblCalculate.setHorizontalAlignment(SwingConstants.CENTER);
		lblCalculate.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		lblCalculate.setFont(Theme.font(Font.BOLD, 14));
		lblCalculate.setForeground(Theme.ACCENT);
		lblItemsToTarget = new JLabel(" ");
		lblItemsToTarget.setHorizontalAlignment(SwingConstants.CENTER);
		lblItemsToTarget.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		lblItemsToTarget.setFont(Theme.font(Font.PLAIN, 13));
		lblItemsToTarget.setForeground(Theme.TEXT_PRIMARY);
		results.add(lblCalculate);
		results.add(javax.swing.Box.createVerticalStrut(6));
		results.add(lblItemsToTarget);
		wrap.add(results, BorderLayout.CENTER);

		return wrap;
	}

	private JPanel buildSkillCard() {
		JPanel card = Theme.card();
		card.setLayout(new BorderLayout(0, 10));

		JPanel top = new JPanel(new BorderLayout());
		top.setOpaque(false);
		top.add(Theme.sectionTitle("Skill"), BorderLayout.WEST);
		lblListXP = new JLabel(" ");
		lblListXP.setFont(Theme.font(Font.BOLD, 12));
		lblListXP.setForeground(Theme.ACCENT);
		lblListXP.setHorizontalAlignment(SwingConstants.RIGHT);
		top.add(lblListXP, BorderLayout.EAST);
		card.add(top, BorderLayout.NORTH);

		JPanel picker = new JPanel(new BorderLayout());
		picker.setOpaque(false);
		choiceSkill = new JComboBox<String>();
		Theme.styleComboBox(choiceSkill);
		choiceSkill.addItem("Select one");
		s.addSkillItemsToMenu(choiceSkill);
		picker.add(choiceSkill, BorderLayout.NORTH);
		picker.setBorder(new EmptyBorder(0, 0, 8, 0));

		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		Theme.styleList(list);
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
		scroll.getViewport().setBackground(Theme.BG_FIELD);
		scroll.setPreferredSize(new Dimension(10, 10));

		JPanel center = new JPanel(new BorderLayout());
		center.setOpaque(false);
		center.add(picker, BorderLayout.NORTH);
		center.add(scroll, BorderLayout.CENTER);
		card.add(center, BorderLayout.CENTER);

		return card;
	}

	// ------------------------------------------------------------------
	// Item XP window
	// ------------------------------------------------------------------

	private void buildItemXpWindow() {
		itemXP = new Window("ItemXP", "Item XP", 300, 240, new BorderLayout(), false, false);
		itemXP.setPos(main.getPosX() + main.getWidth() - 4, main.getPosY());
		itemXP.getContentPane().setBackground(Theme.BG_DEEP);

		JPanel content = new JPanel(new GridBagLayout());
		content.setOpaque(false);
		content.setBorder(new EmptyBorder(18, 18, 18, 18));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		content.add(Theme.sectionTitle("Item XP Calculator"), c);

		c.gridy = 1;
		c.insets = new Insets(0, 0, 14, 0);
		textFieldItemAmount = new JFormattedTextField(Vars.formatterXP);
		Theme.styleTextField(textFieldItemAmount);
		content.add(textFieldItemAmount, c);

		c.gridy = 2;
		c.insets = new Insets(0, 0, 16, 0);
		lblItemXP = new JLabel("N/A");
		lblItemXP.setHorizontalAlignment(SwingConstants.CENTER);
		lblItemXP.setFont(Theme.font(Font.BOLD, 16));
		lblItemXP.setForeground(Theme.ACCENT);
		content.add(lblItemXP, c);

		c.gridy = 3;
		c.insets = new Insets(0, 0, 0, 0);
		btnCalcXP = Theme.primaryButton("Calculate");
		content.add(btnCalcXP, c);

		itemXP.add(content, BorderLayout.CENTER);
	}

	// ------------------------------------------------------------------
	// Listeners
	// ------------------------------------------------------------------

	private void wireListeners() {

		btnItemXP.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						itemXP.setVisible(true);
					}
				});
			}
		});

		chckbxPrestige.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				double rate = Experience.XP_rate;
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Experience.XP_rate = rate * 0.40;
				} else {
					Experience.XP_rate = rate * 1.00;
				}
			}
		});

		mntmDoubleXp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double prst = Experience.XP_rate;
				lblYourExperienceString.setText("DOUBLE XP");
				lblYourExperience.setText("ACTIVE");
				Experience.XP_rate = prst * 2.00;
				mntmDoubleXp.setEnabled(false);
			}
		});
		mntmResetAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetGUI();
			}
		});

		list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			@Override
			public void valueChanged(javax.swing.event.ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				for (int i = 0; i < Vars.skillList.size(); i++) {
					s.updateList(Vars.skillList.get(i), choiceSkill, list, lblListXP);
				}
			}
		});

		choiceSkill.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() != ItemEvent.SELECTED) {
					return;
				}
				if (((String) choiceSkill.getSelectedItem()).equalsIgnoreCase("Select one")) {
					listModel.clear();
					lblYourLevel.setText("N/A");
					lblYourExperience.setText("N/A");
				}
				if (choiceUserOrXP.getSelectedItem().equals("Username")) {
					updateUserStats(user, choiceSkill, lblYourLevel, lblYourExperience);
				} else if (choiceUserOrXP.getSelectedItem().equals("Experience")) {
					lblYourLevel.setText(Integer.toString(Experience.yourCurrentLevel));
					lblYourExperience.setText(NumberFormat.getIntegerInstance().format(Experience.yourCurrentXP));
				}
				lblCalculate.setText(" ");
				lblItemsToTarget.setText(" ");
				for (int i = 0; i < Vars.skillList.size(); i++) {
					s.updateChoices(Vars.skillList.get(i), choiceSkill, list);
					lblListXP.setText(" ");
				}
			}
		});

		textFieldTargetLevel.addKeyListener(digitEditGuard());
		textFieldTargetLevel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textFieldTargetLevel.getText().isEmpty()) {
					Experience.yourTargetLevel = Integer.parseInt(textFieldTargetLevel.getText().replace(",", ""));
				}
			}
		});
		textFieldTargetLevel.addMouseListener(caretFollowsClick);

		textFieldTargetXP.addKeyListener(digitEditGuard());
		textFieldTargetXP.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textFieldTargetXP.getText().isEmpty()) {
					Experience.yourTargetXP = Integer.parseInt(textFieldTargetXP.getText().replace(",", ""));
				}
			}
		});
		textFieldTargetXP.addMouseListener(caretFollowsClick);

		textFieldYourSkillXP.addKeyListener(digitEditGuard());
		textFieldYourSkillXP.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textFieldYourSkillXP.getText().isEmpty() && choiceUserOrXP.getSelectedItem().equals("Experience")) {
					Experience.yourCurrentXP = Long.parseLong(textFieldYourSkillXP.getText().replace(",", ""));
					Experience.setLevelFromXP();
					lblYourLevel.setText(Integer.toString(Experience.yourCurrentLevel));
					lblYourExperience.setText(NumberFormat.getIntegerInstance().format(Experience.yourCurrentXP));
				}
			}
		});
		textFieldYourSkillXP.addMouseListener(caretFollowsClick);

		textFieldUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (user == null || textFieldUsername.getText().isEmpty()) {
					return;
				}
				try {
					user.getUserStats(user.getUsername());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				updateUserStats(user, choiceSkill, lblYourLevel, lblYourExperience);
				if (Experience.yourTotalLevel != null && Experience.yourTotalXP != null) {
					lblTotalLevel.setText(Integer.toString(Experience.yourTotalLevel));
					lblTotalXP.setText(NumberFormat.getIntegerInstance().format(Experience.yourTotalXP));
					setTotalRowVisible(true);
				}
			}
		});
		textFieldUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				user = new User(textFieldUsername.getText());
			}
		});
		textFieldUsername.addMouseListener(caretFollowsClick);

		choiceTargetLevelOrXP.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() != ItemEvent.SELECTED) {
					return;
				}
				java.awt.CardLayout cl = (java.awt.CardLayout) targetSlot.getLayout();
				if (choiceTargetLevelOrXP.getSelectedItem().equals("Target Level")) {
					Vars.formatterXP.setAllowsInvalid(true);
					textFieldTargetXP.setText("");
					Experience.yourTargetXP = 0;
					Vars.formatterXP.setAllowsInvalid(false);
					textFieldTargetXP.setVisible(false);
					textFieldTargetLevel.setVisible(true);
					cl.show(targetSlot, "level");
				} else if (choiceTargetLevelOrXP.getSelectedItem().equals("Target XP")) {
					Vars.formatterLevel.setAllowsInvalid(true);
					textFieldTargetLevel.setText("");
					Experience.yourTargetLevel = 0;
					Vars.formatterLevel.setAllowsInvalid(false);
					textFieldTargetLevel.setVisible(false);
					textFieldTargetXP.setVisible(true);
					cl.show(targetSlot, "xp");
				}
			}
		});

		choiceUserOrXP.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() != ItemEvent.SELECTED) {
					return;
				}
				java.awt.CardLayout cl = (java.awt.CardLayout) inputSlot.getLayout();
				if (choiceUserOrXP.getSelectedItem().equals("Experience")) {
					textFieldUsername.setVisible(false);
					textFieldUsername.setText("");
					lblYourLevel.setText("N/A");
					lblYourExperience.setText("N/A");
					lblItemsToTarget.setText(" ");
					lblCalculate.setText(" ");
					setTotalRowVisible(false);
					lblTotalLevel.setText("");
					lblTotalXP.setText("");
					textFieldYourSkillXP.setVisible(true);
					cl.show(inputSlot, "xp");
					textFieldYourSkillXP.requestFocus();
				} else if (choiceUserOrXP.getSelectedItem().equals("Username")) {
					Vars.formatterXP.setAllowsInvalid(true);
					textFieldYourSkillXP.setVisible(false);
					textFieldYourSkillXP.setText("");
					Vars.formatterXP.setAllowsInvalid(false);
					lblYourLevel.setText("N/A");
					lblYourExperience.setText("N/A");
					lblItemsToTarget.setText(" ");
					lblCalculate.setText(" ");
					textFieldUsername.setVisible(true);
					cl.show(inputSlot, "user");
					textFieldUsername.requestFocus();
				}
			}
		});

		btnCalculate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (choiceUserOrXP.getSelectedItem().equals("Username")) {
					updateUserStats(user, choiceSkill, lblYourLevel, lblYourExperience);
				} else if (choiceUserOrXP.getSelectedItem().equals("Experience")) {
					Experience.setLevelFromXP();
				}
				if (choiceTargetLevelOrXP.getSelectedItem().equals("Target Level")) {
					Experience.setTargetXPFromTargetLevel();
				} else if (choiceTargetLevelOrXP.getSelectedItem().equals("Target XP")) {
					Experience.yourTargetXP = Integer.parseInt(textFieldTargetXP.getText());
				}
				for (int i = 0; i < Vars.skillList.size(); i++) {
					s.calculateLogic(Vars.skillList.get(i), choiceSkill, list, textFieldYourSkillXP, textFieldUsername, textFieldTargetLevel, textFieldTargetXP, lblCalculate, lblItemsToTarget);
				}
			}
		});

		// Item XP ActionListeners

		textFieldItemAmount.addKeyListener(digitEditGuard());
		textFieldItemAmount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textFieldItemAmount.getText().isEmpty()) {
					for (int i = 0; i < listModel.getSize(); i++) {
						if (list.isSelectedIndex(i)) {
							double xp = Vars.getItem(listModel.getElementAt(i)).getExp() * Integer.parseInt(textFieldItemAmount.getText().replaceAll(",", ""));
							lblItemXP.setText(NumberFormat.getNumberInstance().format(xp) + " XP");
						}
					}
				}
			}
		});
		textFieldItemAmount.addMouseListener(caretFollowsClick);
	}

	private KeyAdapter digitEditGuard() {
		return new KeyAdapter() {
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
		};
	}

	private void setTotalRowVisible(boolean visible) {
		totalRow.setVisible(visible);
		lblTotalLevel_Title.setVisible(visible);
		lblTotalXP_Title.setVisible(visible);
		lblTotalLevel.setVisible(visible);
		lblTotalXP.setVisible(visible);
	}

	public void resetGUI() {
		Vars.formatterLevel.setAllowsInvalid(true);
		Vars.formatterXP.setAllowsInvalid(true);
		textFieldYourSkillXP.setText("");
		textFieldTargetXP.setText("");
		textFieldTargetLevel.setText("");
		textFieldUsername.setText("");
		lblListXP.setText(" ");
		lblCalculate.setText(" ");
		lblItemsToTarget.setText(" ");
		lblYourLevel.setText("N/A");
		lblYourExperience.setText("N/A");
		lblYourExperienceString.setText("Experience");
		Experience.XP_rate = 1.00;
		mntmDoubleXp.setEnabled(true);
		listModel.clear();
		choiceSkill.setSelectedIndex(0);
		if (choiceUserOrXP.getSelectedItem().equals("Username")) {
			textFieldUsername.requestFocus();
		} else if (choiceUserOrXP.getSelectedItem().equals("Experience")) {
			textFieldYourSkillXP.requestFocus();
		}
		setTotalRowVisible(false);
		Vars.formatterLevel.setAllowsInvalid(false);
		Vars.formatterXP.setAllowsInvalid(false);
	}

	public void updateUserStats(User user, JComboBox<String> choiceSkill, JLabel lblYourLevel, JLabel lblYourExperience) {
		try {
			user.setSelectedSkill((String) choiceSkill.getSelectedItem());
			user.getUserStats(user.getUsername());
			if (((String) choiceSkill.getSelectedItem()).equalsIgnoreCase("Select one")) {
				lblYourLevel.setText("N/A");
				lblYourExperience.setText("N/A");
			} else {
				lblYourLevel.setText(Integer.toString(user.getSkillLevel((String) choiceSkill.getSelectedItem())));
				lblYourExperience.setText(NumberFormat.getIntegerInstance().format(Experience.yourCurrentXP));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
