import java.io.*;
import java.lang.*;
import java.util.*;

public class SortSkill implements Comparator<SkillItem> {
    public int compare(SkillItem skillItem1, SkillItem skillItem2) {
        return skillItem2.getSkill().getSkillPower() - skillItem1.getSkill().getSkillPower();
    }
}

public class SortEngimon implements Comparator<Engimon> {
    public int compare(Engimon engi1, Engimon engi2) {
        return engi2.getLevel() - engi1.getLevel();
    }
}


public class Player {
    private Engimon activeEngimon;
    private static Integer maxCapacity = 10;
    private Inventory<Engimon> engimonInvent = new Inventory<Engimon>();
    private Inventory<SkillItem> skillInvent = new Inventory<SkillItem>();

    // Constructors
    Player() {
        this.activeEngimon = null;
    }

    // Copy Constructors
    Player(Engimon active) {
        this.activeEngimon = active;
        this.engimonInvent.add(this.activeEngimon);
    }

    // Setter
    public void setActiveEngimon(Engimon active) {
        this.activeEngimon = active;
    }

    // Getters
    public Inventory<Engimon> getEngimonInvent() {
        return this.engimonInvent;
    }

    public Inventory<SkillItem> getSkillInvent() {
        return this.skillInvent;
    }

    public int getTotalItems() {
        return this.skillInvent.size() + this.engimonInvent.size();
    }

    // Methods

    // Check if there is available space for inventory
    public Boolean isStillEmpty() {
        return this.getTotalItems() + 1 <= maxCapacity;
    }

    // Add a new Engimon
    public void addEngimon(Engimon newEngimon) {
        if (!this.isStillEmpty()) {
            System.out.println("Inventory sudah penuh!");
        } else {
            this.engimonInvent.add(newEngimon);
            System.out.println("Berhasil menambahkan Engimon baru!");
        }
    }

    // Check if a Skill Item is already in Inventory
    public int isAlreadyInInvent(String skillName) {
        for (SkillItem item : this.skillInvent.getItemList()) {
            if (item.getSkill().getSkillName().equals(skillName)) {
                return this.skillInvent.getItemList().indexOf(item);
            }
        }
        return -1;
    }

    // Add a new Skill Item
    public void addSkillItem(SkillItem newSkillItem) {
        int inInvent = this.isAlreadyInInvent(newSkillItem.getSkill().getSkillName());

        if (!this.isStillEmpty()) {
            System.out.println("Inventory sudah penuh!");
        } else {
            if (inInvent != -1) {
                this.skillInvent.getItemList().get(inInvent).addAmount(newSkillItem.getAmount());
            } else {
                this.skillInvent.getItemList().add(newSkillItem);
            }
            System.out.println("Berhasil menambahkan Skill Item baru!");
        }
    }

    // Show All Engimons Info
    public void showAllOwnedEngi() {
        if (this.engimonInvent.isEmptyInvent()) {
            System.out.println("Tidak ada Engimon di Inventory");
        } else {
            System.out.println("Daftar Engimon yang dimiliki:");
            for (Engimon engi : this.engimonInvent.getItemList()) {
                engi.showStats();
            }
        }
    }

    // Show certain Engimons Info
    public void showEngiInfo(String engiName) {
        for (Engimon engi : this.engimonInvent.getItemList()) {
            if (engi.getName().equals(engiName)) {
                engi.showStats();
                return;
            }
        }
        System.out.println("Tangkap Engimon ini untuk memenuhi rasa penasaranmu!");
    }

    // Show Active Engimons Info
    public void showActiveEngi() {
        System.out.println("Engimon yang sedang aktif berpetualang:");
        this.activeEngimon.showStats();
    }

    // Interact with active engimon
    public void interactWithEngi() {
        System.out.println(this.activeEngimon.getName() + " said:");
        System.out.println(this.activeEngimon.getMessage());
    }

    // Show all Skill Items
    public void showAllOwnedItem() {
        if (this.skillInvent.isEmptyInvent()) {
            System.out.println("Tidak ada SKill Item di Inventory");
        } else {
            System.out.println("Daftar Skill Item yang dimiliki:");
            for (SkillItem item : this.skillInvent.getItemList()) {
                item.skillItemInfo();
            }
        }
    }

    // If an Engimon is already owned
    public int isAlreadyOwned(String engiName) {
        for (Engimon engi : this.engimonInvent.getItemList()) {
            if (engi.getName().equals(engiName)) {
                return this.engimonInvent.getItemList().indexOf(engi);
            }
        }
        return -1;
    }

    // Decrement
    public void decOrRemove(int index) throws IndexOutOfBoundException{
        if (this.skillInvent.getItemList().get(index).getAmount() > 1)
            this.skillInvent.getItemList().get(index).decAmount(1);
        else
            this.skillInvent.remove(index);
    }

