import java.util.*;

public class Player{
    private Integer MAX_CAPACITY;
    private Engimon ActiveEngimon;
    private Map map;
    private Inventory<Engimon> inventoryEngimon;
    private Inventory<SkillItem> inventorySkillItem;

    public Player() {
        map.setplayerPosition(1,1);
        map.setactiveEngimonPosition(0,0);
    }

    public Player(Engimon activeEngimon) {
        addEngimon(activeEngimon);
        map.setplayerPosition(1,1);
        map.setactiveEngimonPosition(0,0);
    }

    public void addSkillItem(SkillItem newSkillItem){
        if(inventoryEngimon.getInventorySize() + inventorySkillItem.getInventorySize() + 1 > MAX_CAPACITY){
            throw "Maximum Capacity";
            return;
        }

        Integer i = 0;
        while (i < inventorySkillItem.getInventorySize()){
            if (inventorySkillItem.getInventoryVector()[i].getSkill().getSkillName().compare(newSkillItem.getSkill().getSkillName()) == 0)
            {
                System.out.print("Menambahkan Skill Item untuk ");
                System.out.print(newSkillItem.getSkill().getSkillName());
                System.out.print("\n");
                inventorySkillItem.getInventoryVector()[i].addItemAmount(1);
                return;
            }
            i++;
        }

        //inventorySkillItem << newSkillItem;
	    System.out.print("Berhasil menambahkan skill item baru");
	    System.out.print("\n");

    public void addEngimon(Engimon newEngimon){
        if(inventoryEngimon.getInventorySize() + inventorySkillItem.getInventorySize() + 1 > MAX_CAPACITY){
            throw "Maximum Capacity";
            return;
        }
        //inventoryEngimon << newEngimon;
	    System.out.print("Berhasil menambahkan engimon baru");
	    System.out.print("\n");
    }

    public Map getMap(){
        return map;
    }

    public Inventory<Engimon> getInventoryEngimon() { return inventoryEngimon; }
    public Inventory<SkillItem> getInventorySkillItem() { return inventorySkillItem; }
    public Engimon getActiveEngimon() { return ActiveEngimon; }

    public void learnSkill(String SkillName){

    }

    public void replaceSkillItem(Integer replacedSkillIdx, Skill newSkill){

    }

    public void moveUp(){

    }

    public void moveDown(){

    }

    public void moveLeft(){

    }

    public void moveRight(){

    }

    public void showOwnedEngimon(){

    }

    public void showStatsEngimon(){

    }

    public void showActiveEngimon(){

    }

    public void swapActiveEngimon(String newEngimonName){

    }

    public void interactWithEngimon(){

    }

    public void doBreed(){

    }
}