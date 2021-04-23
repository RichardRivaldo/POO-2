package com.poo.engimon.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class EnemyList {
    private Array<Enemy> enemylist;
    private int maxEnemy;
    public int getMaxEnemy() { return maxEnemy; }
    public void setMaxEnemy(int maxEnemy) { this.maxEnemy = maxEnemy; }
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    // Player Atlas
    private TextureAtlas playerAtlas;
    // Player Atlas Animation
    private Animation<TextureRegion> s, a, w, d;
    // Layer of blocks with collisions
    private TiledMapTileLayer collisionLayer;

    public Array<Enemy> getEnemylist() {
        return enemylist;
    }
    public void setEnemylist(Array<Enemy> enemylist) {
        this.enemylist = enemylist;
    }

    public EnemyList(int maxEnemy, TiledMap map, OrthogonalTiledMapRenderer renderer, OrthographicCamera camera){
        this.map = map;
        this.renderer = renderer;
        this.camera = camera;
        this.maxEnemy = maxEnemy;
        this.playerAtlas = new TextureAtlas("entities/player.pack");
        this.s = new Animation(1/10f, playerAtlas.findRegions("s"));
        this.a = new Animation(1/10f, playerAtlas.findRegions("a"));
        this.w = new Animation(1/10f, playerAtlas.findRegions("w"));
        this.d = new Animation(1/10f, playerAtlas.findRegions("d"));
        this.s.setPlayMode(Animation.PlayMode.LOOP);
        this.a.setPlayMode(Animation.PlayMode.LOOP);
        this.w.setPlayMode(Animation.PlayMode.LOOP);
        this.d.setPlayMode(Animation.PlayMode.LOOP);
        this.enemylist = new Array<Enemy>();
        /*enemylist.add(new Enemy(s, a, w, d,
                (TiledMapTileLayer) map.getLayers().get(0), 21, 30));
        enemylist.add(new Enemy(s, a, w, d,
                (TiledMapTileLayer) map.getLayers().get(0), 30, 40));
        */
        addEnemy();
    }

    public void addEnemy(){
        if (enemylist.size < maxEnemy) {
            Random randomx = new Random();
            Random randomy = new Random();
            int coorX = 0;
            int coorY = 0;
            boolean found = false;
            boolean collide = false;
            coorX = randomx.nextInt(50);
            coorY = randomy.nextInt(50);
            enemylist.add(new Enemy(s, a, w, d,
                    (TiledMapTileLayer) map.getLayers().get(0), coorX, coorY));
            /*enemylist.add(new Enemy(s, a, w, d,
                    (TiledMapTileLayer) map.getLayers().get(0), 30, 40));*/
        }
    }
    /*public void addEnemy(){
        if (enemylist.size < maxEnemy){
            Random random = new Random();
            int coorX = 0;
            int coorY = 0;
            boolean found = false;
            boolean collide = false;
            while(!found){
                coorX = random.nextInt(50);
                coorY = random.nextInt(50);
                //collide = isCollision((float)coorX, (float)coorY);
                collide = true;
                if(!collide){
                    found = true;
                    enemylist.add(new Enemy(this.s, this.a, this.w, this.d,
                            (TiledMapTileLayer) this.map.getLayers().get(0), coorX, coorY));
                }
            }
        }
    }*/
}
