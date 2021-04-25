package com.poo.engimon.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.poo.engimon.screens.Play;

import java.util.*;


class SortSkill implements Comparator<SkillItem> {
    public int compare(SkillItem skillItem1, SkillItem skillItem2) {
        return skillItem2.getSkill().getSkillPower() - skillItem1.getSkill().getSkillPower();
    }
}

class SortEngimon implements Comparator<Engimon> {
    public int compare(Engimon engi1, Engimon engi2) {
        return engi2.getLevel() - engi1.getLevel();
    }
}

public class Player extends Sprite implements InputProcessor {
    // Gravity and speed of the entity
    private float speed = 60 * 2, gravity = 60 * 1.8f;
    // Velocity of the player entity
    private Vector2 velocity = new Vector2();
    // Layer of blocks with collisions
    private TiledMapTileLayer collisionLayer;
    // Player Atlas Animation
    private Animation<TextureRegion> s, a, w, d;
    // Player Orientation
    private String orientation = "s";
    // Animation Time
    private float animationTime = 0;

    private Engimon activeEngimon;
    private static Integer maxCapacity = 0;
    private Inventory<Engimon> engimonInvent = new Inventory<Engimon>();
    private Inventory<SkillItem> skillInvent = new Inventory<SkillItem>();
    private String playerName;

    private Play play;

    public String answer;

    // Constructor
    public Player(Animation<TextureRegion> s, Animation<TextureRegion> a, Animation<TextureRegion> w, Animation<TextureRegion> d,
                  TiledMapTileLayer collisionsLayer, int x, int y, Play play, Engimon active){
        // Make the sprite
        super(s.getKeyFrame(0));

        // Animations
        this.s = s;
        this.a = a;
        this.w = w;
        this.d = d;

        // Collision Layers and Init Position
        this.collisionLayer = collisionsLayer;
        this.setPosition(x * this.getCollisionLayer().getTileWidth(),
                (this.getCollisionLayer().getHeight() - y) * this.getCollisionLayer().getTileHeight());

        this.play = play;
        this.activeEngimon = active;
        this.engimonInvent.add(this.activeEngimon);
        this.playerName = "Ash";
    }

    // Getters and setters
    public float getSpeed(){ return this.speed; }
    public void setSpeed(float speed) { this.speed = speed; }
    public float getGravity(){ return this.gravity; }
    public void setGravity(float gravity) { this.gravity = gravity; }
    public Vector2 getVelocity() { return velocity; }
    public void setVelocity(Vector2 velocity) { this.velocity = velocity; }
    public TiledMapTileLayer getCollisionLayer(){ return this.collisionLayer; }
    public void setCollisionLayer(TiledMapTileLayer collisionLayer) { this.collisionLayer = collisionLayer; }
    public String getOrientation() { return orientation; }
    public void setOrientation(String orientation) { this.orientation = orientation; }

