package svetroid.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.stream.Stream;

import javax.swing.text.NumberFormatter;

import org.apache.commons.io.FilenameUtils;

public class Vars {

	public static final String version = "2017.03.03_12.36";

	public Vars() {
	}

	// Screen size

	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// Jar resources

	public static ArrayList<Path> pathArray = new ArrayList<Path>();
	public static URI pathURI;

	public static void getJarResources(String dir) {
		try {
			pathURI = FileLoader.class.getResource(dir).toURI();
			Path myPath;
			if (pathURI.getScheme().equals("jar")) {
				FileSystem fileSystem = FileSystems.newFileSystem(pathURI, Collections.<String, Object>emptyMap());
				myPath = fileSystem.getPath(dir);
				walkPath(myPath);
				fileSystem.close();
			} else {
				myPath = Paths.get(pathURI);
				walkPath(myPath);
			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void walkPath(Path myPath) {
		Stream<Path> walk;
		try {
			walk = Files.walk(myPath, 1);
			for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
				pathArray.add(it.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Fonts

	public static ArrayList<Font> fontList = new ArrayList<Font>();
	public static String fontPath = "/res/font/";
	public static int numberOfFonts;

	public static void loadFonts() {
		Vars.getJarResources(fontPath);
		numberOfFonts = Vars.pathArray.toArray().length;
		for (int i = 0; i < numberOfFonts; i++) {
			String fontName;
			String extension = FilenameUtils.getExtension(Vars.pathArray.toArray()[i].toString());
			if (!extension.equals("")) {
				try {
					fontName = FilenameUtils.getBaseName(Vars.pathArray.toArray()[i].toString());
					Font customFont = Font.createFont(Font.TRUETYPE_FONT, Vars.class.getResourceAsStream(fontPath + fontName + "." + extension));
					fontList.add(customFont);
				} catch (FontFormatException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		pathArray.clear();
	}

	// Icons

	public static final ArrayList<Image> iconImageList = new ArrayList<Image>();
	public static String iconPath = "/res/icon/";
	public static int numberOfIcons;

	public static void loadIcons() {
		Vars.getJarResources(iconPath);
		numberOfIcons = Vars.pathArray.toArray().length;
		for (int i = 0; i < numberOfIcons; i++) {
			String iconName;
			String extension = FilenameUtils.getExtension(Vars.pathArray.toArray()[i].toString());
			if (!extension.equals("")) {
				iconName = FilenameUtils.getBaseName(Vars.pathArray.toArray()[i].toString());
				iconImageList.add(Toolkit.getDefaultToolkit().getImage(Vars.class.getResource(iconPath + iconName + "." + extension)));
			}
		}
		pathArray.clear();
	}

	// Number formats

	public static final Locale currentLocale = Locale.getDefault();
	public static final NumberFormat numFormat = NumberFormat.getInstance(currentLocale);
	public static final NumberFormatter formatterLevel = new NumberFormatter(numFormat);
	public static final NumberFormatter formatterXP = new NumberFormatter(numFormat);

	public static void setFormats() {
		formatterLevel.setValueClass(Long.class);
		formatterLevel.setAllowsInvalid(false);
		formatterLevel.setMinimum(0l);
		formatterLevel.setMaximum(126l);

		formatterXP.setValueClass(Long.class);
		formatterXP.setAllowsInvalid(false);
		formatterXP.setMinimum(0l);
		formatterXP.setMaximum(200000000l);
	}

	// Skill list

	public static final ArrayList<Skill> skillList = new ArrayList<Skill>();

	public static Item getItem(String itemName) {
		for (int i = 0; i < skillList.size(); i++) {
			for (int j = 0; j < skillList.get(i).getItems().size(); j++)
				if (itemName.equalsIgnoreCase(skillList.get(i).getItems().get(j).getName())) {
					return skillList.get(i).getItems().get(j);
				}
		}
		return null;
	}

	public static int getInt(String skillName) {
		for (int i = 0; i < Vars.skillList.size(); i++) {
			if (Vars.skillList.get(i).getName().equals(skillName)) {
				return i;
			}
		}
		return -1;
	}

}
