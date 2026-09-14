package svetroid.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * Central visual identity for the app: a dark, "runestone" palette pulled
 * from the app icon, plus factory methods for the hand-painted flat/rounded
 * widgets used throughout the UI.
 */
public final class Theme {

	public static final Color BG_DEEP = new Color(0x11, 0x15, 0x19);
	public static final Color BG_PANEL = new Color(0x18, 0x1e, 0x23);
	public static final Color BG_CARD = new Color(0x1f, 0x27, 0x2d);
	public static final Color BG_CARD_HOVER = new Color(0x25, 0x2e, 0x35);
	public static final Color BG_FIELD = new Color(0x14, 0x19, 0x1d);
	public static final Color BORDER = new Color(0x2c, 0x37, 0x3d);
	public static final Color BORDER_LIGHT = new Color(0x3a, 0x47, 0x4e);

	public static final Color ACCENT = new Color(0x35, 0xd6, 0x9e);
	public static final Color ACCENT_DARK = new Color(0x1c, 0x8f, 0x69);
	public static final Color ACCENT_TEXT = new Color(0x0b, 0x17, 0x14);

	public static final Color TEXT_PRIMARY = new Color(0xec, 0xf2, 0xf1);
	public static final Color TEXT_SECONDARY = new Color(0x8f, 0xa1, 0x9e);
	public static final Color TEXT_MUTED = new Color(0x5c, 0x6b, 0x69);

	public static final Color SUCCESS = new Color(0x35, 0xd6, 0x9e);
	public static final Color ERROR = new Color(0xe2, 0x6d, 0x63);

	private static Font baseFont = new Font(Font.SANS_SERIF, Font.PLAIN, 13);

	private Theme() {
	}

	// ---- Look and feel -------------------------------------------------