    // Override the draw method
    @Override
    public void draw(Batch batch) {
        this.move(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    // Check if a block is a collision block
    public boolean isCollision(float x, float y){
        Cell block = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        // Blocked Property
        String collision = "collision";
        return block != null && block.getTile() != null && block.getTile().getProperties().containsKey(collision);
    }

    // Move Position
    public void move(int x, int y){
        this.setX(getX() + x);
        this.setY(getY() + y);
    }

    // Update the Player
    public void move(float delta){
        // Handle null pointer
        if(delta > 1 / 10f){
            delta = 1 / 10f;
        }

        // Applies gravity to the entity
        // velocity.y -= this.gravity * delta;
        // Clamp the gravity
        // if(velocity.y > this.speed){
        //     velocity.y = this.speed;
        // }
        // else if(velocity.y < this.speed){
        //     velocity.y = -this.speed;
        // }

        // Old position
        float oldX = getX(), oldY = getY();
        boolean collideOnX = false, collideOnY = false;

        if(tileNotExists(getX(), getY(), collisionLayer)){
            setX(oldX);
            setY(oldY);
            velocity.x = 0;
            velocity.y = 0;
        }

        // Move on X
        this.setX(getX() + velocity.x * delta);
        if(velocity.x < 0){
            // Top Left
            collideOnX = isCollision(getX(), getY() + getHeight());
            // Mid Left
            if(!collideOnX){
                collideOnX = isCollision(getX(), getY() + getHeight() / 2);
            }
            // Bottom Left
            if(!collideOnX) {
                collideOnX = isCollision(getX(), getY());
            }
        }
        else if(velocity.x > 0){
            // Top Right
            collideOnX = isCollision(getX(), getY() + getHeight());
            // Mid Right
            if(!collideOnX){
                collideOnX = isCollision(getX(), getY() + getHeight() / 2);
            }
            // Bottom Left
            if(!collideOnX){
                collideOnX = isCollision(getX(), getY());
            }
        }

        // Collision on X happen, revert to old position and set velocity to 0
        if(collideOnX){
            setX(oldX);
            velocity.x = 0;
        }

        // Move on Y
        this.setY(getY() + velocity.y * delta);

        if(velocity.y < 0){
            // Bottom Left
            collideOnY = isCollision(getX(), getY());
            // Bottom Middle
            if(!collideOnY){
                collideOnY = isCollision(getX() + getWidth() / 2, getY());
            }
            // Bottom Right
            if (!collideOnY){
                collideOnY = isCollision(getX() + getWidth(), getY());
            }
        }
        else if(velocity.y > 0){
            // Top Left
            collideOnY = isCollision(getX(), getY() + getHeight());
            // Top Middle
            if(!collideOnY){
                collideOnY = isCollision(getX() + getWidth(), getY() + getHeight());
            }
            // Top Right
            if (!collideOnY){
                collideOnY = isCollision(getX() + getWidth(), getY() + getHeight());
            }
        }
        // Collision on Y happen, revert to old position and set velocity to 0
        if(collideOnY){
            setY(oldY);
            velocity.y = 0;
        }

        // Update Animation
        animationTime += delta;
        if(notOutOfBound(animationTime)) {
            if (this.velocity.x < 0) { setRegion(a.getKeyFrame(animationTime)); }
            else if (velocity.x > 0) { setRegion(d.getKeyFrame(animationTime)); }
            else if(velocity.y > 0){ setRegion(w.getKeyFrame(animationTime)); }
            else if(velocity.y < 0){ setRegion(s.getKeyFrame(animationTime)); }
            else
            {
                if (this.orientation.equals("w")) { setRegion(w.getKeyFrame(0)); }
                else if(this.orientation.equals("s")) { setRegion(s.getKeyFrame(0)); }
                else if(this.orientation.equals("a")){ setRegion(a.getKeyFrame(0)); }
                else{ setRegion(d.getKeyFrame(0)); }
            }
        }
    }

    public boolean notOutOfBound(float idx){ return idx > -1; }

    private boolean tileNotExists(float tileX, float tileY, TiledMapTileLayer map)
    {
        return tileX < 0 && tileY < 0 &&
                tileX > map.getWidth() && tileY > map.getHeight();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Keys.W :
            case Keys.UP:
                if(this.play.option.isVisible()){
                    this.play.option.moveUp();
                    break;
                }
                velocity.y = speed;
                animationTime = 0;
                this.setOrientation("w");
                break;
            case Keys.A :
            case Keys.LEFT:
                velocity.x = -speed;
                animationTime = 0;
                this.setOrientation("a");
                break;
            case Keys.D:
            case Keys.RIGHT:
                velocity.x = speed;
                animationTime = 0;
                this.setOrientation("d");
                break;
            case Keys.S:
            case Keys.DOWN:
                if(this.play.option.isVisible()){
                    this.play.option.moveDown();
                    break;
                }
                velocity.y = -speed;
                animationTime = 0;
                this.setOrientation("s");
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Keys.W:
            case Keys.UP:
                if(this.play.option.isVisible()){
                    this.play.option.moveUp();
                    break;
                }
                velocity.y = 0;
                animationTime = 0;
                this.setOrientation("w");
                break;
            case Keys.A:
            case Keys.LEFT:
                velocity.x = 0;
                animationTime = 0;
                this.setOrientation("a");
                break;
            case Keys.D:
            case Keys.RIGHT:
                velocity.x = 0;
                animationTime = 0;
                this.setOrientation("d");
                break;
            case Keys.S:
            case Keys.DOWN:
                if(this.play.option.isVisible()){
                    this.play.option.moveDown();
                    break;
                }
                velocity.y = 0;
                animationTime = 0;
                this.setOrientation("s");
                break;
            case Keys.M:
                this.play.uiPopup.setVisible(!this.play.uiPopup.isVisible());
                break;
            case Keys.H:
                this.play.uiPopup.setText(this.showHelp());
                this.play.uiPopup.setVisible(!this.play.uiPopup.isVisible());
                break;
            case Keys.R:
                play.lastCommand = "r";
                if(!play.uiPopup.isVisible()) {
                    this.play.text.setVisible(true);
                    Gdx.input.setInputProcessor(this.play.uiStage);
                }
                break;
            case Keys.I:
                this.play.uiPopup.setText(this.showAllOwnedItem());
                this.play.uiPopup.setVisible(!this.play.uiPopup.isVisible());
                break;
            case Keys.E:
                this.play.uiPopup.setText(this.showAllOwnedEngi());
                this.play.uiPopup.setVisible(!this.play.uiPopup.isVisible());
                break;
            case Keys.B:
                play.lastCommand = "b";
                if(!play.uiPopup.isVisible()) {
                    this.play.text.setVisible(true);
                    this.play.text2.setVisible(true);
                    this.play.text3.setVisible(true);
                    Gdx.input.setInputProcessor(this.play.uiStage);
                }
                break;
            case Keys.Q:
                this.play.uiPopup.setText(this.showActiveEngi());
                this.play.uiPopup.setVisible(!this.play.uiPopup.isVisible());
                break;
            case Keys.P:
                play.lastCommand = "p";
                if(!play.uiPopup.isVisible()) {
                    this.play.text.setVisible(true);
                    Gdx.input.setInputProcessor(this.play.uiStage);
                }
                break;
            case Keys.T:
                this.play.uiPopup.setText(this.interactWithEngi());
                this.play.uiPopup.setVisible(!this.play.uiPopup.isVisible());
                break;
            case Keys.C:
                play.lastCommand = "c";
                if(!play.uiPopup.isVisible()) {
                    this.play.text.setVisible(true);
                    Gdx.input.setInputProcessor(this.play.uiStage);
                }
                break;
            case Keys.L:
                play.lastCommand = "l";
                if(!play.uiPopup.isVisible()) {
                    this.play.text.setVisible(true);
                    this.play.text2.setVisible(true);
                    Gdx.input.setInputProcessor(this.play.uiStage);
                }
                break;
            case Keys.X:
                play.lastCommand = "x";
                if(!play.uiPopup.isVisible()) {
                    this.play.text.setVisible(true);
                    Gdx.input.setInputProcessor(this.play.uiStage);
                }
                break;
            case Keys.SPACE:
                if(this.play.option.getSelectedOptionIndex() == 0){
                    this.play.uiPopup.setText("Good Luck!");
                }
                else{
                    //this.setActiveEngimon(Load.extractActiveEngimon());
                    //this.engimonInvent.setInventory(Load.extractInventoryEngimon());
                    //this.skillInvent.setInventory(Load.extractInventorySkillItem());
                    //this.changePlayerName(Load.extractPlayerName());
                    this.play.uiPopup.setText("Successfully Loading!");
                }
                this.play.uiPopup.setVisible(!this.play.uiPopup.isVisible());
                this.play.option.setVisible(false);
                break;
            case Keys.ENTER:
                try{
                    Thread.sleep(2000);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                finally {
                    if(this.play.uiPopup.isVisible()){
                        this.play.uiPopup.setVisible(false);
                        this.play.text.setVisible(false);
                        this.play.text2.setVisible(false);
                        this.play.text3.setVisible(false);
                    }
                }
            break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) { return false; }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }

    public void changePlayerName(String newName) {
        this.playerName = newName;
    }

    public Engimon getActiveEngimon() { return this.activeEngimon; }

    public String showHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("w | UP   : Move Up\n");
        sb.append("a | lEFT : Move Left\n");
        sb.append("s | DOWN : Move Down\n");
        sb.append("d | RIGHT:Move Right\n");
        sb.append("h : Show Help Menu\n");
        sb.append("r : Rename your engimon\'s name\n");
        sb.append("i : Show Skill Items\n");
        sb.append("e : Show Engimons\n");
        sb.append("b : Breed Two Engimons\n");
        sb.append("q : Show Active Engimons\n");
        sb.append("p : Show Engimons Stats\n");
        sb.append("t : Interact With Engimon\n");
        sb.append("c : Swap Active Engimons\n");
        sb.append("l : Learn New Skills\n");
        sb.append("x : Challenge Engimons!\n");

        return sb.toString();
    }

    // Setter
    public void setActiveEngimon(Engimon active) {
        this.activeEngimon = active;
    }

    // Swap Active Engimon
    public String swapActiveEngimon(String newEngi){
        StringBuilder sb = new StringBuilder();
        for(Engimon engi: this.engimonInvent.getItemList()){
            if(engi.getName().equals(newEngi)){
                this.activeEngimon = engi;
                sb.append("Berhasil mengganti Engimon!");
                return sb.toString();
            }
        }
        return sb.append("Tidak berhasil menemukan Engimon ini di Inventory Anda!").toString();
    }

    // Getters
    public String getPlayerName() {
        return this.playerName;
    }

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

    // Check if there is no Engimon anymore in Inventory
    public Boolean isLosing(){
        return this.engimonInvent.size() == 0;
    }

    // Add a new Engimon
    public String addEngimon(Engimon newEngimon) {
        StringBuilder sb = new StringBuilder();
        if (!this.isStillEmpty()) {
            sb.append("Inventory sudah penuh!");
        } else {
            this.engimonInvent.add(newEngimon);
            sb.append("Berhasil menambahkan Engimon baru!");
        }
        return sb.toString();
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
    public String addSkillItem(SkillItem newSkillItem) {
        int inInvent = this.isAlreadyInInvent(newSkillItem.getSkill().getSkillName());
        StringBuilder sb = new StringBuilder();

        if (!this.isStillEmpty()) {
            sb.append("Inventory sudah penuh!");
        } else {
            if (inInvent != -1) {
                this.skillInvent.getItemList().get(inInvent).addAmount(newSkillItem.getAmount());
            } else {
                this.skillInvent.getItemList().add(newSkillItem);
            }
            sb.append("Berhasil menambahkan Skill Item baru!");
        }
        return sb.toString();
    }

    // Show All Engimons Info
    public String showAllOwnedEngi() {
        StringBuilder sb = new StringBuilder();
        if (this.engimonInvent.isEmptyInvent()) {
            sb.append("Tidak ada Engimon di Inventory\n");
        } else {
            this.sortingEngimonInventory();
            sb.append("Daftar Engimon yang dimiliki:\n");
            for (Engimon engi : this.engimonInvent.getItemList()) {
                sb.append(engi.showStats());
            }
        }
        return sb.toString();
    }

    // Show certain Engimons Info
    public String showEngiInfo(String engiName) {
        StringBuilder sb = new StringBuilder();
        for (Engimon engi : this.engimonInvent.getItemList()) {
            if (engi.getName().equals(engiName)) {
                sb.append(engi.showStats());
                return sb.toString();
            }
        }
        return sb.append("Tangkap Engimon ini untuk memenuhi rasa penasaranmu!").toString();
    }

    // Show Active Engimons Info
    public String showActiveEngi() {
        StringBuilder sb = new StringBuilder();
        sb.append("Engimon yang sedang aktif berpetualang:\n");
        sb.append(this.activeEngimon.showStats());
        return sb.toString();
    }

    // Interact with active engimon
    public String interactWithEngi() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.activeEngimon.getName() + " said:\n");
        sb.append(this.activeEngimon.getMessage());

        return sb.toString();
    }

