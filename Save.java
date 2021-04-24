import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Save {
    static void saveGame(Player player) {
        // Engimon Aktif
        JSONObject engimonDetail = new JSONObject();
        // Name
        engimonDetail.put("name", player.getActiveEngimon().getName());
        // Species
        engimonDetail.put("species", player.getActiveEngimon().getSpecies());

        // Skills
        JSONObject skillDetail = new JSONObject();
        for (Skill skill : player.getActiveEngimon().getSkill()) {
            skillDetail.put("skillName", skill.getSkillName());
            skillDetail.put("skillPower", skill.getSkillPower());
            skillDetail.put("skillMastery", skill.getSkillMastery());

            JSONArray compatibleElements = new JSONArray();
            for (String compatible : skill.getCompatibleElmts()) {
                compatibleElements.add(compatible);
            }
            skillDetail.put("compatibleElements", compatibleElements);
        }

        engimonDetail.put("skills", skillDetail);

        // Elements
        JSONArray elementDetail = new JSONArray();
        for (String element : player.getActiveEngimon().getElement()) {
            elementDetail.add(element);
        }
        engimonDetail.put("elements", elementDetail);

        // Level
        engimonDetail.put("level", player.getActiveEngimon().getLevel());

        // Experience
        engimonDetail.put("experience", player.getActiveEngimon().getExperience());

        // CumulativeExperience
        engimonDetail.put("cumulativeExperience", player.getActiveEngimon().getCumulativeExperience());

        // Parents
        JSONArray parentDetail = new JSONArray();
        for (String parent : player.getActiveEngimon().getParents()) {
            parentDetail.add(parent);
        }
        engimonDetail.put("parents", parentDetail);

        // Message
        engimonDetail.put("message", player.getActiveEngimon().getMessage());

        // Life
        engimonDetail.put("life", player.getActiveEngimon().getLife());

        JSONArray engimonArray = new JSONArray();
        engimonArray.add(engimonDetail);

        try (FileWriter file = new FileWriter("savedat.json")) {

            file.write(engimonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Engimon active = new Engimon("Engi", "Firemon", Skill.randomElements());
        Player test = new Player(active);

        UniqueSkill unique = new UniqueSkill("UNIQUE", 1, 1, Skill.randomElements(), "Icemon");
        SpecialSkill special = new SpecialSkill("SPECIAL", 2, 2, Skill.randomElements(), 10);
        Skill skill = new Skill("BASIC", 1, 1, Skill.randomElements());
        Skill another = new Skill("BASIC2", 1, 2, Skill.randomElements());
        Skill another2 = new Skill("BASIC3", 1, 2, Skill.randomElements());
        Skill another3 = new Skill("BASIC4", 1, 2, Skill.randomElements());

        SkillItem item = new SkillItem(skill, 2);
        SkillItem anotherItem = new SkillItem(another, 1);
        SkillItem anotherItem2 = new SkillItem(another2, 1);
        SkillItem anotherItem3 = new SkillItem(another3, 1);
        SkillItem uniqueItem = new SkillItem(unique, 4);
        SkillItem specialItem = new SkillItem(special, 3);
        Engimon newEngi = new Engimon("Engi2", "Icemon", Skill.randomElements());

        test.addSkillItem(item);
        test.addSkillItem(anotherItem);
        test.addSkillItem(anotherItem2);
        test.addSkillItem(anotherItem3);
        test.addSkillItem(uniqueItem);
        test.addSkillItem(specialItem);
        test.addSkillItem(specialItem);
        test.addEngimon(newEngi);

        saveGame(test);
    }
}
