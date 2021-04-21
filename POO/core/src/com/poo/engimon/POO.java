package com.poo.engimon;

import com.badlogic.gdx.Game;
import com.poo.engimon.screens.Play;

public class POO extends Game {

	@Override
	public void create () {
		setScreen(new Play());
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void render () {
		super.render();
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
