import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Save {
    static JSONObject saveEngimon(Engimon engimon) {
        JSONObject engimonDetail = new JSONObject();
        // Name
        engimonDetail.put("name", engimon.getName());
        // Species
        engimonDetail.put("species", engimon.getSpecies());

        // Skills
        JSONObject skillDetail = new JSONObject();
        JSONArray listSkill = new JSONArray();
        for (Skill skill : engimon.getSkill()) {
            skillDetail.put("skillName", skill.getSkillName());
            skillDetail.put("skillPower", skill.getSkillPower());
            skillDetail.put("skillMastery", skill.getSkillMastery());

            JSONArray compatibleElements = new JSONArray();
            for (String compatible : skill.getCompatibleElmts()) {
                compatibleElements.add(compatible);
            }
            skillDetail.put("compatibleElements", compatibleElements);
            listSkill.add(skillDetail);
        }

        engimonDetail.put("skills", listSkill);

        // Elements
        JSONArray elementDetail = new JSONArray();
        for (String element : engimon.getElement()) {
            elementDetail.add(element);
        }
        engimonDetail.put("elements", elementDetail);

        // Level
        engimonDetail.put("level", engimon.getLevel());

        // Experience
        engimonDetail.put("experience", engimon.getExperience());

        // CumulativeExperience
        engimonDetail.put("cumulativeExperience", engimon.getCumulativeExperience());

        // Parents
        JSONArray parentDetail = new JSONArray();
        for (String parent : engimon.getParents()) {
            parentDetail.add(parent);
        }
        engimonDetail.put("parents", parentDetail);

        // Message
        engimonDetail.put("message", engimon.getMessage());

        // Life
        engimonDetail.put("life", engimon.getLife());

        JSONObject engimonObj = new JSONObject();

        return engimonDetail;
    }

    static JSONObject saveSkillItem(SkillItem skillitem) {
        JSONObject skillItemObj = new JSONObject();
        JSONObject skill = new JSONObject();

        Skill skillToSave = skillitem.getSkill();

        skill.put("skillName", skillToSave.getSkillName());
        skill.put("skillPower", skillToSave.getSkillPower());
        skill.put("skillMastery", skillToSave.getSkillMastery());

        JSONArray compatibleElements = new JSONArray();
        for (String compatible : skillToSave.getCompatibleElmts()) {
            compatibleElements.add(compatible);
        }
        skill.put("compatibleElements", compatibleElements);

        skillItemObj.put("skill", skill);
        skillItemObj.put("amount", skillitem.getAmount());

        return skillItemObj;
    }

    static void saveGame(Player player) {
        // Engimon Aktif
        JSONObject engimonAktifDetail = saveEngimon(player.getActiveEngimon());
        JSONObject engimonObj = new JSONObject();

        engimonObj.put("engimonAktif", engimonAktifDetail);

        JSONArray playerArray = new JSONArray();
        playerArray.add(engimonObj);

        // Inventory Engimon
        JSONObject inventoryEngimon = new JSONObject();
        JSONArray listEngimon = new JSONArray();

        for (Engimon engimon : player.getEngimonInvent().getItemList()) {
            JSONObject tmpEngimonObj = saveEngimon(engimon);
            listEngimon.add(tmpEngimonObj);
        }
        inventoryEngimon.put("inventoryEngimon", listEngimon);
        playerArray.add(inventoryEngimon);

        // Inventory SkillItem
        JSONObject inventorySkillItem = new JSONObject();
        JSONArray listSkillItems = new JSONArray();

        for (SkillItem skillitem : player.getSkillInvent().getItemList()) {
            JSONObject tmpSkillItem = saveSkillItem(skillitem);
            listSkillItems.add(tmpSkillItem);
        }
        inventorySkillItem.put("inventorySkillItem", listSkillItems);
        playerArray.add(inventorySkillItem);

        JSONObject playerNameObject = new JSONObject();
        playerNameObject.put("playerName", player.getPlayerName());
        playerArray.add(playerNameObject);

        // Save to savedat.json
        try (FileWriter file = new FileWriter("savedat.json")) {

            file.write(playerArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Engimon active = new Engimon("Engi", "Firemon", Skill.randomElements());
        ArrayList<String> elements = new ArrayList<String>();
        elements.add("FIRE");
        active.addSkill(new Skill("Fireball", 20, 1, elements));
        active.addSkill(new Skill("Waterball", 20, 1, elements));
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
