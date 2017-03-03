package svetroid.main;

public class Item {

	private String itemName;
	private Double itemExp;
	private int itemLevelReq;

	public Item(String itemName, Double itemExp, int itemLevelReq) {
		this.itemName = itemName;
		this.itemExp = itemExp;
		this.itemLevelReq = itemLevelReq;
	}

	public String getName() {
		return itemName;
	}

	public double getExp() {
		return itemExp;
	}

	public int getLevelReq() {
		return itemLevelReq;
	}

}