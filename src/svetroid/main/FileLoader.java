package svetroid.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class FileLoader {

	public String skillPath = "/res/skill_items/";
	private int numberOfSkills;

	public void loadItems() {
		Vars.getJarResources(skillPath);
		numberOfSkills = Vars.pathArray.toArray().length;
		for (int i = 0; i < numberOfSkills; i++) {
			readFiles(i);
		}
		Vars.pathArray.clear();
	}

	private void readFiles(int fileIndex) {
		try {

			ArrayList<String> itemNameList = new ArrayList<String>();
			ArrayList<Double> itemXPList = new ArrayList<Double>();
			ArrayList<Integer> itemLevelList = new ArrayList<Integer>();

			int numberOfItems = 0;

			String skillName = FilenameUtils.getBaseName(Vars.pathArray.toArray()[fileIndex].toString());
			String extension = FilenameUtils.getExtension(Vars.pathArray.toArray()[fileIndex].toString());

			if (!extension.equals("")) {

				@SuppressWarnings("unused")
				Skill tempSkill = new Skill(skillName);

				InputStream stream = this.getClass().getResourceAsStream(skillPath + skillName + "." + extension);
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));

				String line;

				while ((line = br.readLine()) != null) {
					if (line.length() > 0) {
						numberOfItems++;
						String[] data = line.split(" ");
						itemNameList.add(data[0].toString());
						itemXPList.add(Double.parseDouble(data[1].toString()));
						itemLevelList.add(Integer.parseInt(data[2].toString()));
					}
				}

				Vars.skillList.get(Vars.getInt(skillName)).setLineNumber(numberOfItems);

				br.close();

				for (int j = 0; j < numberOfItems; j++) {
					Item tempItem = new Item(itemNameList.get(j).replaceAll("_", " "), itemXPList.get(j), itemLevelList.get(j));
					Vars.skillList.get(Vars.getInt(skillName)).addItem(tempItem);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
