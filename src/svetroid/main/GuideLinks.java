package svetroid.main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** External guide/reference links shown under the menu bar, grouped by menu. */
public final class GuideLinks {

	private GuideLinks() {
	}

	/**
	 * Every skill guide menu entry, in menu order. A handful (Agility, Farming,
	 * Fishing, Smithing) have never had a guide written, so they appear in the
	 * menu but stay inert - matching the original app.
	 */
	public static final List<String> SKILL_GUIDE_MENU_ORDER = Arrays.asList("Agility", "Cooking", "Crafting", "Farming", "Firemaking", "Fishing", "Fletching", "Herblore", "Hunting", "Mining", "Runecrafting", "Slayer", "Smithing", "Summoning", "Thieving");

	public static final Map<String, String> SKILL_GUIDES = new LinkedHashMap<>();
	static {
		SKILL_GUIDES.put("Cooking", "http://community.serenps.com/index.php?/topic/1144-1-99-cooking-guide");
		SKILL_GUIDES.put("Crafting", "http://community.serenps.com/index.php?/topic/1202-1-99-crafting-guide/");
		SKILL_GUIDES.put("Firemaking", "http://community.serenps.com/index.php?/topic/1129-1-99-firemaking-guide/");
		SKILL_GUIDES.put("Fletching", "http://community.serenps.com/index.php?/topic/1205-1-99-fletching-guide/");
		SKILL_GUIDES.put("Herblore", "http://community.serenps.com/index.php?/topic/986-1-99-herblore-guide/");
		SKILL_GUIDES.put("Hunting", "http://community.serenps.com/index.php?/topic/1198-1-99-hunter-guide/");
		SKILL_GUIDES.put("Mining", "http://community.serenps.com/index.php?/topic/934-1-99-mining-guide/");
		SKILL_GUIDES.put("Runecrafting", "http://community.serenps.com/index.php?/topic/937-1-99-runecrafting-guide/");
		SKILL_GUIDES.put("Slayer", "http://community.serenps.com/index.php?/topic/1206-comprehensive-slayer-guide/");
		SKILL_GUIDES.put("Summoning", "http://community.serenps.com/index.php?/topic/632-1-99-summoning-guide/");
		SKILL_GUIDES.put("Thieving", "http://community.serenps.com/index.php?/topic/977-1-99-thieving-guide/");
	}

	public static final Map<String, String> CLUE_SCROLLS = new LinkedHashMap<>();
	static {
		CLUE_SCROLLS.put("Map Clues", "http://runescape.wikia.com/wiki/Treasure_Trails/Guide/Maps");
		CLUE_SCROLLS.put("Coordinate Clues", "http://runescape.wikia.com/wiki/Treasure_Trails/Guide/Coordinates");
		CLUE_SCROLLS.put("Emote Clues", "http://runescape.wikia.com/wiki/Treasure_Trails/Guide/Emotes");
	}

	public static final Map<String, String> WORLD_MAP = new LinkedHashMap<>();
	static {
		WORLD_MAP.put("OSRS Map", "http://OSRSmap.com/");
	}

	public static void open(String url) {
		try {
			Desktop.getDesktop().browse(URI.create(url));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
