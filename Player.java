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
            System.out.println("SHOULD WE THROW EXCEPTION HERE?");
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
    public void addSkillItem(SkillItem newSkillItem, Integer amount) {
        int inInvent = this.isAlreadyInInvent(newSkillItem.getSkill().getSkillName());

        if (!this.isStillEmpty()) {
            System.out.println("SHOULD WE THROW EXCEPTION HERE?");
        } else {
            if (inInvent != -1) {
                this.skillInvent.getItemList().get(inInvent).addAmount(amount);
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

    // Do breeding here

    // Do Battle here
}