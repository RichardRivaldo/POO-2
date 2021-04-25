package com.poo.engimon.battle;

import com.poo.engimon.entities.Engimon;
import com.poo.engimon.entities.Player;
import com.poo.engimon.entities.Skill;
import com.poo.engimon.entities.SkillItem;

import java.util.Scanner;

import static java.lang.Math.floor;

class Battle {
    private final Player player;
    private final Engimon engimonPlayer;
    private final Engimon engimonWild;
    private double engiPlayerPower;
    private double engiWildPower;
    private double playerMult;
    private double wildMult;
    private double playerSkillPower;
    private double wildSkillPower;

    public Battle(Player player, Engimon engimonWild) {
        this.player = player;
        this.engimonPlayer = player.getActiveEngimon();
        this.engimonWild = engimonWild;

        if (this.engimonPlayer != null) {
            this.setMultiplier();
            this.setSkillPower();
            this.setPower();

            this.doBattle();
        }
        else {
            System.out.println("Player tidak bisa melakukan battle");
            System.out.println("Player membutuhkan active engimon untuk melakukan battle");
        }
        
    }

    public double checkMultiplier(String elemen1, String elemen2) {
        switch (elemen1) {
            case "Fire":
                switch (elemen2) {
                    case "Fire":
                    case "Electric":
                        return 1;
                    case "Water":
                        return 0;
                    case "Ground":
                        return 0.5;
                    case "Ice":
                        return 2;
                }
                break;
            case "Water":
                switch (elemen2) {
                    case "Fire":
                        return 2;
                    case "Water":
                    case "Ground":
                    case "Ice":
                        return 1;
                    case "Electric":
                        return 0;
                }
                break;
            case "Electric":
                switch (elemen2) {
                    case "Fire":
                    case "Electric":
                        return 1;
                    case "Water":
                        return 2;
                    case "Ground":
                        return 0;
                    case "Ice":
                        return 1.5;
                }
                break;
            case "Ground":
                switch (elemen2) {
                    case "Fire":
                        return 1.5;
                    case "Water":
                    case "Ground":
                        return 1;
                    case "Electric":
                        return 2;
                    case "Ice":
                        return 0;
                }
                break;
            case "Ice":
                switch (elemen2) {
                    case "Fire":
                        return 0;
                    case "Water":
                    case "Ice":
                        return 1;
                    case "Electric":
                        return 0.5;
                    case "Ground":
                        return 2;
                }
                break;
        }
        return 0;
    }

    public void setMultiplier() {
        double max1 = 0;
        double max2 = 0;

        for (String elemen1 : this.engimonPlayer.getElement()) {
            for (String elemen2 : this.engimonWild.getElement()) {
                if (this.checkMultiplier(elemen1, elemen2) > max1) {
                    max1 = this.checkMultiplier(elemen1, elemen2);
                }
                if (this.checkMultiplier(elemen2, elemen1) > max2) {
                    max2 = this.checkMultiplier(elemen2, elemen1);
                }
            }
        }
        this.playerMult = max1;
        this.wildMult = max2;
    }

    public void setSkillPower() {
        double sum1 = 0;
        double sum2 = 0;

        for (Skill skill : this.engimonPlayer.getSkill()) {
            sum1 += skill.getSkillPower() * skill.getSkillMastery();
        }

        for (Skill skill : this.engimonWild.getSkill()) {
            sum2 += skill.getSkillPower() * skill.getSkillMastery();
        }

        this.playerSkillPower = sum1;
        this.wildSkillPower = sum2;
    }

    public void setPower() {
        this.engiPlayerPower = this.engimonPlayer.getLevel() * this.playerMult + this.playerSkillPower;
        this.engiWildPower = this.engimonWild.getLevel() * this.wildMult + this.wildSkillPower;
    }

    public void showEngimonPower() {
        System.out.println("\nENGIMON PLAYER");
        System.out.println("Nama                  : " + this.engimonPlayer.getName());
        System.out.println("Spesies               : " + this.engimonPlayer.getSpecies());
        System.out.println("Level                 : " + this.engimonPlayer.getLevel());
        System.out.println("Experience            : " + this.engimonPlayer.getExperience());
        System.out.println("Cumulative Experience : " + this.engimonPlayer.getCumulativeExperience());
        System.out.println("Power Level           : " + this.engiPlayerPower);
        System.out.println("\nENGIMON WILD");
        System.out.println("Nama                  : " + this.engimonWild.getName());
        System.out.println("Spesies               : " + this.engimonWild.getSpecies());
        System.out.println("Level                 : " + this.engimonWild.getLevel());
        System.out.println("Experience            : " + this.engimonWild.getExperience());
        System.out.println("Cumulative Experience : " + this.engimonWild.getCumulativeExperience());
        System.out.println("Power Level           : " + this.engiWildPower);
    }

    public void doBattle() {
        this.showEngimonPower();

        System.out.println("\nProceed to the battle? [Proceed / Cancel]");
        Scanner input = new Scanner(System.in);
        String answer;
        answer = input.nextLine();
        while ((!answer.equalsIgnoreCase("Proceed")) && (!answer.equalsIgnoreCase("Cancel"))) {
            answer = input.nextLine();
        }

        if (answer.equalsIgnoreCase("Proceed")) {
            System.out.println("Battle antara " + this.engimonPlayer.getName() + " dan " + this.engimonWild.getName() + " dimulai\n");
            this.startBattle();
        }
        
        else {
            System.out.println("Battle antara " + this.engimonPlayer.getName() + " dan " + this.engimonWild.getName() + " dibatalkan\n");
        }
    }

    public void startBattle() {
        if (this.engiPlayerPower >= this.engiWildPower) {
            System.out.println(this.engimonPlayer.getName() + " memenangkan battle");

            this.player.getEngimonInvent().add(this.engimonWild);
            System.out.println("Selamat Anda berhasil mendapatkan " + this.engimonWild.getName());

            int exp = (int) floor((this.engimonWild.getLevel()*100)/this.engimonPlayer.getLevel());
            this.engimonPlayer.addExp(exp);
            System.out.println(this.engimonPlayer.getName() + " mendapatkan " + exp + " xp");

            SkillItem item = new SkillItem(this.engimonWild.getSkill().get(0), 1);
            this.player.getSkillInvent().add(item);
            System.out.println("Anda mendapatkan skill item " + item.toString());
        }

        else {
            System.out.println(this.engimonPlayer.getName() + " kalah");
            System.out.println(this.engimonWild.getName() + " kabur");
            this.engimonPlayer.decreaseLife();
        }

    }
}