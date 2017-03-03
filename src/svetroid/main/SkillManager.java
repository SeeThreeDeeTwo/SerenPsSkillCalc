package svetroid.main;

import java.awt.Choice;
import java.awt.List;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class SkillManager {

	private long XPDifference;
	FileLoader fl = new FileLoader();

	public String lblListXPString;

	public void addSkillItemsToMenu(Choice choiceSkill) {
		fl.loadItems();
		for (int i = 0; i < Vars.skillList.size(); i++) {
			if (Vars.skillList.get(i).getLineNumber() > 0) {
				choiceSkill.add(Vars.skillList.get(i).getName());
			}
		}
	}

	public void updateChoices(Skill skill, Choice choiceSkill, List list) {
		if (choiceSkill.getItem(choiceSkill.getSelectedIndex()).equals(skill.getName())) {
			list.removeAll();
			addItemsToList(skill, list);
		}
	}

	public void updateList(Skill skill, Choice choiceSkill, List list, JLabel lblListXP) {
		if (choiceSkill.getItem(choiceSkill.getSelectedIndex()).equals(skill.getName())) {
			changeXP(skill, list, lblListXP);
		}
	}

	public void calculateLogic(Skill skill, Choice choiceSkill, List list, JFormattedTextField textFieldYourSkillXP, JFormattedTextField textFieldUsername, JFormattedTextField textFieldTargetLevel, JFormattedTextField textFieldTargetXP, JLabel lblCalculate, JLabel lblItemsToTarget) {
		if ((!textFieldYourSkillXP.getText().isEmpty() && !textFieldTargetLevel.getText().isEmpty()) || (!textFieldUsername.getText().isEmpty() && !textFieldTargetLevel.getText().isEmpty()) || (!textFieldYourSkillXP.getText().isEmpty() && !textFieldTargetXP.getText().isEmpty()) || (!textFieldUsername.getText().isEmpty() && !textFieldTargetXP.getText().isEmpty())) {
			XPDifference = Experience.yourTargetXP - Experience.yourCurrentXP;
			if (XPDifference <= 0) {
				lblCalculate.setText("Error! XP difference less than 1.");
			} else if (XPDifference > 0) {
				if (choiceSkill.getItem(choiceSkill.getSelectedIndex()).equals(skill.getName())) {
					if (checkLevelValidity(skill, list) == true) {
						doCalc(choiceSkill, list, lblCalculate, lblItemsToTarget);
					} else {
						lblCalculate.setText("");
						lblItemsToTarget.setText("You need " + skill.getItems().get(list.getSelectedIndex()).getLevelReq() + " " + skill.getName() + " for that!");
					}
				}
			} else {
				lblCalculate.setText("Error! No entry in the proper fields.");
			}
		}
	}

	public void doCalc(Choice choiceSkill, List list, JLabel lblCalculate, JLabel labelItemsToTarget) {
		lblCalculate.setText("XP Difference: " + NumberFormat.getInstance(Locale.getDefault()).format(XPDifference));
		if (choiceSkill.getItem(choiceSkill.getSelectedIndex()).equals("Agility")) {
			labelItemsToTarget.setText(NumberFormat.getInstance(Locale.getDefault()).format(Math.ceil(XPDifference / Experience.currentItemXP)) + " laps.");
		} else {
			labelItemsToTarget.setText(NumberFormat.getInstance(Locale.getDefault()).format(Math.ceil(XPDifference / Experience.currentItemXP)) + " " + list.getItem(list.getSelectedIndex()));
		}
	}

	public void changeXP(Skill skill, List list, JLabel lbl) {
		for (int i = 0; i < skill.getItems().size(); i++) {
			if (list.getItem(list.getSelectedIndex()).equals(skill.getItems().get(i).getName())) {
				lbl.setText(String.format("%.1f", (skill.getItems().get(i).getExp() * Experience.XP_rate)) + " XP");
				lblListXPString = lbl.getText();
				Experience.currentItemXP = skill.getItems().get(i).getExp() * Experience.XP_rate;
			}
		}
	}

	public void addItemsToList(Skill skill, List list) {
		for (int i = 0; i < skill.getItems().size(); i++) {
			list.add(skill.getItems().get(i).getName());
		}
	}

	public boolean checkLevelValidity(Skill skill, List list) {
		if (Experience.yourCurrentLevel < skill.getItems().get(list.getSelectedIndex()).getLevelReq()) {
			return false;
		}
		return true;
	}

}