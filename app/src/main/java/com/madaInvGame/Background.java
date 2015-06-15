package com.madaInvGame;

public class Background {
	
	public int bgX,bgY,speedY;

	public Background(int x, int y){
		bgX=x;
		bgY=y;
		speedY=0;
	}
	
	public void update(){
		bgY += speedY;
		
		if(bgY <= -2160){
			bgY += 4320;
		}
	}
}
