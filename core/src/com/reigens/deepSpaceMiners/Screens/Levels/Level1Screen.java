package com.reigens.deepSpaceMiners.Screens.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.reigens.deepSpaceMiners.Assets.Graphics;
import com.reigens.deepSpaceMiners.GameMain;
import com.reigens.deepSpaceMiners.Helper;
import com.reigens.deepSpaceMiners.Meteors.RegularMeteor;
import com.reigens.deepSpaceMiners.Ships.Ship1;
import com.reigens.deepSpaceMiners.Ships.WormHole;

/**
 * Created by Richard Reigens on 9/6/2014.
 */

public class Level1Screen implements Screen {
    GameMain game;
    OrthographicCamera camera;
    float stateTime;
    public static SpriteBatch batch;
    public int shipX = 960;
    public int shipY = 540;
    Vector3 touch;
    BitmapFont blackFont, redFont;
    WormHole wormHole;
    Ship1 ship;
    boolean gameState = true;

    RegularMeteor regularMeteor;
    public static Array<Rectangle> smallRegMeteors;
    int meteorsGathered;
    int meteorsMissed;
    long speedTime = TimeUtils.millis();
    public static long lastDropTime;

    //Changeable variables
    int shipSizeX = 256, shipSizeY = 256;// Ship Size
    int startingHull = 100;// Default Hull integrity to reset to
    int shipHull = 100;// Hull integrity Total - set same as startingHull but this value changes while playing
    int startingSpeed = 100;// Default Starting speed to reset to "same as fall speed"
    int fallSpeed = 100;// Starting Speed - Set same as startingSpeed, but this value changes while playing.
    int maxSpeed = 500;// Cap on meteor speed - higher is faster
    int speedRate = 500;// Rate of speed increase - higher is slower increase over time
    int meteorRate = 500;// Time in between meteors - higher is less meteors
    int meteorsToWin = 50;// Number of meteors required to win

    public Level1Screen(GameMain game) {
        this.game = game;


        //Initialize Variables and any other "run once" commands
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        Graphics.loadLevel1();
        Graphics.loadShip();
        Graphics.loadMeteor();
        blackFont = new BitmapFont(Gdx.files.internal("font/black14.fnt"));
        redFont = new BitmapFont(Gdx.files.internal("font/red14.fnt"));
        stateTime = 0f;
        batch = new SpriteBatch();
        wormHole = new WormHole();
        ship = new Ship1();
        touch = new Vector3();
        regularMeteor = new RegularMeteor();
        smallRegMeteors = new Array<Rectangle>();
        //  RegularMeteor.spawnMeteor();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        stateTime += Gdx.graphics.getDeltaTime();
        generalUpdate(touch, camera);

        //Initialize graphics
        wormHole.image = wormHole.wormHole_animation.getKeyFrame(stateTime, true);
            if (gameState == true)
        regularMeteor.image = RegularMeteor.animation.getKeyFrame(stateTime, true);

        ship.image.setSize(shipSizeX, shipSizeY);
        ship.image.setCenter(shipX, shipY);
        ship.image.setOriginCenter();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //Draw Graphics on screen
        batch.draw(Graphics.sprite_background, 0, 0, camera.viewportWidth, camera.viewportHeight);

        for (Rectangle smallRegMeteor : smallRegMeteors)
        {
            batch.draw(regularMeteor.image, smallRegMeteor.x, smallRegMeteor.y,
                    regularMeteor.bounds.width, regularMeteor.bounds.height);
        }

        batch.draw(Graphics.sprite_scorePanelLeft, 0, 880, 650, 200);
        batch.draw(Graphics.sprite_scorePanelRight, 1270, 880, 650, 200);
        batch.draw(Graphics.sprite_topBar, 650, 880, 620, 200);
        blackFont.setScale(3f, 4f);
        blackFont.draw(batch, "Collected: " + meteorsGathered, 110, 1040);
        blackFont.draw(batch, "Speed: " + fallSpeed, 1500, 1040);
        blackFont.draw(batch, "Hull Integrity " + shipHull, 1350, 980);
        redFont.setScale(3f, 4f);
        redFont.setColor(Color.RED);
        redFont.draw(batch, "Missed: " + meteorsMissed, 210, 980);

        batch.draw(wormHole.image, wormHole.bounds.x, wormHole.bounds.y, wormHole.bounds.width, wormHole.bounds.height);

        ship.image.draw(batch);

        //End batch "all graphics above this"
        batch.end();
    }

