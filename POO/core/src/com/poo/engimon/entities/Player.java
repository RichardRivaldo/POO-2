package com.poo.engimon.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

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
    //Dialog box
    private Stage stage;
    //Engimon Property
    private Engimon activeEngimon;
    private static Integer maxCapacity = 10;
    private Inventory<Engimon> engimonInvent = new Inventory<Engimon>();
    private Inventory<SkillItem> skillInvent = new Inventory<SkillItem>();
    private String playerName;
    // Constructor
    public Player(Animation<TextureRegion> s, Animation<TextureRegion> a, Animation<TextureRegion> w, Animation<TextureRegion> d,
                  TiledMapTileLayer collisionsLayer, int x, int y){
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
        this.activeEngimon = null;
    }
    //copy constructor
    public Player(Engimon active, Animation<TextureRegion> s, Animation<TextureRegion> a, Animation<TextureRegion> w, Animation<TextureRegion> d,
                  TiledMapTileLayer collisionsLayer, int x, int y){
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
                velocity.y = 0;
                animationTime = 0;
                this.setOrientation("s");
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

    public Engimon getActiveEngimon() {
        return this.activeEngimon;
    }

    public void showHelp() {
        System.out.println("w : Move Up");
        System.out.println("a : Move Left");
        System.out.println("s : Move Down");
        System.out.println("d : Move Right");
        System.out.println("help : Show Help Menu");
        System.out.println("rename: Rename your engimon\'s name");
        System.out.println("items : Show Skill Items");
        System.out.println("engimons : Show Engimons");
        System.out.println("breed : Breed Two Engimons");
        System.out.println("show : Show Active Engimons");
        System.out.println("stats : Show Engimons Stats");
        System.out.println("engi : Interact With Engimon");
        System.out.println("swap : Swap Active Engimons");
        System.out.println("learn : Learn New Skills");
        System.out.println("battle : Challenge Engimons!");
        System.out.println("quit : Exit The Game");
    }

    // Setter
    public void setActiveEngimon(Engimon active) {
        this.activeEngimon = active;
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
    public void decOrRemove(int index) throws IndexOutOfBoundException {
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
        } else if (this.engimonInvent.getItemList().get(hasEngi).hasSkill(skillName)) {
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
            if (wantToAdd) {
                try {
                    this.decOrRemove(hasItem);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
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

        if (groupEngimon.get("WIND") != null)
            for (Engimon engi : groupEngimon.get("WIND").getItemList())
                this.engimonInvent.add(engi);

        if (groupEngimon.get("EARTH") != null)
            for (Engimon engi : groupEngimon.get("EARTH").getItemList())
                this.engimonInvent.add(engi);

        if (groupEngimon.get("MORE") != null)
            for (Engimon engi : groupEngimon.get("MORE").getItemList())
                this.engimonInvent.add(engi);
    }

}
