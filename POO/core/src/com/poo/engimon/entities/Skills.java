package com.poo.engimon.entities;

import java.util.ArrayList;
import java.util.Arrays;

public class Skills {
    public static ArrayList<Skill> fireSkills(){
        ArrayList<Skill> fires = new ArrayList<>();
        UniqueSkill Fireball = new UniqueSkill("Fireball", 20, 1, new ArrayList<String>(Arrays.asList("FIRE")), "Firemon");
        Skill CrimsonFire = new Skill("CrimsonFire", 10, 1, new ArrayList<String>(Arrays.asList("FIRE")));
        Skill Flamethrower = new Skill("FlameThrower", 12, 1, new ArrayList<String>(Arrays.asList("FIRE", "ELECTRIC")));
        Skill HeatWave = new Skill("HeatWave", 14, 1, new ArrayList<String>(Arrays.asList("FIRE")));
        Skill ScorchingSands = new Skill("ScorchingSands", 12, 1, new ArrayList<String>(Arrays.asList("FIRE", "GROUND")));
        SpecialSkill Hellfire = new SpecialSkill("Hellfire", 65, 1, new ArrayList<String>(Arrays.asList("FIRE")), 10);
        SpecialSkill ChasmTrample = new SpecialSkill("ChasmTrample", 70, 1, new ArrayList<String>(Arrays.asList("FIRE")), 10);
        fires.add(Fireball);
        fires.add(CrimsonFire);
        fires.add(Flamethrower);
        fires.add(HeatWave);
        fires.add(ScorchingSands);
        fires.add(Hellfire);
        fires.add(ChasmTrample);

        return fires;
    }

    public static ArrayList<Skill> waterSkills(){
        ArrayList<Skill> waters = new ArrayList<>();
        UniqueSkill Watercut = new UniqueSkill("Watercut", 20, 1, new ArrayList<String>(Arrays.asList("WATER")), "Watermon");
        Skill LittleWanderers = new Skill("LittleWanderers", 10, 1, new ArrayList<String>(Arrays.asList("WATER")));
        Skill DancingRain = new Skill("DancingRain", 12, 1, new ArrayList<String>(Arrays.asList("WATER", "ICE")));
        Skill Tsunami = new Skill("Tsunami", 14, 1, new ArrayList<String>(Arrays.asList("WATER")));
        Skill SubzeroSlammer = new Skill("SubzeroSlammer", 10, 1, new ArrayList<String>(Arrays.asList("WATER", "ICE")));
        SpecialSkill BubbleBeam = new SpecialSkill("BubbleBeam", 60, 1, new ArrayList<String>(Arrays.asList("WATER")), 10);
        SpecialSkill PoseidonWrath = new SpecialSkill("Poseidon's Wrath", 75, 1, new ArrayList<String>(Arrays.asList("WATER")), 10);

        waters.add(Watercut);
        waters.add(LittleWanderers);
        waters.add(DancingRain);
        waters.add(Tsunami);
        waters.add(SubzeroSlammer);
        waters.add(BubbleBeam);
        waters.add(PoseidonWrath);

        return waters;
    }

    public static ArrayList<Skill> electricSkills(){
        ArrayList<Skill> electric = new ArrayList<>();
        UniqueSkill Thunderslash = new UniqueSkill("Thunderslash", 20, 1, new ArrayList<String>(Arrays.asList("ELECTRIC")), "Electromon");
        Skill PlasmaFist = new Skill("PlasmaFist", 10, 1, new ArrayList<String>(Arrays.asList("ELECTRIC")));
        Skill PikaPapow = new Skill("PikaPapow", 12, 1, new ArrayList<String>(Arrays.asList("ELECTRIC")));
        Skill Flamethrower = new Skill("FlameThrower", 12, 1, new ArrayList<String>(Arrays.asList("ELECTRIC", "FIRE")));
        Skill StokedSparksurfer = new Skill("StokedSparksurfer", 14, 1, new ArrayList<String>(Arrays.asList("ELECTRIC", "FIRE")));
        SpecialSkill BOLT = new SpecialSkill("10,000,000VoltThunderbolt", 75, 1, new ArrayList<String>(Arrays.asList("ELECTRIC")), 10);
        SpecialSkill ForkedLightning = new SpecialSkill("ForkedLightning", 60, 1, new ArrayList<String>(Arrays.asList("ELECTRIC")), 10);

        electric.add(Thunderslash);
        electric.add(PlasmaFist);
        electric.add(PikaPapow);
        electric.add(Flamethrower);
        electric.add(StokedSparksurfer);
        electric.add(BOLT);
        electric.add(ForkedLightning);

        return electric;
    }