	public static void install(Font customFont) {
		if (customFont != null) {
			baseFont = customFont.deriveFont(13f);
		}
		javax.swing.JPopupMenu.setDefaultLightWeightPopupEnabled(true);
		try {
			MetalLookAndFeel.setCurrentTheme(new RuneMetalTheme());
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		UIManager.put("ToolTip.background", BG_CARD);
		UIManager.put("ToolTip.foreground", TEXT_PRIMARY);
		UIManager.put("ToolTip.border", BorderFactory.createLineBorder(BORDER_LIGHT));

		// MetalTheme's derivation of menu colors is inconsistent across JDKs;
		// set the keys menu/menubar UIs actually paint with directly.
		UIManager.put("MenuBar.background", BG_PANEL);
		UIManager.put("MenuBar.foreground", TEXT_PRIMARY);
		UIManager.put("MenuBar.border", BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));
		UIManager.put("Menu.background", BG_PANEL);
		UIManager.put("Menu.foreground", TEXT_PRIMARY);
		UIManager.put("Menu.selectionBackground", ACCENT_DARK);
		UIManager.put("Menu.selectionForeground", TEXT_PRIMARY);
		UIManager.put("Menu.borderPainted", Boolean.FALSE);
		UIManager.put("MenuItem.background", BG_PANEL);
		UIManager.put("MenuItem.foreground", TEXT_PRIMARY);
		UIManager.put("MenuItem.selectionBackground", ACCENT_DARK);
		UIManager.put("MenuItem.selectionForeground", TEXT_PRIMARY);
		UIManager.put("MenuItem.disabledForeground", TEXT_MUTED);
		UIManager.put("PopupMenu.background", BG_PANEL);
		UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(BORDER_LIGHT));
		UIManager.put("CheckBoxMenuItem.background", BG_PANEL);
		UIManager.put("CheckBoxMenuItem.foreground", TEXT_PRIMARY);
		UIManager.put("CheckBoxMenuItem.selectionBackground", ACCENT_DARK);
		UIManager.put("CheckBoxMenuItem.selectionForeground", TEXT_PRIMARY);
	}

	public static Font font(int style, int size) {
		return baseFont.deriveFont(style, (float) size);
	}

	// ---- Layout helpers --------------------------------------------------

	/** A rounded, slightly-raised container used to group related controls. */
	public static JPanel card() {
		JPanel p = new RoundedPanel(BG_CARD, BORDER, 14);
		p.setBorder(new EmptyBorder(14, 16, 14, 16));
		return p;
	}

	public static JLabel sectionTitle(String text) {
		JLabel l = new JLabel(text.toUpperCase());
		l.setFont(font(Font.BOLD, 11));
		l.setForeground(ACCENT);
		return l;
	}

	public static JLabel heading(String text, int size) {
		JLabel l = new JLabel(text);
		l.setFont(font(Font.BOLD, size));
		l.setForeground(TEXT_PRIMARY);
		return l;
	}

	public static JLabel body(String text) {
		JLabel l = new JLabel(text);
		l.setFont(font(Font.PLAIN, 13));
		l.setForeground(TEXT_SECONDARY);
		return l;
	}

	public static JLabel value(String text) {
		JLabel l = new JLabel(text);
		l.setFont(font(Font.BOLD, 14));
		l.setForeground(TEXT_PRIMARY);
		return l;
	}

	// ---- Controls ----------------------------------------------------

	public static JButton primaryButton(String text) {
		return new FlatButton(text, ACCENT, ACCENT_TEXT, ACCENT.darker());
	}

	public static JButton secondaryButton(String text) {
		return new FlatButton(text, BG_CARD_HOVER, TEXT_PRIMARY, BORDER_LIGHT);
	}

	public static void styleTextField(JFormattedTextField field) {
		field.setBackground(BG_FIELD);
		field.setForeground(TEXT_PRIMARY);
		field.setCaretColor(ACCENT);
		field.setFont(font(Font.PLAIN, 13));
		field.setBorder(new CompoundRoundedBorder());
		field.setSelectionColor(ACCENT_DARK);
		field.setSelectedTextColor(TEXT_PRIMARY);
	}

	public static void styleComboBox(JComboBox<String> combo) {
		combo.setBackground(BG_FIELD);
		combo.setForeground(TEXT_PRIMARY);
		combo.setFont(font(Font.PLAIN, 13));
		combo.setBorder(new EmptyBorder(2, 2, 2, 2));
		combo.setRenderer(new javax.swing.DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object v, int idx, boolean sel, boolean focus) {
				Component c = super.getListCellRendererComponent(list, v, idx, sel, focus);
				c.setFont(font(Font.PLAIN, 13));
				c.setBackground(sel ? BG_CARD_HOVER : BG_FIELD);
				c.setForeground(sel ? ACCENT : TEXT_PRIMARY);
				setBorder(new EmptyBorder(4, 8, 4, 8));
				return c;
			}
		});
	}

	public static void styleList(JList<String> list) {
		list.setBackground(BG_FIELD);
		list.setForeground(TEXT_PRIMARY);
		list.setFont(font(Font.PLAIN, 13));
		list.setSelectionBackground(ACCENT_DARK);
		list.setSelectionForeground(TEXT_PRIMARY);
		list.setFixedCellHeight(26);
		list.setBorder(new EmptyBorder(4, 8, 4, 8));
		list.setCellRenderer(new javax.swing.DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> l, Object v, int idx, boolean sel, boolean focus) {
				Component c = super.getListCellRendererComponent(l, v, idx, sel, focus);
				c.setFont(font(Font.PLAIN, 13));
				setBorder(new EmptyBorder(3, 8, 3, 8));
				return c;
			}
		});
	}

	// Menus use the platform's own sans-serif font rather than the custom
	// loaded TTF: pairing that font with JMenuItem's paint path leaves popup
	// menus unpainted (background never filled) on at least one JDK/X11
	// combination, even though every other component renders it fine.
	private static final Font MENU_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 13);

	public static void styleMenu(javax.swing.JMenu menu) {
		menu.setBackground(BG_PANEL);
		menu.setForeground(TEXT_PRIMARY);
		menu.setFont(MENU_FONT);
		menu.setOpaque(true);
		javax.swing.JPopupMenu popup = menu.getPopupMenu();
		popup.setBackground(BG_PANEL);
		popup.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT));
		// Force a lightweight (pure-Swing-painted) popup: some environments
		// promote menus to heavyweight AWT panels, which paint their native
		// background before Swing's colors are composited on top.
		popup.setLightWeightPopupEnabled(true);
	}

	public static void styleMenuItem(javax.swing.JMenuItem item) {
		item.setBackground(BG_PANEL);
		item.setForeground(TEXT_PRIMARY);
		item.setFont(MENU_FONT);
		item.setOpaque(true);
	}

	public static void styleCheckBox(JCheckBox box) {
		box.setForeground(TEXT_SECONDARY);
		box.setFont(font(Font.PLAIN, 13));
		box.setOpaque(false);
		box.setFocusPainted(false);
		box.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	// ---- Custom painted components -----------------------------------

	/** Simple rounded-rect panel used as a card background. */
	private static class RoundedPanel extends JPanel {
		private final Color fill;
		private final Color stroke;
		private final int radius;

		RoundedPanel(Color fill, Color stroke, int radius) {
			this.fill = fill;
			this.stroke = stroke;
			this.radius = radius;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(fill);
			g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.setColor(stroke);
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.dispose();
			super.paintComponent(g);
		}
	}

	/** Flat, rounded button with a hover/press state, no native chrome. */
	private static class FlatButton extends JButton {
		private final Color base;
		private final Color fg;
		private final Color hover;
		private boolean hovering = false;

		FlatButton(String text, Color base, Color fg, Color hover) {
			super(text);
			this.base = base;
			this.fg = fg;
			this.hover = hover;
			setFont(font(Font.BOLD, 13));
			setForeground(fg);
			setContentAreaFilled(false);
			setFocusPainted(false);
			setBorderPainted(false);
			setOpaque(false);
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			setBorder(new EmptyBorder(9, 18, 9, 18));
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					hovering = true;
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					hovering = false;
					repaint();
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Color fill = !isEnabled() ? BG_CARD_HOVER : (getModel().isPressed() ? hover : (hovering ? hover : base));
			g2.setColor(fill);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
			g2.dispose();
			setForeground(isEnabled() ? fg : TEXT_MUTED);
			super.paintComponent(g);
		}
	}

	/** Rounded border + padding combined, for text fields. */
	private static class CompoundRoundedBorder implements Border {
		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(6, 10, 6, 10);
		}

		@Override
		public boolean isBorderOpaque() {
			return false;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(1.2f));
			g2.setColor(c.isFocusOwner() ? ACCENT : BORDER_LIGHT);
			g2.drawRoundRect(x, y, w - 1, h - 1, 8, 8);
			g2.dispose();
		}
	}

	/** Metal theme wired to the runestone palette so native widgets (menus, scrollbars) match. */
	private static class RuneMetalTheme extends DefaultMetalTheme {
		@Override
		public String getName() {
			return "SerenPS";
		}

		private final FontUIResource controlFont = new FontUIResource(baseFont.deriveFont(Font.PLAIN, 13f));
		private final FontUIResource menuFont = new FontUIResource(MENU_FONT);
		private final FontUIResource titleFont = new FontUIResource(baseFont.deriveFont(Font.BOLD, 13f));

		private final ColorUIResource primary1 = new ColorUIResource(ACCENT_DARK);
		private final ColorUIResource primary2 = new ColorUIResource(BG_CARD_HOVER);
		private final ColorUIResource primary3 = new ColorUIResource(ACCENT);
		private final ColorUIResource secondary1 = new ColorUIResource(BORDER);
		private final ColorUIResource secondary2 = new ColorUIResource(BG_CARD);
		private final ColorUIResource secondary3 = new ColorUIResource(BG_PANEL);
		private final ColorUIResource black = new ColorUIResource(TEXT_PRIMARY);
		private final ColorUIResource white = new ColorUIResource(BG_FIELD);

		@Override
		protected ColorUIResource getPrimary1() {
			return primary1;
		}

		@Override
		protected ColorUIResource getPrimary2() {
			return primary2;
		}

		@Override
		protected ColorUIResource getPrimary3() {
			return primary3;
		}

		@Override
		protected ColorUIResource getSecondary1() {
			return secondary1;
		}

		@Override
		protected ColorUIResource getSecondary2() {
			return secondary2;
		}

		@Override
		protected ColorUIResource getSecondary3() {
			return secondary3;
		}

		@Override
		public ColorUIResource getBlack() {
			return black;
		}

		@Override
		public ColorUIResource getWhite() {
			return white;
		}

		@Override
		public ColorUIResource getControlTextColor() {
			return black;
		}

		@Override
		public ColorUIResource getSystemTextColor() {
			return black;
		}

		@Override
		public ColorUIResource getUserTextColor() {
			return black;
		}

		@Override
		public ColorUIResource getMenuForeground() {
			return black;
		}

		@Override
		public FontUIResource getControlTextFont() {
			return controlFont;
		}

		@Override
		public FontUIResource getSystemTextFont() {
			return controlFont;
		}

		@Override
		public FontUIResource getUserTextFont() {
			return controlFont;
		}

		@Override
		public FontUIResource getMenuTextFont() {
			return menuFont;
		}

		@Override
		public FontUIResource getWindowTitleFont() {
			return titleFont;
		}

		@Override
		public FontUIResource getSubTextFont() {
			return controlFont;
		}
	}

}