    public void setGameState(boolean running, boolean reset) {
        // Boolean running: to set state whether the game is currently playing or paused
        // Boolean reset: to reset the screen back to default values when game over.
        if (reset == true)
        {
            meteorsGathered = 0;
            meteorsMissed = 0;
            fallSpeed = startingSpeed;
            shipHull = startingHull;
            shipX = 960;
            shipY = 540;
            smallRegMeteors.clear();
        }

        if (running == false)
        {
            gameState = false;
        }
        else if (running == true)
        {
            gameState = true;
        }

    }

    public void generalUpdate(Vector3 touch, OrthographicCamera camera) {
        ship.image.setRotation(0);
        wormHole.bounds.x = shipX - ship.image.getWidth();
        wormHole.bounds.y = shipY + 75;
        if (gameState == true && Gdx.input.isTouched())
        {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            Float degrees = (float) ((Math.atan2(touch.x - shipX,
                    -(touch.y - shipY)) * 180.0d / Math.PI) - 180.0f);


            // Sets the X,Y for the wormHole
            wormHole.bounds.setPosition(shipX * touch.x, shipY * touch.y);

            // Check to see if ship was touched and update its X,Y if it was
            if (ship.image.getBoundingRectangle().contains(touch.x, touch.y))
            {
                //  wormHole.bounds.setSize(256,256);
                //  wormHole.bounds.setX(shipX - ship.image.getWidth() * 50);

                shipX = (int) touch.x;
                shipY = (int) touch.y;
                ship.image.setOriginCenter();
                ship.image.setRotation(degrees);
            }
        }
        wormHole.bounds.setSize(256, 256);
        wormHole.bounds.x = shipX - wormHole.bounds.getWidth() / 2;


        //Keyboard Keys input - not needed  "testing purposes only"
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
        {
            camera.zoom += .5;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
        {
            camera.zoom -= .5;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)))
        {
            pause();
        }

        // Check if WormHole is at screen boarder and keep it there, to prevent it going off screen
        if (shipX < 1)
        {
            shipX = 1;
        }
        if (shipX > camera.viewportWidth - ship.image.getWidth())
        {
            shipX = (int) camera.viewportWidth - (int) ship.image.getWidth();
        }
        if (shipY < 1)
        {
            shipY = 1;
        }

        // Sets Spawn rate for Meteors
        if (gameState == true && TimeUtils.millis() - lastDropTime > meteorRate) RegularMeteor.spawnMeteor();

        // Sets Speed of Meteors
        if (gameState == true && TimeUtils.millis() - speedTime > speedRate)
        {
            if (fallSpeed < maxSpeed)
                fallSpeed += 5;
            speedTime = TimeUtils.millis();
        }

        java.util.Iterator<Rectangle> iter = smallRegMeteors.iterator();
        while (iter.hasNext())
        {
            Rectangle smallRegMeteor = iter.next();
            smallRegMeteor.y -= fallSpeed * Gdx.graphics.getDeltaTime();

            if (smallRegMeteor.y + 64 < 0)
            {
                meteorsMissed++;
                iter.remove();
            }
            if (smallRegMeteor.overlaps(WormHole.bounds))
            {
                if (meteorsGathered >= meteorsToWin){
                    //Go to Win Screen
                    game.setScreen(game.wonScreen);
                Helper.Log("You Win");

            }else
                meteorsGathered++;
                iter.remove();
            }
            if (smallRegMeteor.overlaps(ship.image.getBoundingRectangle()))
            {
                Helper.Log("Ship hit" + smallRegMeteor);
                shipHull--;
                if (shipHull <= 0)
                {
                    // to be replaced by lose screen ****************
                    game.setScreen(game.levelSelectScreen);
                }

            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        setGameState(true, true);


    }

    @Override
    public void hide() {
        setGameState(false, false);
    }

    @Override
    public void pause() {
        game.pauseScreen.show();
        game.pauseScreen.resume();
        game.setScreen(game.pauseScreen);
        setGameState(false, false);

    }

    @Override
    public void resume() {
        setGameState(true, false);

    }

    @Override
    public void dispose() {
        Graphics.disposeLevel1();
        Graphics.disposeMeteor();
        Graphics.disposeShip();
    }
}
