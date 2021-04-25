package com.poo.engimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class Popup extends Table {
    Label text;
    float width;
    float height;

    Popup(float width, float height) {
        super(new Skin(Gdx.files.internal("skin/golden-ui-skin.json")));
        Texture backgroundColor = new Texture("texture/white_color_texture.png");
        TextureRegionDrawable backgroundColorDrawable = new TextureRegionDrawable(new TextureRegion(backgroundColor)); // ini bisa diganti

        this.setBackground(backgroundColorDrawable);
        this.text = new Label("Hello World", this.getSkin());

        this.add(this.text).expand().align(Align.center).pad(20f);

        this.width = width;
        this.height = height;
    }

    public float getPrefWidth () {
        return this.width;
    }

    public float getPrefHeight () {
        return this.height;
    }
}
