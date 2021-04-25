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
    public static Engimon extractActiveEngimon() {
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
            return engimonRes;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Engimon> extractInventoryEngimon() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("savedat.json")) {
            Object obj = jsonParser.parse(reader);

            ArrayList<Engimon> listEngimon = new ArrayList<Engimon>();

            JSONArray data = (JSONArray) obj;
            JSONObject dataInventoryEngimon = (JSONObject) data.get(1);

            JSONArray listData = (JSONArray) dataInventoryEngimon.get("inventoryEngimon");

            for (int i = 0; i < listData.size(); i++) {
                JSONObject tmpEngimonObject = (JSONObject) listData.get(i);

                String name = (String) tmpEngimonObject.get("name");
                String species = (String) tmpEngimonObject.get("species");
                Long level = (Long) tmpEngimonObject.get("level");
                int intLevel = level.intValue();
                Long exp = (Long) tmpEngimonObject.get("experience");
                int intExp = exp.intValue();
                String message = (String) tmpEngimonObject.get("message");
                Long life = (Long) tmpEngimonObject.get("life");
                int intLife = life.intValue();
                Long cumulativeExperience = (Long) tmpEngimonObject.get("cumulativeExperience");
                int intCumulativeExperience = cumulativeExperience.intValue();

                JSONArray skills = (JSONArray) tmpEngimonObject.get("skills");
                ArrayList<Skill> listSkill = new ArrayList<Skill>();
                for (int j = 0; j < skills.size(); j++) {
                    JSONObject tmpSkill = (JSONObject) skills.get(j);
                    ArrayList<String> compatibleElements = new ArrayList<String>();
                    JSONArray listCompatible = (JSONArray) tmpSkill.get("compatibleElements");
                    for (int k = 0; k < listCompatible.size(); k++) {
                        String stringElement = (String) listCompatible.get(k);
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

                JSONArray elements = (JSONArray) tmpEngimonObject.get("elements");
                ArrayList<String> elementList = new ArrayList<String>();
                for (int j = 0; j < elements.size(); j++) {
                    String stringElement = (String) elements.get(j);
                    elementList.add(stringElement);
                }

                JSONArray parents = (JSONArray) tmpEngimonObject.get("parents");
                ArrayList<String> parentList = new ArrayList<String>();
                for (int j = 0; j < parents.size(); j++) {
                    String stringParent = (String) parents.get(j);
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
                listEngimon.add(engimonRes);
            }
            return listEngimon;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<SkillItem> extractInventorySkillItem() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("savedat.json")) {
            Object obj = jsonParser.parse(reader);

            JSONArray data = (JSONArray) obj;
            JSONObject dataInventorySkillItem = (JSONObject) data.get(2);

            JSONArray listData = (JSONArray) dataInventorySkillItem.get("inventorySkillItem");

            ArrayList<SkillItem> listRes = new ArrayList<SkillItem>();

            for (int i = 0; i < listData.size(); i++) {
                JSONObject dataSkillItem = (JSONObject) listData.get(i);
                Long amount = (Long) dataSkillItem.get("amount");
                int intAmount = amount.intValue();

                JSONObject skill = (JSONObject) dataSkillItem.get("skill");
                String skillName = (String) skill.get("skillName");
                Long skillMastery = (Long) skill.get("skillMastery");
                int intSkillMastery = skillMastery.intValue();

                JSONArray compatibleElements = (JSONArray) skill.get("compatibleElements");
                ArrayList<String> compatElements = new ArrayList<String>();
                for (int j = 0; j < compatibleElements.size(); j++) {
                    String elmt = (String) compatibleElements.get(j);
                    compatElements.add(elmt);
                }
                Long skillPower = (Long) skill.get("skillPower");
                int intSkillPower = skillPower.intValue();

                Skill skillRes = new Skill(skillName, intSkillPower, intSkillMastery, compatElements);
                SkillItem skillItemRes = new SkillItem(skillRes, intAmount);

                listRes.add(skillItemRes);
            }
            return listRes;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String extractPlayerName() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("savedat.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            JSONObject dataPlayerName = (JSONObject) data.get(3);

            String playerName = (String) dataPlayerName.get("playerName");
            return playerName;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Engimon activeEngimon = extractActiveEngimon();
        activeEngimon.showStats();
        ArrayList<Engimon> listInventoryEngimon = extractInventoryEngimon();
        for (Engimon engimon : listInventoryEngimon) {
            System.out.println(engimon.getName());
        }
        System.out.println("");
        ArrayList<SkillItem> listSkillItem = extractInventorySkillItem();
        for (SkillItem skillitem : listSkillItem) {
            System.out.println(skillitem.getAmount());
            System.out.println(skillitem.getSkillInfo());
        }
        String playerName = extractPlayerName();
        System.out.println(playerName);
    }
}
