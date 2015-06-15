package com.madaInvGame;

import android.graphics.Rect;

import com.toimemetusaiscestnousquonfaitlesmeilleursjeux.madafackainvaders.GameScreen;

import java.util.ArrayList;

public class Madafacka {
	// In Java, Class Variables should be private so that only its methods can
	// change them.
	final int moveSpeed = -7;
	
	public int centerX = 240;
	public int centerY = 650;
	public boolean movingLeft=false;
	public boolean movingRight = false;
	public boolean shooting=false;
	public boolean readyToFire = true;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public static Background bg1 = GameScreen.bg1;
	public static Background bg2 = GameScreen.bg2;

	public int speedX = 0;
	public int speedY = moveSpeed;
	public static Rect box = new Rect(0,0,0,0);
	public static Rect hitZone = new Rect(0,0,0,0);
	
	public void update() {

		// Moves Character or Scrolls Background accordingly.
		if (speedX != 0) {
			centerX += speedX;
		} 
		if (speedY == 0 || speedY<0) {
			bg1.speedY = 0;
			bg2.speedY=0;

		} 

		if (speedY<0){
			bg1.speedY = -moveSpeed/5;
			bg2.speedY = -moveSpeed/5;
		}
		
		box.set(centerX-20, centerY-20, centerX+20, centerY+20);
		hitZone.set(centerX-80, centerY-80, centerX+80, centerY+80);
		
	}

	public void moveRight() {
		speedX = -moveSpeed;
	}

	public void moveLeft() {
		speedX = moveSpeed;
	}

	public void stopRight() {
		movingRight = false;
		stop();
	}
	
	public void stopLeft() {
		movingLeft = false;
		stop();
	}

	public void stop(){
		if(!movingRight && !movingLeft){
			speedX=0;
		}
		else if (movingRight && !movingLeft){
			moveRight();
		}
		else if (!movingRight && movingLeft){
			moveLeft();
		}
	}
	
	
	public void shoot(){
		if(readyToFire){
			shooting=true;
			Bullet b = new Bullet(centerX, centerY-25);
			bullets.add(b);
		}
	}
}
