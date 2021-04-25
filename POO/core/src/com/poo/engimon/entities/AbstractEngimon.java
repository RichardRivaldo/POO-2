package com.poo.engimon.entities;

import java.util.ArrayList;

public abstract class AbstractEngimon {
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
