import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.String;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Load {
    private static void extractActiveEngimon() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("savedat.json")) {
            Object obj = jsonParser.parse(reader);

            JSONArray data = (JSONArray) obj;
            JSONObject dataEngimonAktif = (JSONObject) data.get(0);

            JSONObject engimonAktif = (JSONObject) dataEngimonAktif.get("engimonAktif");
            String name = (String) engimonAktif.get("name");
            String species = (String) engimonAktif.get("species");
            Long level = (Long) engimonAktif.get("level");
            int intLevel = level.intValue();
            Long exp = (Long) engimonAktif.get("experience");
            int intExp = exp.intValue();
            String message = (String) engimonAktif.get("message");
            Long life = (Long) engimonAktif.get("life");
            int intLife = life.intValue();
            Long cumulativeExperience = (Long) engimonAktif.get("cumulativeExperience");
            int intCumulativeExperience = cumulativeExperience.intValue();

            JSONArray skills = (JSONArray) engimonAktif.get("skills");
            ArrayList<Skill> listSkill = new ArrayList<Skill>();
            for (int i = 0; i < skills.size(); i++) {
                JSONObject tmpSkill = (JSONObject) skills.get(i);
                ArrayList<String> compatibleElements = new ArrayList<String>();
                JSONArray listCompatible = (JSONArray) tmpSkill.get("compatibleElements");
                for (int j = 0; j < listCompatible.size(); j++) {
                    String stringElement = (String) listCompatible.get(j);
                    compatibleElements.add(stringElement);
                }
                String stringSkillName = (String) tmpSkill.get("skillName");
                Long skillPower = (Long) tmpSkill.get("skillPower");
                int intSkillPower = skillPower.intValue();
                Long skillMastery = (Long) tmpSkill.get("skillMastery");
                int intSkillMastery = skillMastery.intValue();
                Skill skill = new Skill(stringSkillName, intSkillPower, intSkillMastery, compatibleElements);
                listSkill.add(skill);
            }

            JSONArray elements = (JSONArray) engimonAktif.get("elements");
            ArrayList<String> elementList = new ArrayList<String>();
            for (int i = 0; i < elements.size(); i++) {
                String stringElement = (String) elements.get(i);
                elementList.add(stringElement);
            }

            JSONArray parents = (JSONArray) engimonAktif.get("parents");
            ArrayList<String> parentList = new ArrayList<String>();
            for (int i = 0; i < parents.size(); i++) {
                String stringParent = (String) parents.get(i);
                parentList.add(stringParent);
            }

            Engimon engimonRes = new Engimon(name, species, elementList);
            engimonRes.setLevel(intLevel);
            engimonRes.setExp(intExp);
            engimonRes.setMessage(message);
            engimonRes.setLife(intLife);
            engimonRes.setSkills(listSkill);
            engimonRes.setParents(parentList);
            engimonRes.setCumulativeExperience(intCumulativeExperience);
            engimonRes.showStats();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        extractActiveEngimon();
    }
}