    public static ArrayList<Skill> iceSkills() {
        ArrayList<Skill> ice = new ArrayList<>();

        ice.add(new UniqueSkill("Blizzard", 20, 1, new ArrayList<String>(Arrays.asList("ICE")), "Icemon"));
        ice.add(new Skill("SubzeroSlammer", 10, 1, new ArrayList<String>(Arrays.asList("ICE", "WATER"))));
        ice.add(new Skill("IceSpearhead", 12, 1, new ArrayList<String>(Arrays.asList("ICE"))));
        ice.add(new Skill("SubzeroSlammer", 10, 1, new ArrayList<String>(Arrays.asList("ICE", "WATER"))));
        ice.add(new Skill("Avalanche", 14, 1, new ArrayList<String>(Arrays.asList("ICE"))));
        ice.add(new Skill("DancingRain", 12, 1, new ArrayList<String>(Arrays.asList("ICE", "WATER"))));
        ice.add(new SpecialSkill("CastleofIce", 55, 1, new ArrayList<String>(Arrays.asList("ICE")), 10));
        ice.add(new SpecialSkill("Avalon", 80, 1, new ArrayList<String>(Arrays.asList("ICE")), 10));

        return ice;
    }

    public static ArrayList<Skill> groundSkills() {
        ArrayList<Skill> ground = new ArrayList<>();

        UniqueSkill Earthquake = new UniqueSkill("Earthquake", 20, 1, new ArrayList<String>(Arrays.asList("GROUND")), "Groundmon");
        Skill SpikesOfDeath = new Skill("SpikesofDeath", 10, 1, new ArrayList<String>(Arrays.asList("GROUND")));
        Skill ScorchingSands = new Skill("ScorchingSands", 12, 1, new ArrayList<String>(Arrays.asList("GROUND", "FIRE")));
        Skill TempestOfBlades = new Skill("TempestofBlades", 14, 1, new ArrayList<String>(Arrays.asList("GROUND")));
        Skill StokedSparksurfer = new Skill("StokedSparksurfer", 14, 1, new ArrayList<String>(Arrays.asList("GROUND", "ELECTRIC")));
        SpecialSkill Void = new SpecialSkill("Void", 70, 1, new ArrayList<String>(Arrays.asList("GROUND")), 10);
        SpecialSkill ThornedRose = new SpecialSkill("ThornedRose", 75, 1, new ArrayList<String>(Arrays.asList("GROUND")), 10);

        ground.add(Earthquake);
        ground.add(SpikesOfDeath);
        ground.add(ScorchingSands);
        ground.add(TempestOfBlades);
        ground.add(StokedSparksurfer);
        ground.add(Void);
        ground.add(ThornedRose);

        return ground;
    }

    public static ArrayList<SkillItem> createSkillItem(ArrayList<Skill> skills){
        ArrayList<SkillItem> skillItems = new ArrayList<>();
        for(Skill skill: skills){
            skillItems.add(new SkillItem(skill, 1));
        }

        return skillItems;
    }

    public static void main(String[] args) {
        ArrayList<Skill> fireSkills = Skills.fireSkills();
        ArrayList<Skill> waterSkills = Skills.waterSkills();
        ArrayList<Skill> electricSkills = Skills.electricSkills();
        ArrayList<Skill> iceSkills = Skills.iceSkills();
        ArrayList<Skill> groundSkills = Skills.groundSkills();

        ArrayList<SkillItem> fireItems = createSkillItem(fireSkills);
        ArrayList<SkillItem> waterItems = createSkillItem(waterSkills);
        ArrayList<SkillItem> electricItems = createSkillItem(electricSkills);
        ArrayList<SkillItem> iceItems = createSkillItem(iceSkills);
        ArrayList<SkillItem> groundItems = createSkillItem(groundSkills);

        for(SkillItem items: fireItems){
            items.skillItemInfo();
        }
    }
}