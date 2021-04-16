import java.util.*;

public class SpecialSkill extends Skill {
    private Integer additionalPower;

    // Default constructor
    public SpecialSkill() {
        super();
        this.additionalPower = 0;
    }

    // User-Defined Constructor
    public SpecialSkill(String name, Integer bp, Integer mastery, ArrayList<String> elmts, Integer addPower) {
        super(name, bp, mastery, elmts);
        this.additionalPower = addPower;
    }

    // Getters and Setters
    public Integer getAdditionalPower() {
        return this.additionalPower;
    }

    public void setAdditionalPower(Integer addPower) {
        this.additionalPower = addPower;
    }

    // Methods
    public void addAdditionalPower() {
        Random rand = new Random();
        this.additionalPower += rand.nextInt(additionalPower);
    }

    public void masteryLevelUp(Integer bpIncrease) {
        super.masteryLevelUp(bpIncrease);
        this.addAdditionalPower();
    }

    public String toString() {
        String skillInfo = super.skillInfo();
        return String.format("%s%nPOWERSSS : %d%n", skillInfo, this.additionalPower);
    }

    public void skillInfo() {
        System.out.println(this);
    }
}