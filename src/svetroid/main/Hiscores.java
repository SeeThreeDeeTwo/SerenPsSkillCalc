package svetroid.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class Hiscores {

	public static final String[] skillList = { "Overall", "Attack", "Defence", "Strength", "Constitution", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction", "Summoning", "Dungeoneering", "Divination", "Invention", "Duel Tournament", "Bounty Hunters", "Bounty Hunter Rogues", "Fist of Guthix", "Mobilising Armies", "B.A Attackers", "B.A Defenders", "B.A Collectors", "B.A Healers", "Castle Wars Games", "Conquest" };
	public static final String[] skillDataList = { "Rank", "Level", "Experience" };
	public static long expVSize = skillList.length;
	public static ArrayList<Integer> levelList;
	public static ArrayList<Long> expList;

	public static String hiscoresToString(Vector<String[]> data) {
		expList = new ArrayList<Long>();
		levelList = new ArrayList<Integer>();
		String formatted = "";
		for (int i = 0; i < expVSize; i++) {
			String[] get = data.get(i);

			formatted = formatted + "[" + skillList[i] + "] ";
			for (int i2 = 0; i2 < get.length; i2++) {
				if (get.length == 3 && i2 == 1) {
					levelList.add(Integer.parseInt(get[i2].toString()));
				} else if (get.length == 3 && i2 == 2) {
					expList.add(Long.parseLong(get[i2].toString()));
				}
				formatted = formatted + skillDataList[i2] + ": " + get[i2] + " || ";
			}

			formatted = formatted + "\n";
		}
		Experience.yourTotalLevel = levelList.get(0);
		Experience.yourTotalXP = expList.get(0);

		return formatted;
	}

	public static Vector<String[]> getHiscores(String username) throws Exception {
		Vector<String[]> data = new Vector<String[]>((int) expVSize);
		URL resultsURL = new URL("http://http://serenps.com/hs/?player=" + username);
		BufferedReader resultsBuffer = new BufferedReader(new InputStreamReader(resultsURL.openStream()));

		String current = "";
		while ((current = resultsBuffer.readLine()) != null) {
			data.add(current.split(","));
		}

		resultsBuffer.close();
		return data;
	}

}
