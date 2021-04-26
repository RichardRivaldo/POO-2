import java.util.*;

public class Skill {
    protected String skillName;
    protected Integer basePower;
    protected Integer masteryLevel;
    protected ArrayList<String> compatibleElmts = new ArrayList<String>();

    // Default constructor
    public Skill() {
        skillName = "sKILL";
        basePower = 0;
        masteryLevel = 1;
    }

    // User-Defined Constructor
    public Skill(String name, Integer bp, Integer mastery, ArrayList<String> elmts) {
        this.skillName = name;
        this.basePower = bp;
        this.masteryLevel = mastery;
        this.compatibleElmts = elmts;
    }

    // Getters
    public String getSkillName() {return this.skillName;}
    public Integer getSkillPower() {return this.basePower;}
    public Integer getSkillMastery() {return this.masteryLevel;}
    public ArrayList<String> getCompatibleElmts() {return this.compatibleElmts;}

    // Setters
    public void setSkillName(String name) {this.skillName = name;}
    public void setSkillPower(Integer basePower) {this.basePower = basePower;}
    public void setSkillMastery(Integer mastery) {this.masteryLevel = mastery;}
    public void setCompatibleElmts(ArrayList<String> elmts) {this.compatibleElmts = elmts;}

    // Methods

    // Check if skill is learnable
    public Boolean isSkillLearnable(ArrayList<String> engimonElmt) {
        for(String element: engimonElmt){
            if(this.compatibleElmts.contains(element)){
                return true;
            }
        }
        return false;
    }

    // Raise the mastery level
    public void masteryLevelUp(Integer lvIncrease) {
        if (this.masteryLevel + lvIncrease <= 3) {
            this.masteryLevel += lvIncrease;
        } else {
            this.masteryLevel = 3;
        }
    }

    // Generate an array of random elements
    public static ArrayList<String> randomElements() {
        ArrayList<String> elementsList = new ArrayList<>(Arrays.asList("FIRE", "WATER", "EARTH", "WIND"));
        ArrayList<String> returned = new ArrayList<>();
        Random rand = new Random();
        Integer numOfElements = rand.nextInt(5);
        while (numOfElements == 0) {
            numOfElements = rand.nextInt(5);
        }

        Collections.shuffle(elementsList);
        for (int i = 0; i < numOfElements; i++) {
            returned.add(elementsList.get(i));
        }
        return returned;
    }

    // For prints
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.compatibleElmts.isEmpty()) {
            sb.append("-----");
        } 
        else {
            for (String elmts : this.compatibleElmts) {
                sb.append("|");
                sb.append(elmts);
            }
            sb.append("|");
        }
        return String.format(
                "Skill Name          : %s%nBase Power          : %d%nMastery Level       : %d%nCompatible Elements : %s%n",
                this.skillName, this.basePower, this.masteryLevel, sb.toString());
    }

    // Show skill info
    public void skillInfo() {System.out.println(this);}

    // Testing
    public static void main(String[] args) {
        ArrayList<String> elements = Skill.randomElements();

        Skill defSkill = new Skill();
        Skill mySkill = new Skill("My Skill", 2, 1, elements);

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