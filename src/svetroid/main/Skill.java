package svetroid.main;

import java.util.ArrayList;
import java.util.Comparator;

public class Skill {

	private String skillName;
	private ArrayList<Item> itemList;
	private int lineNumber;
	private boolean isAlphabetized = false;

	public Skill(String skillName) {
		this.skillName = skillName;
		this.itemList = new ArrayList<Item>();
		Vars.skillList.add(this);
	}

	public void addItem(Item item) {
		if (!itemList.contains(item)) {
			itemList.add(item);
		}
		if (isAlphabetized) itemList.sort(Comparator.comparing(Item::getName));
	}

	public String getName() {
		return skillName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public ArrayList<Item> getItems() {
		return itemList;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

}