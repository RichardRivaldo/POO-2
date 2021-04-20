package com.poo.engimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Play implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.renderer.setView(camera);
        this.renderer.render();
    }

    @Override
    public void resize(int width, int height){
        this.camera.viewportWidth = width;
        this.camera.viewportHeight = height;
        this.camera.position.set(width/2f, height/2f, 0);
        // this.camera.translate(150, 135);
        this.camera.update();
    }

    @Override
    public void show(){
        this.map = new TmxMapLoader().load("map/Map.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(this.map);
        this.camera = new OrthographicCamera();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
