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

public class ActiveEngimon extends Sprite {
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
    private Player player;
    private Play play;

    // Constructorww
    public ActiveEngimon(Player player, Animation<TextureRegion> s, Animation<TextureRegion> a, Animation<TextureRegion> w, Animation<TextureRegion> d,
                         TiledMapTileLayer collisionsLayer, int x, int y, Play play) {
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
        this.player = player;
        this.velocity = player.getVelocity();
    }

    // Getters and setters
    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGravity() {
        return this.gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return this.collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public float xpos() {
        return this.getX();
    }

    public float ypos() {
        return this.getY();
    }

    // Override the draw method
    @Override
    public void draw(Batch batch) {
        this.move(player.deltaTime());
        super.draw(batch);
    }

    // Check if a block is a collision block
    public boolean isCollision(float x, float y) {
        Cell block = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        // Blocked Property
        String collision = "collision";
        return block != null && block.getTile() != null && block.getTile().getProperties().containsKey(collision);
    }

    // Move Position
    public void move(int x, int y) {
        this.setX(getX() + x);
        this.setY(getY() + y);
    }

    // Update the Player
    public void move(float delta) {
        // Handle null pointer
        if (delta > 1 / 10f) {
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

        if (tileNotExists(getX(), getY(), collisionLayer)) {
            setX(oldX);
            setY(oldY);
            velocity.x = 0;
            velocity.y = 0;
        }

        // Move on X
        this.setX(getX() + velocity.x * delta);
        if (velocity.x < 0) {
            // Top Left
            collideOnX = isCollision(getX(), getY() + getHeight());
            // Mid Left
            if (!collideOnX) {
                collideOnX = isCollision(getX(), getY() + getHeight() / 2);
            }
            // Bottom Left
            if (!collideOnX) {
                collideOnX = isCollision(getX(), getY());
            }
        } else if (velocity.x > 0) {
            // Top Right
            collideOnX = isCollision(getX(), getY() + getHeight());
            // Mid Right
            if (!collideOnX) {
                collideOnX = isCollision(getX(), getY() + getHeight() / 2);
            }
            // Bottom Left
            if (!collideOnX) {
                collideOnX = isCollision(getX(), getY());
            }
        }

        // Collision on X happen, revert to old position and set velocity to 0
        if (collideOnX) {
            setX(oldX);
            velocity.x = 0;
        }

        // Move on Y
        this.setY(getY() + velocity.y * delta);

        if (velocity.y < 0) {
            // Bottom Left
            collideOnY = isCollision(getX(), getY());
            // Bottom Middle
            if (!collideOnY) {
                collideOnY = isCollision(getX() + getWidth() / 2, getY());
            }
            // Bottom Right
            if (!collideOnY) {
                collideOnY = isCollision(getX() + getWidth(), getY());
            }
        } else if (velocity.y > 0) {
            // Top Left
            collideOnY = isCollision(getX(), getY() + getHeight());
            // Top Middle
            if (!collideOnY) {
                collideOnY = isCollision(getX() + getWidth(), getY() + getHeight());
            }
            // Top Right
            if (!collideOnY) {
                collideOnY = isCollision(getX() + getWidth(), getY() + getHeight());
            }
        }
        // Collision on Y happen, revert to old position and set velocity to 0
        if (collideOnY) {
            setY(oldY);
            velocity.y = 0;
        }

        // Update Animation
        animationTime += delta;
        if (notOutOfBound(animationTime)) {
            if (this.velocity.x < 0) {
                setRegion(a.getKeyFrame(animationTime));
            } else if (velocity.x > 0) {
                setRegion(d.getKeyFrame(animationTime));
            } else if (velocity.y > 0) {
                setRegion(w.getKeyFrame(animationTime));
            } else if (velocity.y < 0) {
                setRegion(s.getKeyFrame(animationTime));
            } else {
                if (this.orientation.equals("w")) {
                    setRegion(w.getKeyFrame(0));
                } else if (this.orientation.equals("s")) {
                    setRegion(s.getKeyFrame(0));
                } else if (this.orientation.equals("a")) {
                    setRegion(a.getKeyFrame(0));
                } else {
                    setRegion(d.getKeyFrame(0));
                }
            }
        }
    }

    public boolean notOutOfBound(float idx) {
        return idx > -1;
    }

    private boolean tileNotExists(float tileX, float tileY, TiledMapTileLayer map) {
        return tileX < 0 && tileY < 0 &&
                tileX > map.getWidth() && tileY > map.getHeight();
    }
/*
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
            case Keys.UP:
                if (this.play.option.isVisible()) {
                    this.play.option.moveUp();
                    break;
                }
                velocity.y = speed;
                animationTime = 0;
                this.setOrientation("w");
                break;
            case Keys.A:
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
                if (this.play.option.isVisible()) {
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
        switch (keycode) {
            case Keys.W:
            case Keys.UP:
                if (this.play.option.isVisible()) {
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
                if (this.play.option.isVisible()) {
                    this.play.option.moveDown();
                    break;
                }
                velocity.y = 0;
                animationTime = 0;
                this.setOrientation("s");
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }*/
}