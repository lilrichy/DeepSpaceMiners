package com.reigens.deepSpaceMiners.Screens.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.Asteroids.RegularAsteroids;
import com.reigens.deepSpaceMiners.GameMain;
import com.reigens.deepSpaceMiners.Screens.LostScreen;
import com.reigens.deepSpaceMiners.Screens.PauseScreen;
import com.reigens.deepSpaceMiners.Screens.WonScreen;
import com.reigens.deepSpaceMiners.Ships.Ship1;
import com.reigens.deepSpaceMiners.Ships.WormHole;
import com.reigens.deepSpaceMiners.Uitilitys.LogHelper;

/**
 * Created by Richard Reigens on 9/6/2014.
 */

public class Level1Screen implements Screen {
    public static SpriteBatch batch;
    public static Array<Rectangle> smallRegularAsteroids;
    public static long lastDropTime;
    GameMain game;
    OrthographicCamera camera;
    float stateTime;
    int shipX = Gdx.graphics.getWidth() / 2;
    int shipY = Gdx.graphics.getHeight() / 2;
    Vector3 touch;
    BitmapFont blackFont, redFont;
    WormHole wormHole;
    Ship1 ship;
    boolean gameState = true;
    RegularAsteroids regularAsteroids;
    int asteroidsGathered;
    int asteroidsMissed;
    long speedTime = TimeUtils.millis();
    //Changeable variables
    int shipSizeX = 64, shipSizeY = 64;// Ship Size
    int startingHull = 100;// Default Hull integrity to reset to
    int shipHull = 100;// Hull integrity Total - set same as startingHull but this value changes while playing
    int startingSpeed = 50;// Default Starting speed to reset to "same as fall speed"
    int fallSpeed = 50;// Starting Speed - Set same as startingSpeed, but this value changes while playing.
    int maxSpeed = 500;// Cap on asteroid speed - higher is faster
    int speedRate = 500;// Rate of speed increase - higher is slower increase over time
    int asteroidRate = 500;// Time in between asteroids - higher is less asteroids
    int asteroidsToWin = 50;// Number of asteroids required to win

    public Level1Screen(GameMain game) {
        this.game = game;

        //Initialize Variables and any other "run once" commands
        camera = new OrthographicCamera();
        //camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        blackFont = Assets.manager.get(Assets.black14, BitmapFont.class);
        redFont = Assets.manager.get(Assets.red14, BitmapFont.class);
        stateTime = 0f;
        batch = new SpriteBatch();
        wormHole = new WormHole();
        ship = new Ship1();
        touch = new Vector3();
        regularAsteroids = new RegularAsteroids(game);
        smallRegularAsteroids = new Array<Rectangle>();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        stateTime += Gdx.graphics.getDeltaTime();
        generalUpdate(touch, camera);

        //Initialize graphics
        WormHole.image = WormHole.wormHole_animation.getKeyFrame(stateTime, true);
        if (gameState)
            RegularAsteroids.image = RegularAsteroids.animation.getKeyFrame(stateTime, true);

        Ship1.image.setSize(shipSizeX, shipSizeY);
        Ship1.image.setCenter(shipX, shipY);
        Ship1.image.setOriginCenter();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //Draw Graphics on screen
        batch.draw(Assets.manager.get(Assets.level1Background, Texture.class), 0, 0, camera.viewportWidth, camera.viewportHeight);

        for (Rectangle smallRegAsteroid : smallRegularAsteroids) {
            batch.draw(RegularAsteroids.image, smallRegAsteroid.x, smallRegAsteroid.y,
                    RegularAsteroids.bounds.width, RegularAsteroids.bounds.height);
        }

        batch.draw(Assets.manager.get(Assets.hudLeftPanel, Texture.class), 0, 880, 650, 200);
        batch.draw(Assets.manager.get(Assets.hudRightPanel, Texture.class), 1270, 880, 650, 200);
        batch.draw(Assets.manager.get(Assets.hudTopBar, Texture.class), 650, 880, 620, 200);
        blackFont.setScale(3f, 4f);
        blackFont.draw(batch, "Collected: " + asteroidsGathered, 110, 1040);
        blackFont.draw(batch, "Speed: " + fallSpeed, 1500, 1040);
        blackFont.draw(batch, "Hull Integrity " + shipHull, 1350, 980);
        redFont.setScale(3f, 4f);
        redFont.setColor(Color.RED);
        redFont.draw(batch, "Missed: " + asteroidsMissed, 210, 980);

        batch.draw(WormHole.image, WormHole.bounds.x, WormHole.bounds.y, WormHole.bounds.width, WormHole.bounds.height);
        Ship1.image.draw(batch);

        //End batch "all graphics above this"
        batch.end();
    }

