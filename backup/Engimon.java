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
    private int life;

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
        this.life = 0;
    }

    public Engimon(String nama, String species, ArrayList<String> element) {
        this.name = nama;
        this.element = new ArrayList<String>();
        if (element.size() > 2) {
            this.element.add(element.get(0));
            this.element.add(element.get(1));
        } else {
            this.element = element;
        }
        this.species = species;
        this.level = 1;
        this.experience = 1;
        this.cumulativeExperience = 0;
        this.message = "I'm an Engimon";
        this.parents = new ArrayList<String>();
        this.skill = new ArrayList<Skill>();
        this.life = 3;
    }

    // Getters
    public int getLife() {
        return this.life;
    }

    public ArrayList<String> getParents() {
        return this.parents;
    }

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
    public void setCumulativeExperience(int cumulative) {
        this.cumulativeExperience = cumulative;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    public void setParents(ArrayList<String> parents) {
        this.parents = parents;
    }

    public void setSkills(ArrayList<Skill> list) {
        this.skill = list;
    }

    public void setExp(int exp) {
        this.experience = exp;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void showAllSkills() {
        if (!this.skill.isEmpty()) {
            for (Skill skills : this.skill) {
                skills.skillInfo();
            }
        } else {
            System.out.println("Engimon ini belum memiliki Skill!\n");
        }
    }

    public void decreaseLife() {
        this.life -= 1;
    }

    public void addLife() {
        this.life += 1;
    }

    public void showStats() {
        System.out.printf("Nama : %s\n", this.getName());
        System.out.printf("Spesies : %s\n", this.getSpecies());
        System.out.print("Parents : ");

        for (String parent : this.parents) {
            System.out.printf("%s  ", parent);
        }

        // Show Skills
        System.out.print("\nElement : ");

        for (String element : this.getElement()) {
            System.out.printf("%s  ", element);
        }

        System.out.printf("\nLevel : %d\n", this.getLevel());
        System.out.printf("Experience : %d\n", this.getExperience());
        System.out.printf("Cumulative Experience : %d\n", this.getCumulativeExperience());
        System.out.println("Daftar Skill yang dimiliki :");
        showAllSkills();
    }

    public void addExp(int exp) {
        this.experience += exp;
        this.cumulativeExperience += exp;
        if (this.experience >= 100) {
            this.experience -= 100;
            this.level += 1;
        }
    }

    public Skill getHighestMastery() {
        int highest = -1;
        Skill current = null;

        if (!this.skill.isEmpty()) {
            for (Skill skills : this.skill) {
                if (skills.getSkillMastery() > highest) {
                    highest = skills.getSkillMastery();
                    current = skills;
                }
            }
        }
        return current;
    }

    public int findSkill(String name) {
        if (!this.skill.isEmpty()) {
            for (Skill skills : this.skill) {
                if (skills.getSkillName().equals(name)) {
                    return this.skill.indexOf(skills);
                }
            }
        }
        // Not Found
        return -1;
    }

    public Boolean isSkillFull() {
        return this.skill.size() == 4;
    }

    public void addSkill(Skill newSkill) {
        if (this.hasSkill(newSkill.getSkillName())) {
            System.out.println("Engimon ini sudah memiliki Skill ini!");
        } else if (this.isSkillFull()) {
            System.out.println("Skill Engimon ini sudah penuh!");
            Scanner scanner = new Scanner(System.in);
            String choice;
            System.out.print("Ingin ganti skill? (y/n) : ");
            choice = scanner.nextLine();

            if (choice.equals("y")) {
                System.out.print("Masukkan nama skill yang ingin diganti : ");
                String skillName = scanner.nextLine();
                this.removeSkill(skillName);
                if (!this.isSkillFull()) {
                    this.skill.add(newSkill);
                    System.out.println("Berhasil mempelajari Skill baru!");
                }
            }
        } else {
            this.skill.add(newSkill);
            System.out.println("Berhasil mempelajari Skill baru!");
        }
    }

    public void removeSkill(String skillName) {
        int idxOfRemoved = this.findSkill(skillName);

        if (idxOfRemoved != -1) {
            this.skill.remove(idxOfRemoved);
        } else {
            System.out.println("Engimon ini tidak memiliki skill tersebut");
        }
    }

    public Boolean isSkillSizeValid() {
        return this.skill.size() <= 4;
    }

    public Boolean hasSkill(String skillName) {
        return this.findSkill(skillName) != -1;
    }
}