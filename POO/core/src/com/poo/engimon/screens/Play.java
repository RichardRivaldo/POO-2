package com.poo.engimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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
import com.poo.engimon.POO;
import com.poo.engimon.controller.OptionBoxController;
import com.poo.engimon.controller.PlayerController;
import com.poo.engimon.entities.Enemy;
import com.poo.engimon.entities.EnemyList;
import com.poo.engimon.entities.Player;
import com.poo.engimon.ui.DialogueBox;
import com.poo.engimon.ui.OptionBox;
import com.poo.engimon.util.SkinGenerator;

import java.util.Random;

public class Play implements Screen {
    private POO app;

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
    //Stage
    private Stage uiStage;
    private int uiScale = 2;
    //Table
    private Table root;
    //private DialogueBox
    public AssetManager assetManager;
    //Option box
    private OptionBox optionBox;
    //option controller
    private OptionBoxController optionsController;
    //multiplexer
    private InputMultiplexer multiplexer;
    public Play(POO poo) {
        this.app = poo;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Camera View
        this.camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        this.camera.update();
        this.renderer.setView(camera);
        this.renderer.render();
        this.uiStage.act(delta);

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
        //this.enemy.draw(renderer.getBatch());
        this.renderer.getBatch().end();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height){
        this.camera.viewportWidth = width / 1.2f;
        this.camera.viewportHeight = height / 1.2f;
        // this.camera.position.set(width/2f, height/2f, 0);
        // this.camera.translate(150, 135);
        // this.camera.update();
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
        this.player = new Player(s, a, w, d,
                (TiledMapTileLayer) this.map.getLayers().get(0), 10, 31);
        this.enemyList = new EnemyList(10,this.map, this.renderer, this.camera);

        initUI();

        this.multiplexer = new InputMultiplexer();

        PlayerController playerController = new PlayerController(player);
        this.optionsController = new OptionBoxController(optionBox);
        this.multiplexer.addProcessor(playerController);
        this.multiplexer.addProcessor(optionsController);
        Gdx.input.setInputProcessor(this.player);
    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport());

        root = new Table();
        root.setFillParent(true);
        uiStage.addActor(root);

        Table dialogTable = new Table();

        this.optionBox = new OptionBox(getApp().getSkin());
        optionBox.addOption("A");
        optionBox.addOption("B");
        optionBox.addOption("C");

        dialogTable.add(optionBox);

        DialogueBox dialogueBox = new DialogueBox(getApp().getSkin());
        //dialogueBox.setText("This is a test String");
        dialogueBox.setText("This is a test String");
        root.add(dialogueBox).expand().align(Align.bottom).pad(8f);
        root.add(dialogTable).expand().align(Align.bottom).pad(8f);

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
    public POO getApp() {
        return app;
    }
}
