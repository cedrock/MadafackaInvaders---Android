package com.toimemetusaiscestnousquonfaitlesmeilleursjeux.madafackainvaders;

/**
 * Created by c.distelmans on 14-06-15.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.kilobolt.framework.implementation.AccelerometerHandler;

import android.graphics.Color;
import android.graphics.Paint;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;
import com.madaInvGame.Background;
import com.madaInvGame.Madafacka;
import com.madaInvGame.framework.Animation;
import com.madaInvGame.Tile;
import com.madaInvGame.Enemy;
import com.madaInvGame.BasicInvader;
import com.madaInvGame.Bullet;

public class GameScreen extends Screen {
    public static enum GameState {
        Ready, Running, Paused, GameOver
    }

    public static GameState state = GameState.Ready;

    // Variable Setup

    public static Background bg1, bg2;
    public static Madafacka madafacka;

    private Image currentSprite, ship, shipleft1, shipleft2, shipright1, shipright2,
            background, basicInv1, basicInv2;
    private Animation badBoyAnim;

    public ArrayList<Tile> tiles = new ArrayList<Tile>();
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    int livesLeft = 1;
    Paint paint, paint2;

    public GameScreen(Game game) {
        super(game);

        // Initialize game objects here

        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 0);
        madafacka = new Madafacka();

        ship = Assets.ship;
        shipleft1 = Assets.shipleft1;
        shipleft2 = Assets.shipleft2;
        shipright1 = Assets.shipright1;
        shipright2 = Assets.shipright2;

        basicInv1 = Assets.fuckers1;
        basicInv2 = Assets.fuckers2;

        badBoyAnim = new Animation();
        badBoyAnim.addFrame(basicInv1, 800);
        badBoyAnim.addFrame(basicInv2, 800);

        currentSprite = ship;

        loadMap();

        // Defining a paint object
        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        paint2 = new Paint();
        paint2.setTextSize(100);
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.WHITE);

    }

    private void loadMap() {
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        Scanner scanner = new Scanner(MadafackaInvadersGame.map);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // no more lines to read
            if (line == null) {
                break;
            }

            if (!line.startsWith("!")) {
                lines.add(line);
                height = Math.max(height, line.length());
            }
        }
        width = lines.size();

        for (int j = 0; j < height; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    if(Character.getNumericValue(ch) == 5)
                    {
                        Enemy enemy = new BasicInvader(i*40,-j*40);
                        enemies.add(enemy);
                    }
                    else{
                        Tile t = new Tile(i,-j, Character.getNumericValue(ch));
                        tiles.add(t);
                    }
                }

            }
        }

    }

    @Override
    public void update(float deltaTime) {
        List touchEvents = game.getInput().getTouchEvents();

        // We have four separate update methods in this example.
        // Depending on the state of the game, we call different update methods.
        // Refer to Unit 3's code. We did a similar thing without separating the
        // update methods.

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List touchEvents) {

        // This example starts with a "Ready" screen.
        // When the user touches the screen, the game begins.
        // state now becomes GameState.Running.
        // Now the updateRunning() method will be called!

        if (touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List touchEvents, float deltaTime) {
        // This is identical to the update() method from our Unit 2/3 game.

        // 1. All touch input is handled here:
        int len = touchEvents.size();
        updateAccelRunning(deltaTime);
        for (int i = 0; i < len; i++) {
            TouchEvent event = (TouchEvent) touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {

                if (inBounds(event, 15, 50, 450, 700)) {
                    if ( madafacka.readyToFire) {
                        madafacka.shoot();
                        madafacka.readyToFire=false;
                    }
                }
            }

            if (event.type == TouchEvent.TOUCH_UP) {

                if (inBounds(event, 0, 0, 5, 35)) {
                    pause();
                }

                if (inBounds(event, 15, 50, 450, 700)){
                    madafacka.readyToFire=true;
                }
            }

        }

        // 2. Check miscellaneous events like death:
        if (livesLeft == 0) {
            state = GameState.GameOver;
        }

        // 3. Call individual update() methods here.
        // This is where all the game updates happen.
        // For example, robot.update();
        madafacka.update();

        ArrayList<Bullet> bullets = madafacka.bullets;
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = (Bullet) bullets.get(i);
            if (b.visible) {
                b.update();
            } else {
                bullets.remove(i);
            }
        }

        updateTiles();
        for(int i=0; i<enemies.size(); i++){
            ((BasicInvader)enemies.get(i)).update();
        }
        bg1.update();
        bg2.update();
        animate();
    }

    private void updateAccelRunning(float deltaTime) {
        float accelX = AccelerometerHandler.getAccelX();

        if (accelX > .5) {
            // Move left.
            currentSprite=shipleft2;
            madafacka.moveLeft();
            madafacka.movingLeft=true;
        }

        if (accelX < -.5) {
            // Move right.
            currentSprite=shipright2;
            madafacka.moveRight();
            madafacka.movingRight=true;
        }

        if (accelX <= .5 && accelX >= -.5) {
            if(madafacka.movingRight){
                // Move right.
                currentSprite=ship;
                madafacka.stopRight();
            }
            else if(madafacka.movingLeft){
                // Move right.
                currentSprite=ship;
                madafacka.stopLeft();
            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width,
                             int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    private void updatePaused(List touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = (TouchEvent) touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, 0, 800, 240)) {

                    if (!inBounds(event, 0, 0, 35, 35)) {
                        resume();
                    }
                }

                if (inBounds(event, 0, 240, 800, 240)) {
                    nullify();
                    goToMenu();
                }
            }
        }
    }

    private void updateGameOver(List touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = (TouchEvent)touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (inBounds(event, 0, 0, 800, 480)) {
                    nullify();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }

    }

    private void updateTiles() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile t = (Tile) tiles.get(i);
            t.update();
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawImage(Assets.background, bg1.bgX, bg1.bgY);
        g.drawImage(Assets.background, bg2.bgX, bg2.bgY);
        paintTiles(g);

        ArrayList<Bullet> bullets = madafacka.bullets;
        for(int i=0; i<bullets.size(); i++){
            Bullet b = bullets.get(i);
            g.drawRect(b.x, b.y, 5, 10, Color.YELLOW);
        }
        // First draw the game elements.

        g.drawImage(currentSprite, madafacka.centerX-25, madafacka.centerY-25);
        for(int i=0; i<enemies.size(); i++){
            g.drawImage(badBoyAnim.getImage(), ((Enemy)enemies.get(i)).centerX-50, ((Enemy)enemies.get(i)).centerY-50);
        }

        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }

    private void paintTiles(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile t = (Tile) tiles.get(i);
            if (t.type != 0) {
                g.drawImage(t.tileImage, t.tileX, t.tileY);
            }
        }
    }

    public void animate(){
        badBoyAnim.update(50);
    }

    private void nullify() {
        // Set all variables to null. You will be recreating them in the
        // constructor.
        paint = null;
        bg1 = null;
        bg2 = null;
        madafacka = null;
        for(int i=0; i<enemies.size(); i++){
            enemies.remove(i);
            i--;
        }
        currentSprite = null;
        ship = null;
        shipleft1 = null;
        shipleft2 = null;
        shipright1 = null;
        shipright2 = null;
        basicInv1 = null;
        basicInv2 = null;
        badBoyAnim = null;

        // Call garbage collector to clean up memory.
        System.gc();
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap to Start MADAFACKA !!!", 240, 400, paint);

    }

    private void drawRunningUI() {

    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Resume", 220, 350, paint2);
        g.drawString("Menu", 220, 450, paint2);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 400, 240, paint2);
        g.drawString("Tap to return.", 400, 290, paint);

    }

    @Override
    public void pause() {
        if (state == GameState.Running)
            state = GameState.Paused;

    }

    @Override
    public void resume() {
        if (state == GameState.Paused)
            state = GameState.Running;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }

    private void goToMenu() {
        // TODO Auto-generated method stub
        game.setScreen(new MainMenuScreen(game));

    }
}