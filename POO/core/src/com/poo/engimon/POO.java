package com.poo.engimon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.poo.engimon.screens.Play;
import com.poo.engimon.util.SkinGenerator;
import com.poo.engimon.worldloader.DialogueDb;
import com.poo.engimon.worldloader.DialogueLoader;

import java.io.File;

public class POO extends Game {
	private AssetManager assetManager;
	private Skin skin;
	@Override
	public void create () {
		this.assetManager = new AssetManager();
		//assetManager.setLoader(LWorldObjectDb.class, new LWorldObjectLoader(new InternalFileHandleResolver()));
		//assetManager.setLoader(LTerrainDb.class, new LTerrainLoader(new InternalFileHandleResolver()));
		this.assetManager.setLoader(DialogueDb.class, new DialogueLoader(new InternalFileHandleResolver()));
		//assetManager.setLoader(World.class, new WorldLoader(new InternalFileHandleResolver()));

		//assetManager.load("res/LTerrain.xml", LTerrainDb.class);
		//assetManager.load("res/LWorldObjects.xml", LWorldObjectDb.class);
		this.assetManager.load("res/Dialogues.xml", DialogueDb.class);

		//assetManager.load("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		this.assetManager.load("res/graphics_packed/ui/uipack.atlas", TextureAtlas.class);
		//assetManager.load("res/graphics_packed/battle/battlepack.atlas", TextureAtlas.class);
		this.assetManager.load("res/graphics/pokemon/bulbasaur.png", Texture.class);
		this.assetManager.load("res/graphics/pokemon/slowpoke.png", Texture.class);
		this.assetManager.load("res/font/small_letters_font.fnt", BitmapFont.class);

		File dir = new File("res/worlds/");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				System.out.println("Loading world "+child.getPath());
				this.assetManager.load(child.getPath(), World.class);
			}
		}
		this.assetManager.finishLoading();
		this.skin = SkinGenerator.generateSkin(assetManager);
		setScreen(new Play(this));
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void render () {
		super.render();
	}

	public AssetManager getAssetManager() {
		return this.assetManager;
	}

	public Skin getSkin() {
		return this.skin;
	}



	@Override
	public void resize(int width, int height){
		super.resize(width, height);
	}

	@Override
	public void pause(){
		super.pause();
	}

	@Override
	public void resume(){
		super.resume();
	}

}
