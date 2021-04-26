package com.poo.engimon.entities;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Enemy extends Sprite {
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
    //Engimon
    private Engimon engimon;

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

    Random random = new Random();
    private void randomvector(){
        int oneTwoX = random.nextInt(20)+1;
        int oneTwoY = random.nextInt(20)+1;
        if (oneTwoX == 1 && oneTwoY == 1){
            setVelocity(new Vector2((-1*speed),(-1*speed)));
        }else if (oneTwoX == 1 && oneTwoY == 2){
            setVelocity(new Vector2((-1*speed),(speed)));
        }else if (oneTwoX == 2 && oneTwoY == 1){
            setVelocity(new Vector2((speed),(-1*speed)));
        }else if (oneTwoX == 2 && oneTwoY == 2){
            setVelocity(new Vector2((speed),(speed)));
        }else if (oneTwoX >= 3 && oneTwoY >= 3){
            setVelocity(new Vector2(0,0));
        }else if (oneTwoX >= 3 && oneTwoY == 1){
            setVelocity(new Vector2(0,(-1*speed)));
        }else if (oneTwoX == 1){
            setVelocity(new Vector2((-1*speed),0));
        }else if (oneTwoX >= 3){
            setVelocity(new Vector2(0,(speed)));
        }else {
            setVelocity(new Vector2((speed),0));
        }

    }

    public Enemy(Animation<TextureRegion> s, Animation<TextureRegion> a, Animation<TextureRegion> w, Animation<TextureRegion> d,
                  TiledMapTileLayer collisionsLayer, int x, int y, Engimon engimon){
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
        this.engimon = engimon;
    }

    // Override the draw method
    @Override
    public void draw(Batch batch) {
        //this.move(Gdx.graphics.getDeltaTime());
        Random randomgerak = new Random();
        int peluang = randomgerak.nextInt(100) + 1;
        if (peluang > 98){
            this.randomvector();
        }
        this.move((float) 0.01);
        super.draw(batch);
    }

    // Check if a block is a collision block
    public boolean isCollision(float x, float y){
        TiledMapTileLayer.Cell block = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        // Blocked Property
        String collision = "collision";
        return block != null && block.getTile() != null && block.getTile().getProperties().containsKey(collision);
    }

    // Move Position
    public void move(int x, int y){
        this.setX(getX() + x);
        this.setY(getY() + y);
    }

    //Coordinate
    public float xpos(){
        return this.getX();
    }

    public float ypos(){
        return this.getY();
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

    public Engimon getEngimonLiar(){
        return this.engimon;
    }

    public boolean notOutOfBound(float idx){ return idx > -1; }

    private boolean tileNotExists(float tileX, float tileY, TiledMapTileLayer map)
    {
        return tileX < 0 && tileY < 0 &&
                tileX > map.getWidth() && tileY > map.getHeight();
    }
}