    // Learn a skill item to active engimon
    public void learnNewSkill(String skillName, String engiName) {
        int hasItem = isAlreadyInInvent(skillName), hasEngi = isAlreadyOwned(engiName);
        Boolean wantToAdd = false;
        if (hasItem == -1) {
            System.out.println("Tidak ada Skill Item target di Inventory!");
        } else if (hasEngi == -1) {
            System.out.println("Tidak ada Engimon target di Inventory!");
        } else if(this.engimonInvent.getItemList().get(hasEngi).hasSkill(skillName)){
            System.out.println("Engimon ini sudah memiliki Skill tersebut!");
        } else {
            ArrayList<String> elements = this.engimonInvent.getItemList().get(hasEngi).getElement();
            if (this.skillInvent.getItemList().get(hasItem).getSkill() instanceof UniqueSkill) {
                UniqueSkill newSkill = (UniqueSkill) this.skillInvent.getItemList().get(hasItem).getSkill();
                if (newSkill.isSkillLearnable(elements, this.engimonInvent.getItemList().get(hasEngi).getSpecies())) {
                    this.engimonInvent.getItemList().get(hasEngi).addSkill(newSkill);
                    wantToAdd = true;
                } else {
                    System.out.println("Engimon ini tidak cocok dengan spesies Skill tersebut!");
                }
            } else {
                Skill newSkill = this.skillInvent.getItemList().get(hasItem).getSkill();
                if (newSkill.isSkillLearnable(elements)) {
                    this.engimonInvent.getItemList().get(hasEngi).addSkill(newSkill);
                    wantToAdd = true;
                } else {
                    System.out.println("Engimon ini tidak cocok dengan elemen Skill tersebut!");
                }
            }
            if(wantToAdd){
                try{
                    this.decOrRemove(hasItem);
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public HashMap<String, Inventory<Engimon>> groupEngimon() {
        HashMap<String, Inventory<Engimon>> groupEngimon = new HashMap<String, Inventory<Engimon>>();
        for(Engimon engi : this.engimonInvent.getItemList()) {
            if(engi.getElement().size() == 1) {
                if(groupEngimon.get(engi.getElement().get(0)) != null) {
                    groupEngimon.get(engi.getElement().get(0)).add(engi);
                }else{
                    Inventory<Engimon> inventEngi = new Inventory<Engimon>();
                    inventEngi.add(engi);
                    groupEngimon.put(engi.getElement().get(0), inventEngi);
                }
            }else {
                if(groupEngimon.get("More") != null) {
                    groupEngimon.get("More").add(engi);
                }else{
                    Inventory<Engimon> inventEngi = new Inventory<Engimon>();
                    inventEngi.add(engi);
                    groupEngimon.put("More", inventEngi);
                }
            }
        }
    }

    // Testing
    public static void main(String[] args) {
        Engimon active = new Engimon("Engi", "Firemon", Skill.randomElements());
        Player test = new Player(active);

        active.setMessage("Engimonnnnnn");
        test.showActiveEngi();
        test.interactWithEngi();

        test.showAllOwnedEngi();
        test.showAllOwnedItem();
        test.showEngiInfo("Engi");
        test.showEngiInfo("Engia");

        // Max 2 elements, for testing only
        UniqueSkill unique = new UniqueSkill("UNIQUE", 1, 1, Skill.randomElements(), "Icemon");
        SpecialSkill special = new SpecialSkill("SPECIAL", 2, 2, Skill.randomElements(), 10);
        Skill skill = new Skill("BASIC", 1, 1, Skill.randomElements());
        Skill another = new Skill("BASIC2", 1, 2, Skill.randomElements());
        Skill another2 = new Skill("BASIC3", 1, 2, Skill.randomElements());
        Skill another3 = new Skill("BASIC4", 1, 2, Skill.randomElements());

        SkillItem item = new SkillItem(skill, 2);
        SkillItem anotherItem = new SkillItem(another, 1);
        SkillItem anotherItem2 = new SkillItem(another2, 1);
        SkillItem anotherItem3 = new SkillItem(another3, 1);
        SkillItem uniqueItem = new SkillItem(unique, 4);
        SkillItem specialItem = new SkillItem(special, 3);
        Engimon newEngi = new Engimon("Engi2", "Icemon", Skill.randomElements());

        test.addSkillItem(item);
        test.addSkillItem(anotherItem);
        test.addSkillItem(anotherItem2);
        test.addSkillItem(anotherItem3);
        test.addSkillItem(uniqueItem);
        test.addSkillItem(specialItem);
        test.showAllOwnedItem();
        test.addSkillItem(specialItem);
        test.showAllOwnedItem();
        test.addEngimon(newEngi);

        test.learnNewSkill("UNIQUE", "Engi");
        test.learnNewSkill("BASIC", "Engi2");
        test.learnNewSkill("UNIQUE", "Engi2");
        test.learnNewSkill("SPECIAL", "Engi2");
        test.learnNewSkill("BASIC2", "Engi2");
        test.learnNewSkill("BASIC3", "Engi2");

        test.showAllOwnedEngi();
        test.showAllOwnedItem();

        test.learnNewSkill("BASIC4", "Engi2");
        test.showEngiInfo("Engi2");
    }

    // Do breeding here

    // Do Battle here
}