import java.util.*;

public class UniqueSkill extends Skill {
    private String uniqueSpecies;

    // Default constructor
    public UniqueSkill() {
        super();
        this.uniqueSpecies = "Engimon";
    }

    // User-Defined Constructor
    public UniqueSkill(String name, Integer bp, Integer mastery, ArrayList<String> elmts, String species) {
        super(name, bp, mastery, elmts);
        this.uniqueSpecies = species;
    }

    // Getters and Setters
    public String getUniqueSpecies() {return this.uniqueSpecies;}
    public void setUniqueSpecies(String species) {this.uniqueSpecies = species;}

    // Methods

    // Learnable or not
    public Boolean isSkillLearnable(ArrayList<String> engimonElmt, String species) {
        return super.isSkillLearnable(engimonElmt) && species.equals(this.uniqueSpecies);
    }

    // String for Skill Info
    public String toString() {
        String skillInfo = super.toString();
        return String.format("%sSkill Grade         : Unique Skill%nUnique Species      : %s%n", 
        skillInfo, this.uniqueSpecies);
    }

    // Show skill info
    public void skillInfo() {System.out.println(this);}

    // Testing
    public static void main(String[] args) {
        ArrayList<String> elements = UniqueSkill.randomElements();

        Skill defSkill = new UniqueSkill();
        Skill mySkill = new UniqueSkill("My Skill", 2, 1, elements, "Firemon");

        defSkill.skillInfo();
        mySkill.skillInfo();

        if (!defSkill.isSkillLearnable(new ArrayList<String>(Arrays.asList("FIRE")))) {
            System.out.println("FALSE");
        }
        if (mySkill.isSkillLearnable(new ArrayList<String>(Arrays.asList("WATER")))) {
            System.out.println("TRUE");
        }

        mySkill.masteryLevelUp(100);
        mySkill.skillInfo();
    }
}