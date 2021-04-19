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

        inventorySkillItem.add(newSkillItem);
	    System.out.print("Berhasil menambahkan skill item baru");
	    System.out.print("\n");
    }

    public void addEngimon(Engimon newEngimon){
        if(inventoryEngimon.getInventorySize() + inventorySkillItem.getInventorySize() + 1 > MAX_CAPACITY){
            throw "Maximum Capacity";
            return;
        }
        inventoryEngimon.add(newEngimon);
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
        if (replacedSkillIdx - 1 > ActiveEngimon.getSkill().size() - 1){
            System.out.print("Indeks Invalid! Pergantian Gagal");
            System.out.print("\n");
            return;
        }
        else{
            ActiveEngimon.RemoveSkillByIdx(replacedSkillIdx);
            ActiveEngimon.AddSkill(newSkill);
        }
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
        Integer index = 1;
        if (inventoryEngimon.getInventorySize() == 0){
            System.out.print("Tidak ada engimon di inventory");
            System.out.print("\n");
        }
        else {
            System.out.print("Daftar Engimon yang dimiliki");
            System.out.print("\n");
            for (Engimon engimon : inventoryEngimon.getInventoryVector()){
                System.out.print(engimon.getName());
                System.out.print("\n");
                System.out.print(".");
                index++;
            }
        }
    }

    public void showStatsEngimon(){

    }

    public void showActiveEngimon(){
        System.out.print("Engimon yang sedang aktif berpetualang : ");
        System.out.print("\n");
        ActiveEngimon.showStats();
    }

    public void swapActiveEngimon(String newEngimonName){

    }

    public void interactWithEngimon(){
        System.out.print(ActiveEngimon.getName());
        System.out.print(" : ");
        System.out.print(ActiveEngimon.getMessage());
        System.out.print("\n");
    }

    public void showOwnedItems(){
        if (inventorySkillItem.getInventorySize() == 0)
        {
            System.out.print("Tidak ada Skill Items di Inventory!");
            System.out.print("\n");
        }
        else
        {
            System.out.print("Daftar Skill Items yang dimiliki : ");
            System.out.print("\n");
    
            for (SkillItem items : inventorySkillItem.getInventoryVector())
            {
                items.skillItemInfo();
            }
        }
    }

    public void doBreed(){

    }
}