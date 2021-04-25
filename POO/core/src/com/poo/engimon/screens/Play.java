package com.poo.engimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.poo.engimon.entities.Enemy;
import com.poo.engimon.entities.EnemyList;
import com.poo.engimon.entities.Player;

import java.util.Random;

public class Play implements Screen {
    // Tiled Map
    private TiledMap map;

    // Renderer
    private OrthogonalTiledMapRenderer renderer;

    // Camera
    private OrthographicCamera camera;

    // Player
    private Player player;

    //enemylist
    private EnemyList enemyList;

    // Player Atlas
    private TextureAtlas playerAtlas;

    private Stage uiStage;
    private Table root;
    public Popup uiPopup;

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Camera View
        this.camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        this.camera.update();
        this.renderer.setView(camera);
        this.renderer.render();

        this.renderer.getBatch().begin();
        this.player.draw(renderer.getBatch());
        if (enemyList != null){
            Random random = new Random();
            int peluang = random.nextInt(100);
            if(peluang>98){
                enemyList.addEnemy();
            }
            if(enemyList.getEnemylist().size > 0){
                for (Enemy satuenemy: enemyList.getEnemylist()) {
                    satuenemy.draw(renderer.getBatch());
                }
            }
        }

        this.uiStage.act(delta);
        this.renderer.getBatch().end();
        this.uiStage.draw();
    }

    @Override
    public void resize(int width, int height){
        this.camera.viewportWidth = width / 1.2f;
        this.camera.viewportHeight = height / 1.2f;
    }

    @Override
    public void show(){
        // Load, render, and set the camera of the map
        this.map = new TmxMapLoader().load("map/Map.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(this.map);
        this.camera = new OrthographicCamera();
        // Player Atlas
        this.playerAtlas = new TextureAtlas("entities/player.pack");
        // Animations
        Animation<TextureRegion> s, a, w, d;
        s = new Animation(1/10f, playerAtlas.findRegions("s"));
        a = new Animation(1/10f, playerAtlas.findRegions("a"));
        w = new Animation(1/10f, playerAtlas.findRegions("w"));
        d = new Animation(1/10f, playerAtlas.findRegions("d"));
        s.setPlayMode(Animation.PlayMode.LOOP);
        a.setPlayMode(Animation.PlayMode.LOOP);
        w.setPlayMode(Animation.PlayMode.LOOP);
        d.setPlayMode(Animation.PlayMode.LOOP);

        // Render and set the player
        this.player = new Player(s, a, w, d, (TiledMapTileLayer) this.map.getLayers().get(0), 10, 31, this);
        this.enemyList = new EnemyList(10,this.map, this.renderer, this.camera);
        Gdx.input.setInputProcessor(this.player);

        this.initPopUp();
    }

    public void initPopUp() {
        this.uiStage = new Stage(new ScreenViewport());
        this.uiStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.root = new Table();
        this.root.setFillParent(true);

        this.uiStage.addActor(this.root);

        this.uiPopup = new Popup(300f, 500f);
        this.root.add(this.uiPopup).expand().align(Align.center).pad(10f);

        this.uiPopup.setVisible(false);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        playerAtlas.dispose();
    }
}
