package com.poo.engimon.entities;

import java.util.*;

public class Breed implements Breeding {
    Engimon parent1;
    Engimon parent2;
    Engimon anakEngimon;

    public Breed(){
        this.parent1 = null;
        this.parent2 = null;
        this.anakEngimon = null;
    }

    public static boolean containsSkill(ArrayList<Skill> listSkill, String skill) {
        for (Skill elmt : listSkill) {
            if (elmt.getSkillName().equals(skill)) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkElement(Engimon engi1, Engimon engi2){
        if(engi1.getElement().size() != engi2.getElement().size()){
            return false;
        }
        else{
            Boolean cocok = true;
            for(String elmt: engi1.getElement()){
                cocok = cocok && engi2.getElement().contains(elmt);
            }
            return cocok;
        }
    }

    public Engimon breed(Engimon thisEngimon, Engimon anotherEngimon, String nama) {
        Engimon engimonAnak = new Engimon();
        if (!this.checkElement(thisEngimon, anotherEngimon)) {
            return engimonAnak;
        } else if (thisEngimon.getLevel() < 3 || anotherEngimon.getLevel() < 3) {
            return engimonAnak;
        } else {
            Engimon engimon1tmp = thisEngimon;
            Engimon engimon2tmp = anotherEngimon;

            Engimon tmp = new Engimon(nama, thisEngimon.getSpecies(), thisEngimon.getElement());

            thisEngimon.setLevel(thisEngimon.getLevel() - 3);
            anotherEngimon.setLevel(anotherEngimon.getLevel() - 3);

            ArrayList<String> parents = new ArrayList<String>();
            parents.add(thisEngimon.getName());
            parents.add(anotherEngimon.getName());

            engimonAnak = tmp;

            engimonAnak.setParents(parents);

            for (int i = 0; i < thisEngimon.getSkill().size(); i++) {
                Skill highest1 = engimon1tmp.getHighestMastery();
                Skill highest2 = engimon2tmp.getHighestMastery();

                if (highest1.getSkillMastery() > highest2.getSkillMastery()) {
                    if (containsSkill(engimon2tmp.getSkill(), highest1.getSkillName())) {
                        engimonAnak.addSkill(highest1);
                        engimon1tmp.removeSkill(highest1.getSkillName());
                        engimon2tmp.removeSkill(highest2.getSkillName());
                    } else {
                        engimonAnak.addSkill(highest1);
                        engimon2tmp.removeSkill(highest1.getSkillName());
                    }
                } else if (highest1.getSkillMastery() < highest2.getSkillMastery()) {
                    if (containsSkill(engimon1tmp.getSkill(), highest2.getSkillName())) {
                        engimonAnak.addSkill(highest2);
                        engimon1tmp.removeSkill(highest1.getSkillName());
                        engimon2tmp.removeSkill(highest2.getSkillName());
                    } else {
                        engimonAnak.addSkill(highest2);
                        engimon2tmp.removeSkill(highest2.getSkillName());
                    }
                } else {
                    if (highest1.getSkillName() == highest2.getSkillName()) {
                        Skill tmpSkill = highest1;
                        if (tmpSkill.getSkillMastery() < 3) {
                            tmpSkill.masteryLevelUp(highest1.getSkillPower());
                        }
                        engimonAnak.addSkill(tmpSkill);
                        engimon1tmp.removeSkill(highest1.getSkillName());
                        engimon2tmp.removeSkill(highest2.getSkillName());
                    } else {
                        engimonAnak.addSkill(highest1);
                        engimon1tmp.removeSkill(highest2.getSkillName());
                    }
                }
            }
        }

        return engimonAnak;
    }
}