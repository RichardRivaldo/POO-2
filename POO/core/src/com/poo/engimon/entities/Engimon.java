package com.poo.engimon.entities;

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

    abstract public String showStats();

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

    public void setCumulativeExperience(int cumulativeExperience){ this.cumulativeExperience = cumulativeExperience;}

    public ArrayList<Skill> getSkill() {
        return this.skill;
    }

    public String getMessage() {
        return this.message;
    }

    // Setters
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

    public String showAllSkills() {
        StringBuilder sb = new StringBuilder();
        if (!this.skill.isEmpty()) {
            for (Skill skills : this.skill) {
                sb.append(skills.skillInfo());
            }
        } else {
            sb.append("Engimon ini belum memiliki Skill!\n");
        }
        return sb.toString();
    }

    public void decreaseLife() { this.life -= 1; }

    public void addLife() {
        this.life += 1;
    }

    public String showStats() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Nama : %s\n", this.getName()));
        sb.append("Life : " + this.getLife() + "\n");
        sb.append(String.format("Spesies : %s\n", this.getSpecies()));
        sb.append("Parents : ");

        for (String parent : this.parents) {
            sb.append(String.format("%s  ", parent));
        }

        // Show Skills
        sb.append("\nElement : ");

        for (String element : this.getElement()) {
            sb.append(String.format("%s  ", element));
        }

        sb.append(String.format("\nLevel : %d\n", this.getLevel()));
        sb.append(String.format("Experience : %d\n", this.getExperience()));
        sb.append(String.format("Cumulative Experience : %d\n", this.getCumulativeExperience()));
        sb.append("Daftar Skill yang dimiliki :\n");
        sb.append(showAllSkills());

        return sb.toString();
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
                if (skills.getSkillName().equalsIgnoreCase(name)) {
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

            if (choice.equalsIgnoreCase("y")) {
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