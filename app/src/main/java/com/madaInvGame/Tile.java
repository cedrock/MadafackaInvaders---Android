package com.madaInvGame;

import com.kilobolt.framework.Image;
import android.graphics.Rect;
import com.toimemetusaiscestnousquonfaitlesmeilleursjeux.madafackainvaders.Assets;
import com.toimemetusaiscestnousquonfaitlesmeilleursjeux.madafackainvaders.GameScreen;

public class Tile {

	public int tileX, tileY, speedY, type;
	public Image tileImage;
	
	public Madafacka madafacka = GameScreen.madafacka;
	public Background bg = GameScreen.bg1;
	public Rect box;

	public Tile(int x, int y, int typeInt) {
		// TODO Auto-generated constructor stub
		tileX = x*40;
		tileY = y*40;
		type = typeInt;
		box = new Rect();
		
		switch (type){
		case 1:
			tileImage = Assets.redGround;
			break;
		case 2:
			tileImage = Assets.purpleGround;
			break;
			
		default:
			type = 0;
			break;
		}
	}

	public void update(){
		speedY = bg.speedY*5;
		tileY += speedY;
		
		box.set(tileX, tileY, tileX+40, tileY+40);
		
		if(Rect.intersects(box,Madafacka.hitZone) && type!=0){
			checkCollision(Madafacka.box);
		}
		for(int i =0; i<GameScreen.madafacka.bullets.size(); i++){
			if(Rect.intersects(box,GameScreen.madafacka.bullets.get(i).hitZone) && type!=0)
			{
				checkBulletCollision(GameScreen.madafacka.bullets.get(i));
			}
		}
	}
	
	public void checkCollision(Rect r){
		if(Rect.intersects(box,r)){
            GameScreen.state = GameScreen.GameState.GameOver;
		}
	}
	
	public void checkBulletCollision(Bullet b){
		if(Rect.intersects(box,b.box)){
			b.visible=false;
		}
	}
}