    // Show all Skill Items
    public String showAllOwnedItem() {
        StringBuilder sb = new StringBuilder();
        if (this.skillInvent.isEmptyInvent()) {
            sb.append("Tidak ada SKill Item di Inventory");
        } else {
            this.sortingItemInventory();
            sb.append("Daftar Skill Item yang dimiliki:\n");
            for (SkillItem item : this.skillInvent.getItemList()) {
                sb.append(item.skillItemInfo());
            }
        }
        return sb.toString();
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
    public void decOrRemove(int index) throws IndexOutOfBoundException {
        if (this.skillInvent.getItemList().get(index).getAmount() > 1)
            this.skillInvent.getItemList().get(index).decAmount(1);
        else
            this.skillInvent.remove(index);
    }

    // Learn a skill item to active engimon
    public String learnNewSkill(String skillName, String engiName) {
        int hasItem = isAlreadyInInvent(skillName), hasEngi = isAlreadyOwned(engiName);
        Boolean wantToAdd = false;
        StringBuilder sb = new StringBuilder();

        if (hasItem == -1) {
            sb.append("Tidak ada Skill Item target di Inventory!");
        } else if (hasEngi == -1) {
            sb.append("Tidak ada Engimon target di Inventory!");
        } else if (this.engimonInvent.getItemList().get(hasEngi).hasSkill(skillName)) {
            sb.append("Engimon ini sudah memiliki Skill tersebut!");
        } else {
            ArrayList<String> elements = this.engimonInvent.getItemList().get(hasEngi).getElement();
            if (this.skillInvent.getItemList().get(hasItem).getSkill() instanceof UniqueSkill) {
                UniqueSkill newSkill = (UniqueSkill) this.skillInvent.getItemList().get(hasItem).getSkill();
                if (newSkill.isSkillLearnable(elements, this.engimonInvent.getItemList().get(hasEngi).getSpecies())) {
                    this.engimonInvent.getItemList().get(hasEngi).addSkill(newSkill);
                    wantToAdd = true;
                } else {
                    sb.append("Engimon ini tidak cocok dengan spesies Skill tersebut!");
                }
            } else {
                Skill newSkill = this.skillInvent.getItemList().get(hasItem).getSkill();
                if (newSkill.isSkillLearnable(elements)) {
                    this.engimonInvent.getItemList().get(hasEngi).addSkill(newSkill);
                    wantToAdd = true;
                } else {
                    sb.append("Engimon ini tidak cocok dengan elemen Skill tersebut!");
                }
            }
            if (wantToAdd) {
                try {
                    this.decOrRemove(hasItem);
                } catch (Exception e) {
                    sb.append(e.getMessage());
                }
            }
        }
        return sb.toString();
    }

    public void sortingItemInventory() {
        Collections.sort(this.skillInvent.getItemList(), new SortSkill());
    }

    public void sortingEngimonInventory() {
        HashMap<String, Inventory<Engimon>> groupEngimon = new HashMap<String, Inventory<Engimon>>();

        for (Engimon engi : this.engimonInvent.getItemList()) {
            if (engi.getElement().size() == 1) {
                if (groupEngimon.get(engi.getElement().get(0)) != null) {
                    groupEngimon.get(engi.getElement().get(0)).add(engi);
                } else {
                    Inventory<Engimon> inventEngi = new Inventory<Engimon>();
                    inventEngi.add(engi);
                    groupEngimon.put(engi.getElement().get(0), inventEngi);
                }
            } else {
                if (groupEngimon.get("MORE") != null) {
                    groupEngimon.get("MORE").add(engi);
                } else {
                    Inventory<Engimon> inventEngi = new Inventory<Engimon>();
                    inventEngi.add(engi);
                    groupEngimon.put("MORE", inventEngi);
                }
            }
        }

        for (Inventory<Engimon> inventEngi : groupEngimon.values()) {
            Collections.sort(inventEngi.getItemList(), new SortEngimon());
        }

        this.engimonInvent.getItemList().clear();
        if (groupEngimon.get("FIRE") != null)
            for (Engimon engi : groupEngimon.get("FIRE").getItemList())
                this.engimonInvent.add(engi);

        if (groupEngimon.get("WATER") != null)
            for (Engimon engi : groupEngimon.get("WATER").getItemList())
                this.engimonInvent.add(engi);

        if (groupEngimon.get("ICE") != null)
            for (Engimon engi : groupEngimon.get("ICE").getItemList())
                this.engimonInvent.add(engi);

        if (groupEngimon.get("GROUND") != null)
            for (Engimon engi : groupEngimon.get("GROUND").getItemList())
                this.engimonInvent.add(engi);

        if (groupEngimon.get("ELECTRIC") != null)
            for (Engimon engi : groupEngimon.get("ELECTRIC").getItemList())
                this.engimonInvent.add(engi);

        if (groupEngimon.get("MORE") != null)
            for (Engimon engi : groupEngimon.get("MORE").getItemList())
                this.engimonInvent.add(engi);
    }

    public String breed(String engi1, String engi2, String namaAnak){
        Boolean find = false;
        Engimon engimon1 = null, engimon2 = null;
        StringBuilder sb = new StringBuilder();

        for(Engimon engi: this.engimonInvent.getItemList()){
            if(engi.getName().equals(engi1)){
                engimon1 = engi;
            }
            else if(engi.getName().equals(engi2)){
                engimon2 = engi;
            }
        }
        if(engimon1 != null && engimon2 != null){
            Breed breedTools = new Breed();
            Engimon anakEngi = breedTools.breed(engimon1, engimon2, namaAnak);

            if(anakEngi.getName() == null){
                sb.append("Breeding Gagal!");
            }
            else{
                this.addEngimon(anakEngi);
                sb.append("Breeding " + namaAnak + " berhasil!");
            }
        }
        else{
            sb.append("Salah satu atau kedua Parent tidak ditemukan!");
        }
        return sb.toString();
    }

    public String setBattle(Battle battle) {
        StringBuilder sb = new StringBuilder();

        sb.append(battle.doBattle());
        return sb.toString();
    }

    public String doBattle(Battle battle, String answer) {
        StringBuilder sb = new StringBuilder();
        if (answer.equals("Yes")) {
            sb.append(battle.startBattle());
        }
        else {
            sb.append(battle.cancelBattle());
        }
        return sb.toString();
    }
}
