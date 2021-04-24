import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
            Long exp = (Long) engimonAktif.get("experience");
            String message = (String) engimonAktif.get("message");
            Long life = (Long) engimonAktif.get("life");

            System.out.println(level);
            System.out.println(life);

            JSONArray skills = (JSONArray) engimonAktif.get("skills");
            ArrayList<Skill> skill = new ArrayList<Skill>();
            for (Object tmp : skills) {
                System.out.println(tmp);
                // Skill tmpSkill = new Skill(tmp.skillName, tmp.skillPower, tmp.skillMastery);
                // skill.add(tmpSkill);
            }

            // JSONArray elements = engimonAktif.get("elements");
            // ArrayList<String> elementList = new ArrayList<String>();
            // for (String tmp : elements) {
            // elementList.add(tmp);
            // }

            // JSONArray parents = engimonAktif.get("parents");
            // ArrayList<String> parentList = new ArrayList<String>();
            // for (String parent : parentList) {
            // parentList.add(parent);
            // }

            // Engimon engimonRes = new Engimon(name, species, elementList);
            // engimonRes.setLevel(level);
            // engimonRes.setExp(exp);
            // engimonRes.setMessage(message);
            // engimonRes.setLife(life);
            // engimonRes.setSkills(skill);
            // engimonRes.setParents(parentList);
            // engimonRes.showStats();

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
