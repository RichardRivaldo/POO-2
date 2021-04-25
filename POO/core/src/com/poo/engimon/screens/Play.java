package com.poo.engimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.poo.engimon.entities.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    // enemylist
    private EnemyList enemyList;
    // Player Atlas
    private TextureAtlas playerAtlas;
    // PopUp Table Stage
    public Stage uiStage;
    public Table root;
    public Popup uiPopup;
    public TextField text;
    public TextField text2;
    public TextField text3;
    public String lastCommand;

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

        // UI Act
        this.uiStage.act(delta);

        this.renderer.getBatch().end();

        // Draw UI
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

        Random rand = new Random();
        int random = rand.nextInt(5);
        ArrayList<Skill> fireSkills = Skills.fireSkills();
        ArrayList<Skill> waterSkills = Skills.waterSkills();
        ArrayList<Skill> electricSkills = Skills.electricSkills();
        ArrayList<Skill> iceSkills = Skills.iceSkills();
        ArrayList<Skill> groundSkills = Skills.groundSkills();
        Engimon starterEngimon;

        if(random == 0){
            starterEngimon = new Engimon("My Engi", "Firemon", new ArrayList<String>(Arrays.asList("FIRE")));
            starterEngimon.addSkill(fireSkills.get(0));
        }
        else if(random == 1){
            starterEngimon = new Engimon("My Engi", "Watermon", new ArrayList<String>(Arrays.asList("WATER")));
            starterEngimon.addSkill(waterSkills.get(0));
        }
        else if(random == 2){
            starterEngimon = new Engimon("My Engi", "Electromon", new ArrayList<String>(Arrays.asList("ELECTRIC")));
            starterEngimon.addSkill(electricSkills.get(0));
        }
        else if(random == 3){
            starterEngimon = new Engimon("My Engi", "Icemon", new ArrayList<String>(Arrays.asList("ICE")));
            starterEngimon.addSkill(iceSkills.get(0));
        }
        else{
            starterEngimon = new Engimon("My Engi", "Groundmon", new ArrayList<String>(Arrays.asList("GROUND")));
            starterEngimon.addSkill(groundSkills.get(0));
        }

        // Render and set the player
        this.player = new Player(s, a, w, d, (TiledMapTileLayer) this.map.getLayers().get(0), 10, 31, this, starterEngimon);
        this.enemyList = new EnemyList(10,this.map, this.renderer, this.camera);
        Gdx.input.setInputProcessor(this.player);

        // Start Pop Up
        this.initPopUp();
    }

    // Init Pop Up
    public void initPopUp() {
        this.uiStage = new Stage(new ScreenViewport());
        this.uiStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.root = new Table();
        this.root.setFillParent(true);
        this.uiStage.addActor(this.root);

        this.uiPopup = new Popup(40, 50);
        this.root.add(this.uiPopup).expand().align(Align.center).pad(10f);
        this.root.row();

        this.text = new TextField("", this.uiPopup.getSkin());
        this.text2 = new TextField("", this.uiPopup.getSkin());
        this.text3 = new TextField("", this.uiPopup.getSkin());

        this.root.add(text);
        this.root.row();
        this.root.add(text2);
        this.root.row();
        this.root.add(text3);
        this.root.row();

        text.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField text, char key) {
                if ((key == '\r' || key == '\n')){
                    if(lastCommand.equals("r")){
                        player.getActiveEngimon().changeName(text.getText());
                    }
                    else if(lastCommand.equals("p")){
                        uiPopup.setVisible(!uiPopup.isVisible());
                        uiPopup.setText(player.showEngiInfo(text.getText()));
                    }
                    else if(lastCommand.equals("c")){
                        String msg = player.swapActiveEngimon(text.getText());
                        uiPopup.setVisible(!uiPopup.isVisible());
                        uiPopup.setText(msg);
                    }
                    text.setVisible(false);
                    Gdx.input.setInputProcessor(player);
                    text.setText("");
                }
            }
        });
        text2.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField text2, char key) {
                if ((key == '\r' || key == '\n')){
                    if(lastCommand.equals("l")){
                        String msg = player.learnNewSkill(text.getText(), text2.getText());
                        uiPopup.setVisible(!uiPopup.isVisible());
                        uiPopup.setText(msg);
                    }
                    text.setVisible(false);
                    text2.setVisible(false);
                    Gdx.input.setInputProcessor(player);
                    text.setText("");
                    text2.setText("");
                }
            }
        });
        text3.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField text3, char key) {
                if ((key == '\r' || key == '\n')){
                    if(lastCommand.equals("b")){
                        String msg = player.breed(text.getText(), text2.getText(), text3.getText());
                        uiPopup.setVisible(!uiPopup.isVisible());
                        uiPopup.setText(msg);
                    }
                    text.setVisible(false);
                    text2.setVisible(false);
                    text3.setVisible(false);
                    Gdx.input.setInputProcessor(player);
                    text.setText("");
                    text2.setText("");
                    text3.setText("");
                }
            }
        });

        this.text.setVisible(false);
        this.text2.setVisible(false);
        this.text3.setVisible(false);
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
        uiStage.dispose();
    }
}
