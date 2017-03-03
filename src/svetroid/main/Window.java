package svetroid.main;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

public class Window {

	public static final ArrayList<Window> windowList = new ArrayList<Window>();

	private String name;
	private JFrame jframe;
	private int width;
	private int height;
	private int xPos;
	private int yPos;

	public void start() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Window(String name, String title, int width, int height, LayoutManager lm, boolean isResizable, boolean isVisible) {
		setUI(Vars.fontList.get(0), 12);
		this.name = name;
		this.width = width;
		this.height = height;
		jframe = new JFrame();
		jframe.setIconImages(Vars.iconImageList);
		jframe.setResizable(isResizable);
		jframe.setTitle(title);
		jframe.setSize(width, height);
		jframe.getContentPane().setLayout(lm);
		if (getName().equalsIgnoreCase("Main")) {
			this.setPos(Vars.screenSize.width / 2 - width / 2, Vars.screenSize.height / 2 - height / 1);
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			jframe.setLocationRelativeTo(null);
			jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		jframe.setVisible(isVisible);
		windowList.add(this);
	}

	public void update() {
		getFrame().revalidate();
		getFrame().repaint();
	}

	public static void updateAll() {
		for (int i = 0; i < windowList.size(); i++) {
			windowList.get(i).update();
		}
	}

	public JFrame getFrame() {
		return this.jframe;
	}

	public String getName() {
		return this.name;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.getFrame().setLocation(xPos, yPos);
	}

	public int getPosX() {
		return this.xPos;
	}

	public int getPosY() {
		return this.yPos;
	}

	public void setVisible(boolean isVisible) {
		this.getFrame().setVisible(isVisible);
	}

	public void requestFocus() {
		this.getFrame().setVisible(true);
		this.getFrame().toFront();
		this.getFrame().requestFocus();
	}

	public void add(Component comp) {
		jframe.add(comp);
	}

	public void add(Component comp, boolean isVisible) {
		jframe.add(comp);
		comp.setVisible(isVisible);
	}

	public void add(Component comp, int x, int y, int width, int height) {
		comp.setBounds(x, y, width, height);
		jframe.add(comp);
	}

	public void add(Component comp, int x, int y, int width, int height, boolean isVisible) {
		comp.setBounds(x, y, width, height);
		jframe.add(comp);
		comp.setVisible(isVisible);
	}

	public void destroy() {
		windowList.remove(this);
		this.jframe.dispose();
	}

	private void setUI(Font font, int fontSize) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setUIFont(new FontUIResource(font.deriveFont(Font.PLAIN, fontSize)));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void setUIFont(FontUIResource fr) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof FontUIResource) {
				UIManager.put(key, fr);
			}
		}
	}

}