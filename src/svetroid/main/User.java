package svetroid.main;

public class User {

	private String username;
	private String selectedSkill;

	public User(String username) {
		this.username = username;
	}

	public void setSelectedSkill(String selectedSkill) {
		this.selectedSkill = selectedSkill;
	}

	public String getSelectedSkill() {
		return selectedSkill;
	}

	public int getSkillLevel(String selectedSkill) {
		return Experience.yourCurrentLevel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void getUserStats(String username) {
		try {
			Hiscores.hiscoresToString(Hiscores.getHiscores(username));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setUserSkill(getSelectedSkill());
	}

	public void setUserSkill(String selectedSkill) {
		for (int i = 0; i < Hiscores.skillList.length; i++) {
			if (Hiscores.skillList[i].equals(selectedSkill)) {
				Experience.yourCurrentXP = Hiscores.expList.get(i);
				Experience.yourCurrentLevel = Hiscores.levelList.get(i);
			}
		}
	}

}
