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
        this.enemylist = new Array<Enemy>();
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

    public enum EngimonLiarSpesies
    {
        Firemon,
        Watermon,
        Electromon,
        Groundmon,
        Icemon;

        /**
         * Pick a random value of the EngimonLiarSpecses enum.
         * @return a random EngimonLiarSpesies.
         */
        public static EngimonLiarSpesies getRandomSpesies() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    public void addEnemy(){
        if (enemylist.size < maxEnemy) {
            Random randomx = new Random();
            Random randomy = new Random();
            int coorX = 0;
            int coorY = 0;
            boolean found = false;
            boolean collide = false;
            coorX = randomx.nextInt(48)+1;
            coorY = randomy.nextInt(48)+1;
            String nama = EngimonLiarName.getRandomName().toString();
            String spesies = EngimonLiarSpesies.getRandomSpesies().toString();
            Engimon engimon = new Engimon(nama, spesies, Skill.randomElements());
            engimon.setLife(1);
            Skill skillnya = new Skill();
            if(Skill.randomElements().get(0)=="WATER"){
                this.enemyAtlas = new TextureAtlas("entities/water.pack");
                Random randomskillwater = new Random();
                int indexnya = randomskillwater.nextInt(Skills.waterSkills().size());
                skillnya = Skills.waterSkills().get(indexnya);
            }else if(Skill.randomElements().get(0)=="FIRE"){
                this.enemyAtlas = new TextureAtlas("entities/fire.pack");
                Random randomskillfire = new Random();
                int indexnya = randomskillfire.nextInt(Skills.fireSkills().size());
                skillnya = Skills.fireSkills().get(indexnya);
            }else if(Skill.randomElements().get(0)=="ELECTRIC"){
                this.enemyAtlas = new TextureAtlas("entities/electric.pack");
                Random randomskillelectric = new Random();
                int indexnya = randomskillelectric.nextInt(Skills.electricSkills().size());
                skillnya = Skills.electricSkills().get(indexnya);
            }else if(Skill.randomElements().get(0)=="GROUND"){
                this.enemyAtlas = new TextureAtlas("entities/ground.pack");
                Random randomskillground = new Random();
                int indexnya = randomskillground.nextInt(Skills.groundSkills().size());
                skillnya = Skills.groundSkills().get(indexnya);
            }else if(Skill.randomElements().get(0)=="ICE"){
                this.enemyAtlas = new TextureAtlas("entities/player.pack");
                Random randomskillice = new Random();
                int indexnya = randomskillice.nextInt(Skills.iceSkills().size());
                skillnya = Skills.iceSkills().get(indexnya);
            }
            engimon.addSkill(skillnya);
            this.s = new Animation(1/10f, enemyAtlas.findRegions("s"));
            this.a = new Animation(1/10f, enemyAtlas.findRegions("a"));
            this.w = new Animation(1/10f, enemyAtlas.findRegions("w"));
            this.d = new Animation(1/10f, enemyAtlas.findRegions("d"));
            this.s.setPlayMode(Animation.PlayMode.LOOP);
            this.a.setPlayMode(Animation.PlayMode.LOOP);
            this.w.setPlayMode(Animation.PlayMode.LOOP);
            this.d.setPlayMode(Animation.PlayMode.LOOP);
            enemylist.add(new Enemy(s, a, w, d,
                    (TiledMapTileLayer) map.getLayers().get(0), coorX, coorY, engimon));
            /*enemylist.add(new Enemy(s, a, w, d,
                    (TiledMapTileLayer) map.getLayers().get(0), 30, 40));*/
        }
    }
    public Enemy getEnemyTerdekat(float x_player, float y_player){
        /*Enemy enemynya = null;
        for (Enemy enemy: new Array.ArrayIterator<>(enemylist)) {
            if (((Math.abs(x_player - enemy.xpos())<=5)) && (Math.abs(y_player-enemy.ypos())<=5)){
                enemynya = enemy;
            }
        }
        return enemynya;*/
        return enemylist.first();
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
