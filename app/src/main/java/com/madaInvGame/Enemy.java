package com.madaInvGame;

import android.graphics.Rect;

import com.toimemetusaiscestnousquonfaitlesmeilleursjeux.madafackainvaders.GameScreen;

public class Enemy {

	public int power, speedY, centerX, centerY, sizeX, sizeY;
	public Background bg = GameScreen.bg1;
	public Rect box = new Rect(0,0,0,0);
	public int health;
	public boolean dead=false;
	
	public Enemy(){
		sizeX=50;
		sizeY=50;
		health=5;
	}
	
	public void update(){
		this.speedY = bg.speedY*3;
		centerY += speedY;
		
		box.set(centerX-sizeX/2, centerY-sizeY/2, centerX+sizeX/2, centerY+sizeY/2);
		if(Rect.intersects(box,Madafacka.hitZone)){
			checkCollision();
		}
	}
	
	public void checkCollision(){
		if(Rect.intersects(box,Madafacka.box)){
			GameScreen.state = GameScreen.GameState.GameOver;
		}
	}
	
	public void die(){
		
	}
	
	public void attack(){
		
	}
}
