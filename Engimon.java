import java.util.*;

abstract class AbstractEngimon {
    abstract public String getName();

    abstract public String getSpecies();

    abstract public ArrayList<String> getElement();

    abstract public int getLevel();

    abstract public int getExperience();

    abstract public int getCumulativeExperience();

    abstract public ArrayList<Skill> getSkill();

    abstract public String getMessage();

    abstract public void setLevel(int level);

    abstract public void setMessage(String newMessage);

    abstract public void showStats();

    abstract public void addExp(int exp);
}

public class Engimon extends AbstractEngimon {
    private String name;
    private String species;
    private ArrayList<Skill> skill;
    private ArrayList<String> element;
    private int level;
    private int experience;
    private int cumulativeExperience;
    private ArrayList<String> parents;
    private String message;

    public Engimon() {
        this.name = null;
        this.species = null;
        this.skill = null;
        this.element = null;
        this.level = -1;
        this.experience = -1;
        this.cumulativeExperience = -1;
        this.parents = null;
        this.message = null;
    }

    public Engimon(String nama, String species, ArrayList<String> element) {
        this.name = nama;
        this.element = element;
        this.species = species;
        this.level = 1;
        this.experience = 1;
        this.cumulativeExperience = 0;
        this.message = "I'm an Engimon";
        this.parents = new ArrayList<String>(Arrays.asList(new String[2]));
        this.skill = new ArrayList<Skill>(Arrays.asList(new Skill[4]));
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public String getSpecies() {
        return this.species;
    }

    public ArrayList<String> getElement() {
        return this.element;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getCumulativeExperience() {
        return this.cumulativeExperience;
    }

    public ArrayList<Skill> getSkill() {
        return this.skill;
    }

    public String getMessage() {
        return this.message;
    }

    // Setters
    public void setLevel(int level) {
        this.level = level;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    public void setParents(ArrayList<String> parents) {
        this.parents = parents;
    }

    public void showStats() {
        System.out.printf("Nama : %s\n", this.getName());
        System.out.printf("Spesies : %s\n", this.getSpecies());
        System.out.print("Parents : ");
        if (this.parents.get(0) != "") {
            System.out.print(this.parents.get(0));
            System.out.print(", ");
            System.out.println(this.parents.get(1));
        } else {
            System.out.println("None");
        }
        // Show Skills
        System.out.print("Element : ");
        if (this.element.get(0) != this.element.get(1)) {
            System.out.printf("%s / %s\n", this.element.get(0), this.element.get(1));
        } else {
            System.out.println(this.element.get(0));
        }
        System.out.printf("Level : %d\n", this.getLevel());
        System.out.printf("Experience : %d\n", this.getExperience());
        System.out.printf("Cumulative Experience : %d\n", this.getCumulativeExperience());
    }

    public void addExp(int exp) {
        this.experience += exp;
        this.cumulativeExperience += exp;
        if (this.experience >= 100) {
            this.experience -= 100;
            this.level += 1;
        }
    }

    public Skill getHighestMastery(){
        int highest = -1;
        Skill current = null;

        for(Skill skills : this.skill){
            if(skills.getSkillMastery() > highest){
                highest = skills.getSkillMastery();
                current = skills;
            }
        }
        return current;
    }

    public int findSkill(String name){
        for(Skill skills : this.skill){
            if(skills.getSkillName().equals(name)){
                return this.skill.indexOf(skills);
            }
        }
        // Not Found
        return -1;
    }

    public void addSkill(Skill newSkill){
        this.skill.add(newSkill);
    }

    public void removeSkill(String skillName){
        int idxOfRemoved = this.findSkill(skillName);

        if(idxOfRemoved != -1){
            this.skill.remove(idxOfRemoved);
        }
    }

    public Boolean isSkillSizeValid(){
        return this.skill.size() <= 4;
    }

    public Boolean containsSkill(String skillName){
        return this.findSkill(skillName) != -1;
    }
}