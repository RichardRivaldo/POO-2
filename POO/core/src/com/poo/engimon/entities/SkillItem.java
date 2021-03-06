package com.poo.engimon.entities;

import java.util.*;

public class SkillItem {
    private Pair<Skill, Integer> item;

    // Default Constructor
    public SkillItem() {
        item = new Pair<>(new Skill(), 0);
    }

    // User-Defined Constructor
    public SkillItem(Skill skill, Integer amount) {
        item = new Pair<>(skill, amount);
    }

    // Getters
    public Skill getSkill() {
        return this.item.getKey();
    }

    public Integer getAmount() {
        return this.item.getValue();
    }

    // Methods

    // Add Skill item counts
    public void addAmount(Integer add) {
        Integer currentAmount = this.getAmount();
        this.item = new Pair<>(this.getSkill(), currentAmount + add);
    }

    // Decrease skill item amounts
    public void decAmount(Integer dec) {
        Integer currentAmount = this.getAmount();
        this.item = new Pair<>(this.getSkill(), currentAmount - dec);
    }

    // Get Skill Info as a string
    public String getSkillInfo() {
        return this.getSkill().toString();
    }

    // String for Skill Item info
    public String toString() {
        String skillInfo = this.getSkillInfo();
        return String.format("%sItem Amount         : %d%n", skillInfo, this.getAmount());
    }

    // Print the Skill Item info
    public String skillItemInfo(){
        return this.toString();
    }

    // Testing
    public static void main(String[] args) {
        ArrayList<String> elements = Skill.randomElements();

        SkillItem item = new SkillItem();
        Skill basic = new Skill("Basic", 2, 1, elements);
        UniqueSkill unique = new UniqueSkill("Unique", 2, 1, elements, "Firemon");
        SpecialSkill special = new SpecialSkill("Special", 2, 1, elements, 100);

        SkillItem basicItem = new SkillItem(basic, 1);
        SkillItem uniqueItem = new SkillItem(unique, 2);
        SkillItem specialItem = new SkillItem(special, 3);

        item.skillItemInfo();

        basicItem.skillItemInfo();
        uniqueItem.skillItemInfo();
        specialItem.skillItemInfo();

        basicItem.addAmount(2);
        uniqueItem.addAmount(1);
        specialItem.decAmount(1);

        basicItem.skillItemInfo();
        uniqueItem.skillItemInfo();
        specialItem.skillItemInfo();
    }
}