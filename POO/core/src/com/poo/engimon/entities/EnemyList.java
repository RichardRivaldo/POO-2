package com.poo.engimon.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    private TextureAtlas enemyAtlas;
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
        this.enemylist = new Array<>();
        this.enemyAtlas = null;
        /*enemylist.add(new Enemy(s, a, w, d,
                (TiledMapTileLayer) map.getLayers().get(0), 21, 30));
        enemylist.add(new Enemy(s, a, w, d,
                (TiledMapTileLayer) map.getLayers().get(0), 30, 40));
        */
        addEnemy();
    }

    private enum EngimonLiarName
    {
        Omnimon,
        Skull,
        Greymon,
        Piedmon,
        War_Greymon,
        MagnaAngemon,
        Garurumon,
        Devimon,
        Apocalypmon,
        Etemon,
        Agumon;

        /**
         * Pick a random value of the EngimonLiarName enum.
         * @return a random EngimonLiarName.
         */
        public static EngimonLiarName getRandomName() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    public void addEnemy(){
        if (enemylist.size < maxEnemy) {
            Random randomx = new Random();
            Random randomy = new Random();
            boolean found = false;
            boolean collide = false;
            int coorX = randomx.nextInt(48)+1;
            int coorY = randomy.nextInt(48)+1;
            String nama = EngimonLiarName.getRandomName().toString();
            Skill skillnya = new Skill();
            String spesies = "";
            switch (Skill.randomElements().get(0)) {
                case "WATER": {
                    this.enemyAtlas = new TextureAtlas("entities/water.pack");
                    Random randomskillwater = new Random();
                    int indexnya = randomskillwater.nextInt(Skills.waterSkills().size());
                    skillnya = Skills.waterSkills().get(indexnya);
                    spesies = "Watermon";
                    break;
                }
                case "FIRE": {
                    this.enemyAtlas = new TextureAtlas("entities/fire.pack");
                    Random randomskillfire = new Random();
                    int indexnya = randomskillfire.nextInt(Skills.fireSkills().size());
                    skillnya = Skills.fireSkills().get(indexnya);
                    spesies = "Firemon";
                    break;
                }
                case "ELECTRIC": {
                    this.enemyAtlas = new TextureAtlas("entities/electric.pack");
                    Random randomskillelectric = new Random();
                    int indexnya = randomskillelectric.nextInt(Skills.electricSkills().size());
                    skillnya = Skills.electricSkills().get(indexnya);
                    spesies = "Electromon";
                    break;
                }
                case "GROUND": {
                    this.enemyAtlas = new TextureAtlas("entities/ground.pack");
                    Random randomskillground = new Random();
                    int indexnya = randomskillground.nextInt(Skills.groundSkills().size());
                    skillnya = Skills.groundSkills().get(indexnya);
                    spesies = "Groundmon";
                    break;
                }
                case "ICE": {
                    this.enemyAtlas = new TextureAtlas("entities/ice.pack");
                    Random randomskillice = new Random();
                    int indexnya = randomskillice.nextInt(Skills.iceSkills().size());
                    skillnya = Skills.iceSkills().get(indexnya);
                    spesies = "Icemon";
                    break;
                }
            }
            Engimon engimon = new Engimon(nama, spesies, Skill.randomElements());
            engimon.setLife(1);
            engimon.addSkill(skillnya);
            this.s = new Animation<TextureRegion>(1/10f, this.enemyAtlas.findRegions("s"));
            this.a = new Animation<TextureRegion>(1/10f, this.enemyAtlas.findRegions("a"));
            this.w = new Animation<TextureRegion>(1/10f, this.enemyAtlas.findRegions("w"));
            this.d = new Animation<TextureRegion>(1/10f, this.enemyAtlas.findRegions("d"));
            this.s.setPlayMode(Animation.PlayMode.LOOP);
            this.a.setPlayMode(Animation.PlayMode.LOOP);
            this.w.setPlayMode(Animation.PlayMode.LOOP);
            this.d.setPlayMode(Animation.PlayMode.LOOP);
            enemylist.add(new Enemy(this.s, this.a, this.w, this.d,
                    (TiledMapTileLayer) map.getLayers().get(0), coorX, coorY, engimon));
        }
    }
    public Enemy getEnemyTerdekat(float x_player, float y_player){
        Enemy enemynya = null;
        for (Enemy enemy: new Array.ArrayIterator<>(enemylist)) {
            if (((Math.abs(x_player - enemy.xpos())<=50)) && (Math.abs(y_player-enemy.ypos())<=50)){
                enemynya = enemy;
            }
        }
        return enemynya;
        //return enemylist.first();
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
    public void removeEnemy(){
        for (int i = 0; i < enemylist.size; i++) {
            if (enemylist.get(i).getEngimonLiar().getLife()<=0){
                enemylist.removeIndex(i);
            }
        }
    }
}
