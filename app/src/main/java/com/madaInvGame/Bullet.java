package com.madaInvGame;

import android.graphics.Rect;

import com.toimemetusaiscestnousquonfaitlesmeilleursjeux.madafackainvaders.GameScreen;

public class Bullet {
	
	public int x, y, speedY;
	public boolean visible;
	public Rect box = new Rect(0,0,0,0);
	public Rect hitZone = new Rect(0,0,0,0);

	public Bullet(int startX, int startY) {
		x= startX;
		y = startY;
		speedY=-10;
		visible=true;
	}

	public void update(){
		y += speedY;
		box.set(x,y,x+5,y+10);
		hitZone.set(x, y, x+30, y+30);
		if(y<0){
			visible =false;
			box=null;
		}
		if(y>0){
			checkCollision();
		}
	}
	
	private void checkCollision() {
		for(int i=0; i< GameScreen.enemies.size(); i++){
			BasicInvader enemy = (BasicInvader)GameScreen.enemies.get(i);
			if(Rect.intersects(box,enemy.box)){
				visible=false;
				
				if(enemy.health > 0){
					enemy.health -= 1;
				}
				
				if(enemy.health<=0){
					enemy.dead=true;
                    GameScreen.enemies.remove(i);
				}
			}
		}
	}
}
