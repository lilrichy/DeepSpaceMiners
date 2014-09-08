package com.reigens.deepSpaceMiners.Screens;

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
import com.reigens.deepSpaceMiners.Helper;
import com.reigens.deepSpaceMiners.Meteors.RegularMeteor;
import com.reigens.deepSpaceMiners.Ships.Ship1;
import com.reigens.deepSpaceMiners.Ships.WormHole;
/**
 * Created by Richard Reigens on 9/6/2014.
 */

public class Level1Screen implements Screen {
    OrthographicCamera camera;
    float stateTime;
    public static SpriteBatch batch;
    int shipX = 960;
    int shipY = 540;
    Vector3 touch;
    BitmapFont blackFont, redFont;
    WormHole wormHole;
    Ship1 ship;

    RegularMeteor regularMeteor;
    public static Array<Rectangle> smallRegMeteors;
    int meteorsGathered;
    int meteorsMissed;
    long speedTime = TimeUtils.millis();
    public static long lastDropTime;

    //Changeable variables
    int fallSpeed = 100;// Starting Speed
    int maxSpeed = 500;// Cap on meteor speed - higher is faster
    int speedRate = 500;// Rate of speed increase - higher is slower increase over time
    int meteorRate = 500;// Time in between meteors - higher is less meteors

    public Level1Screen() {
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
        RegularMeteor.spawnMeteor();

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
        regularMeteor.image = RegularMeteor.animation.getKeyFrame(stateTime, true);
        ship.image.setSize(228, 228);
        ship.image.setCenter(shipX - 50, shipY);
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

        batch.draw(Graphics.sprite_scorePanelLeft, -50, 1080 -200, 650, 300);
        batch.draw(Graphics.sprite_scorePanelRight,1320, 1080 -200, 650, 300);
        blackFont.setScale(2, 2);
        blackFont.draw(batch, "Meteors Collected: " + meteorsGathered, 75, 1060);
        blackFont.draw(batch, "Speed: " + fallSpeed, 1500, 1060);
        redFont.setScale(2, 2);
        redFont.setColor(Color.RED);
        redFont.draw(batch, "Meteors Missed: " + meteorsMissed, 125, 1000);

        batch.draw(wormHole.image, wormHole.bounds.x, wormHole.bounds.y, wormHole.bounds.width, wormHole.bounds.height);

        ship.image.draw(batch);

        //End batch "all graphics above this"
        batch.end();
    }

    public void generalUpdate(Vector3 touch, OrthographicCamera camera) {
        ship.image.setRotation(0);
        wormHole.bounds.x = shipX - ship.image.getWidth();
        wormHole.bounds.y = shipY + 75;
        if (Gdx.input.isTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            Float  degrees = (float) ((Math.atan2 (touch.x - shipX,
                 -(touch.y - shipY))*180.0d/Math.PI)-180.0f);


            // Sets the X,Y for the wormHole
            wormHole.bounds.setPosition(shipX * touch.x, shipY * touch.y);

            // Check to see if ship was touched and update its X,Y if it was
          if (ship.image.getBoundingRectangle().contains(touch.x, touch.y))
            {
                wormHole.bounds.setSize(256,256);
                wormHole.bounds.setX(shipX - ship.image.getWidth() * 2);

                shipX =  (int)touch.x;
                shipY =  (int)touch.y;
                ship.image.setOriginCenter();
                ship.image.setRotation(degrees);
                Helper.Log(degrees.toString());
            }

        }

         wormHole.bounds.setSize(128,128);
         wormHole.bounds.x = shipX - ship.image.getWidth() / 2;

        //Keyboard Keys input - not needed "WIP"
        if (Gdx.input.isKeyPressed(Input.Keys.A) || (Gdx.input.isKeyPressed(Input.Keys.LEFT)))
        {
            shipX -= 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT)))
        {
            shipX += 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.UP)))
        {
            shipY -= 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) || (Gdx.input.isKeyPressed(Input.Keys.DOWN)))
        {
            shipY += 10;
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
        /*if (shipY > camera.viewportHeight /3 - ship.image.getHeight())
        {
            shipY = (int) camera.viewportHeight /3 - (int) ship.image.getHeight();
        }*/





        // Sets Spawn rate for Meteors
        if (TimeUtils.millis() - lastDropTime > meteorRate) RegularMeteor.spawnMeteor();

        // Sets Speed of Meteors
        if (TimeUtils.millis() - speedTime > speedRate){
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
                meteorsGathered++;
                iter.remove();
            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        Graphics.disposeLevel1();
        Graphics.disposeMeteor();
        Graphics.disposeShip();
    }
}
