package com.poo.engimon.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
    public Integer getAdditionalPower() {return this.additionalPower;}
    public void setAdditionalPower(Integer addPower) {this.additionalPower = addPower;}

    // Methods

    // Randomly add additional powers
    public void addAdditionalPower() {
        Random rand = new Random();
        
        Integer addPower;
        if(this.getAdditionalPower() == 0){
            addPower = rand.nextInt(100);
        }
        else{
            Integer current = this.getAdditionalPower();
            addPower = rand.nextInt(current);
        }
        setAdditionalPower(this.getAdditionalPower() + addPower);
    }

    // Raise the mastery level
    public void masteryLevelUp(Integer lvIncrease) {
        Integer currentLevel = this.getSkillMastery();
        if (this.getSkillMastery() + lvIncrease < 3) {
            this.setSkillMastery(this.getSkillMastery() + lvIncrease);
        } else {
            this.setSkillMastery(3);
        }
        Integer bound = this.getSkillMastery() - currentLevel;

        for(int i = 0; i < bound; i++){
            this.addAdditionalPower();
        }
    }

    // String of skill info
    public String toString() {
        String skillInfo = super.toString();
        return String.format("%sSkill Grade         : Special Skill%nAdditional Powers   : %d%n", 
        skillInfo, this.getAdditionalPower());
    }

    // Show the skill info
    public void skillInfo() {System.out.println(this);}

    // Testing
    public static void main(String[] args) {
        ArrayList<String> elements = SpecialSkill.randomElements();

        Skill defSkill = new SpecialSkill();
        Skill mySkill = new SpecialSkill("My Skill", 2, 1, elements, 100);

        defSkill.skillInfo();
        mySkill.skillInfo();

        if (!defSkill.isSkillLearnable(new ArrayList<String>(Arrays.asList("FIRE")))) {
            System.out.println("FALSE");
        }
        if (mySkill.isSkillLearnable(new ArrayList<String>(Arrays.asList("WATER")))) {
            System.out.println("TRUE");
        }

        defSkill.masteryLevelUp(100);
        defSkill.skillInfo();
        mySkill.masteryLevelUp(1);
        mySkill.skillInfo();
    }
}