package com.poo.engimon.entities;

import static java.lang.Math.floor;

public class Battle {
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
        this.engimonPlayer = this.player.getActiveEngimon();
        this.engimonWild = engimonWild;
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

    public String showEngimonPower() {
        StringBuilder sb = new StringBuilder();

        sb.append("\nENGIMON PLAYER");
        sb.append("\nNama                  : ").append(this.engimonPlayer.getName());
        sb.append("\nSpesies               : ").append(this.engimonPlayer.getSpecies());
        sb.append("\nLevel                 : ").append(this.engimonPlayer.getLevel());
        sb.append("\nExperience            : ").append(this.engimonPlayer.getExperience());
        sb.append("\nCumulative Experience : ").append(this.engimonPlayer.getCumulativeExperience());
        sb.append("\nPower Level           : ").append(this.engiPlayerPower);
        sb.append("\n");
        sb.append("\nENGIMON WILD");
        sb.append("\nNama                  : ").append(this.engimonWild.getName());
        sb.append("\nSpesies               : ").append(this.engimonWild.getSpecies());
        sb.append("\nLevel                 : ").append(this.engimonWild.getLevel());
        sb.append("\nExperience            : ").append(this.engimonWild.getExperience());
        sb.append("\nCumulative Experience : ").append(this.engimonWild.getCumulativeExperience());
        sb.append("\nPower Level           : ").append(this.engiWildPower);
        sb.append("\n");

        return sb.toString();
    }

    public String doBattle() {
        StringBuilder sb = new StringBuilder();

        if (this.engimonPlayer != null) {
            this.setMultiplier();
            this.setSkillPower();
            this.setPower();

            sb.append(this.showEngimonPower());
            sb.append("\nProceed to the battle? [Yes/No]");
        }
        else {
            sb.append("Player tidak bisa melakukan battle\n");
            sb.append("Player membutuhkan active engimon untuk melakukan battle");
        }

        return sb.toString();
    }

    public String cancelBattle(){
        StringBuilder sb = new StringBuilder();
        sb.append("Battle antara ").append(this.engimonPlayer.getName()).append(" dan ").append(this.engimonWild.getName()).append(" dibatalkan\n");

        return sb.toString();
    }

    public String startBattle() {
        StringBuilder sb = new StringBuilder();

        sb.append("Battle antara ").append(this.engimonPlayer.getName()).append(" dan ").append(this.engimonWild.getName()).append(" dimulai\n");

        if (this.engiPlayerPower >= this.engiWildPower) {
            sb.append(this.engimonPlayer.getName()).append(" memenangkan battle\n");

            Engimon newEngimon = new Engimon (this.engimonWild.getName(), this.engimonWild.getSpecies(), this.engimonWild.getElement());
            this.player.addEngimon(newEngimon);
            this.engimonWild.decreaseLife();
            this.player.getEngimonInvent().getItemList().get(this.player.getEngimonInvent().size()-1).setLife(3);
            sb.append("Selamat Anda berhasil mendapatkan ").append(this.engimonWild.getName());

            int exp = this.engimonWild.getLevel()*100/this.engimonPlayer.getLevel();
            this.engimonPlayer.addExp(exp);
            sb.append(this.engimonPlayer.getName()).append(" mendapatkan ").append(exp).append(" xp\n");

            SkillItem item = new SkillItem(this.engimonWild.getSkill().get(0), 1);
            this.player.addSkillItem(item);
            sb.append("Anda mendapatkan skill item \n").append(item.toString());

        }

        else {
            sb.append(this.engimonPlayer.getName()).append(" kalah\n");
            sb.append(this.engimonWild.getName()).append(" kabur");
            this.engimonPlayer.decreaseLife();
        }
        return sb.toString();
    }
}