package com.poo.engimon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

public class Option extends Table {
    private int selectedIdx = 0;
    private List<Image> arrows = new ArrayList<Image>();
    private List<Label> options = new ArrayList<Label>();

    private Table uiContainer;

    Option(float width, float height) {
        super(new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json")));

        Texture backgroundColor = new Texture("texture/white_color_texture.png");

        TextureRegionDrawable backgroundColorDrawable = new TextureRegionDrawable(new TextureRegion(backgroundColor));
        this.setBackground(backgroundColorDrawable);

        this.uiContainer = new Table();
        this.add(uiContainer).pad(5f);
    }

    public void addOption(String newOption) {
        Label optionLabel = new Label(newOption, this.getSkin());

        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("skin/raw/touchpad.png"))));
        Image arrow = new Image(backgroundDrawable);
        arrow.setScale(0.5f, 0.5f);
        if(this.arrows.size() == 0){
            arrow.setVisible(true);
        }else{
            arrow.setVisible(false);
        }
        this.arrows.add(arrow);
        this.options.add(optionLabel);

        this.uiContainer.add(arrow).expand().align(Align.left).pad(5f);
        this.uiContainer.add(optionLabel).expand().align(Align.left).space(5f);
        this.uiContainer.row();
    }

    public void moveUp() {
        this.selectedIdx--;
        if(this.selectedIdx < 0)
            this.selectedIdx = 0;
        this.changeArrow();
    }

    public void moveDown() {
        this.selectedIdx++;
        if(this.selectedIdx >= this.arrows.size())
            this.selectedIdx = this.arrows.size() - 1;
        this.changeArrow();
    }

    public int getSelectedOptionIndex() {
        return this.selectedIdx;
    }

    public void resetOption() {
        this.uiContainer.clearChildren();
        this.arrows.clear();
        this.options.clear();
        this.selectedIdx = 0;
    }

    public void changeArrow() {
        int i = 0;
        while(i < this.arrows.size()){
            if(i == this.selectedIdx)
                this.arrows.get(i).setVisible(true);
            else
                this.arrows.get(i).setVisible(false);
            i++;
        }
    }
}