    public void setGameState(boolean running, boolean reset) {
        // Boolean running: to set state whether the game is currently playing or paused
        // Boolean reset: to reset the screen back to default values when game over.
        if (reset) {
            asteroidsGathered = 0;
            asteroidsMissed = 0;
            fallSpeed = startingSpeed;
            shipHull = startingHull;
            shipX = Gdx.graphics.getWidth() / 2;
            shipY = Gdx.graphics.getHeight() / 2;
            smallRegularAsteroids.clear();
        }

        gameState = running;
    }

    public void generalUpdate(Vector3 touch, OrthographicCamera camera) {
        Ship1.image.setRotation(0);
        WormHole.bounds.x = shipX - Ship1.image.getWidth();
        WormHole.bounds.y = shipY + 75;
        if (gameState && Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            Float degrees = (float) ((Math.atan2(touch.x - shipX,
                    - (touch.y - shipY)) * 180.0d / Math.PI) - 180.0f);

            // Sets the X,Y for the wormHole
            WormHole.bounds.setPosition(shipX * touch.x, shipY + 64);

            // Check to see if ship was touched and update its X,Y if it was
            if (Ship1.image.getBoundingRectangle().contains(touch.x, touch.y)) {
                //  wormHole.bounds.setSize(256,256);
                //  wormHole.bounds.setX(shipX - ship.image.getWidth() * 50);

                shipX = (int) touch.x;
                shipY = (int) touch.y;
                Ship1.image.setOriginCenter();
                Ship1.image.setRotation(degrees);
            }
        }
        WormHole.bounds.setSize(64, 64);
        WormHole.bounds.x = shipX - WormHole.bounds.getWidth() / 2;

        //Keyboard Keys input - not needed  "testing purposes only"
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            camera.zoom += .5;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            camera.zoom -= .5;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) {
            pause();
        }

        // Check if ship is at screen boarder and keep it there, to prevent it going off screen
        if (shipX < 1) {
            shipX = 1;
        }
        if (shipX > camera.viewportWidth - Ship1.image.getWidth()) {
            shipX = (int) camera.viewportWidth - (int) Ship1.image.getWidth();
        }
        if (shipY < 1) {
            shipY = 1;
        }

        // Sets Spawn rate for asteroids
        if (gameState && TimeUtils.millis() - lastDropTime > asteroidRate) RegularAsteroids.spawnAsteroid();

        // Sets Speed of asteroids
        if (gameState && TimeUtils.millis() - speedTime > speedRate) {
            if (fallSpeed < maxSpeed)
                fallSpeed += 5;
            speedTime = TimeUtils.millis();
        }

        java.util.Iterator<Rectangle> iter = smallRegularAsteroids.iterator();
        while (iter.hasNext()) {
            Rectangle smallRegAsteroid = iter.next();
            smallRegAsteroid.y -= fallSpeed * Gdx.graphics.getDeltaTime();

            if (smallRegAsteroid.y + 64 < 0) {
                asteroidsMissed++;
                iter.remove();
            }
            if (smallRegAsteroid.overlaps(WormHole.bounds)) {
                if (asteroidsGathered == asteroidsToWin - 1) {
                    //Go to Win Screen
                    iter.remove();
                    setGameState(false, true);
                    game.setScreen(new WonScreen(game));
                }
                else if (asteroidsGathered <= asteroidsToWin) {
                    asteroidsGathered++;
                    iter.remove();
                }
            }
            if (smallRegAsteroid.overlaps(Ship1.image.getBoundingRectangle())) {
                LogHelper.Log("Ship hit" + smallRegAsteroid);
                shipHull--;
                if (shipHull <= 0) {
                    // to be replaced by lose screen ****************
                    game.setScreen(new LostScreen(game));
                }
            }
        }
    }

    @Override
    public void hide() {
        setGameState(false, false);
    }

    @Override
    public void pause() {

        game.setScreen(new PauseScreen(game));
        setGameState(false, false);
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
    public void resume() {
        setGameState(true, false);
    }

    @Override
    public void dispose() {

    }
}
