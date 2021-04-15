public class UniqueSkill extends Skill{
    private String uniqueSpecies;

    // Default constructor
    public UniqueSkill(){
        super();
        this.uniqueSpecies = "Engimon";
    }
    
    // User-Defined Constructor
    public UniqueSkill(String name, Integer bp, Integer mastery, ArrayList<String> elmts, String species){
        super(name, bp, mastery, elmts);
        this.uniqueSpecies = species;
    }

    // Getters and Setters
    public String getUniqueSpecies(){return this.uniqueSpecies;}
    public void setUniqueSpecies(String species){this.uniqueSpecies = species;}

    // Methods
    public Boolean isSkillLearnable(String engimonElmt, String species){
        return super.isSkillLearnable(engimonElmt) && species == this.uniqueSpecies;
    }

    public String toString(){
        String skillInfo = super.skillInfo();
        return String.format("%s%nUnique Species : %s%n", skillInfo, this.uniqueSpecies);
    }

    public void skillInfo(){
        System.out.println(this);
    }
}