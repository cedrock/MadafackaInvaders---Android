package com.toimemetusaiscestnousquonfaitlesmeilleursjeux.madafackainvaders;

/**
 * Created by c.distelmans on 14-06-15.
 */
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Screen;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {

        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.menu = g.newImage("images.jpg", ImageFormat.RGB565);
        Assets.background = g.newImage("background.png", ImageFormat.RGB565);
        Assets.ship = g.newImage("ship.png", ImageFormat.ARGB4444);
        Assets.shipleft1 = g.newImage("shipleft1.png", ImageFormat.ARGB4444);
        Assets.shipleft2  = g.newImage("shipleft2.png", ImageFormat.ARGB4444);
        Assets.shipright1 = g.newImage("shipright1.png", ImageFormat.ARGB4444);
        Assets.shipright2 = g.newImage("shipright2.png", ImageFormat.ARGB4444);

        Assets.fuckers1 = g.newImage("fuckers1.png", ImageFormat.ARGB4444);
        Assets.fuckers2 = g.newImage("fuckers2.png", ImageFormat.ARGB4444);

        Assets.purpleGround = g.newImage("purpleground.png", ImageFormat.RGB565);
        Assets.redGround = g.newImage("redground.png", ImageFormat.RGB565);

        Assets.button = g.newImage("button.jpg", ImageFormat.RGB565);

        //This is how you would load a sound if you had one.
        //Assets.click = game.getAudio().createSound("explode.ogg");


        game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.splash, 0, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}