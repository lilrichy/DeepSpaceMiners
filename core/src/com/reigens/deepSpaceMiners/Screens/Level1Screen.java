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
import com.reigens.deepSpaceMiners.Meteors.RegularMeteor;
import com.reigens.deepSpaceMiners.Ships.WormHole;


/**
 * Created by Richard Reigens on 9/6/2014.
 */
public class Level1Screen implements Screen {
    //Create Variables

    OrthographicCamera camera;
    float stateTime;
    public static SpriteBatch batch;
    int wormHoleX = 960;
    int wormHoleY = 540;
    Vector3 touch;
    WormHole wormHole;
    RegularMeteor regularMeteor;
    public static long lastDropTime;
    public static Array<Rectangle> smallRegMeteors;
    int fallSpeed = 100;
    int meteorsGathered;
    BitmapFont whiteFont, redFont;
    int meteorsMissed;

    public Level1Screen() {
        //Initialize Variables and any other "run once" commands
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        Graphics.loadLevel1();
        Graphics.loadShip();
        Graphics.loadMeteor();
        whiteFont = new BitmapFont();
        redFont = new BitmapFont();
        stateTime = 0f;
        batch = new SpriteBatch();
        wormHole = new WormHole();
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

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //Draw Graphics on screen
        batch.draw(Graphics.sprite_background, 0, 0, camera.viewportWidth, camera.viewportHeight);

        for (Rectangle smallRegMeteor : smallRegMeteors)
            batch.draw(regularMeteor.image, smallRegMeteor.x, smallRegMeteor.y,
                    regularMeteor.bounds.width, regularMeteor.bounds.height);

        batch.draw(Graphics.sprite_scorePanelLeft, -50, 1080 -200, 650, 300);
        batch.draw(Graphics.sprite_scorePanelRight,1320, 1080 -200, 650, 300);

        whiteFont.setScale(3, 3);
        whiteFont.draw(batch, "Meteors Collected: " + meteorsGathered, 100, 1060);
        whiteFont.draw(batch, "Speed: " + fallSpeed, 1500, 1060);
        redFont.setScale(3, 3);
        redFont.setColor(Color.RED);
        redFont.draw(batch, "Meteors Missed: " + meteorsMissed, 150, 1000);
        batch.draw(wormHole.image, wormHole.bounds.x, wormHole.bounds.y, wormHole.bounds.width, wormHole.bounds.height);

        //End batch "all graphics above this"
        batch.end();
    }

    public void generalUpdate(Vector3 touch, OrthographicCamera camera) {
        if (Gdx.input.isTouched())
        {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            // Check to see if wormHole was touched and update its X,Y if it was
            if (wormHole.bounds.contains(touch.x, touch.y))
            {
                wormHole.bounds.setSize(256,256);
                wormHoleX = (int) touch.x - (int) wormHole.bounds.getWidth() / 2;
                wormHoleY = (int) touch.y - (int) wormHole.bounds.getHeight() / 2;
            }
            else wormHole.bounds.setSize(128, 128);
        }

        //Keyboard Keys input - not needed "WIP"
        if (Gdx.input.isKeyPressed(Input.Keys.A) || (Gdx.input.isKeyPressed(Input.Keys.LEFT)))
        {
            wormHoleX -= 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT)))
        {
            wormHoleX += 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.UP)))
        {
            wormHoleY -= 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) || (Gdx.input.isKeyPressed(Input.Keys.DOWN)))
        {
            wormHoleY += 10;
        }

        // Check if WormHole is at screen boarder and keep it there, to prevent it going off screen
        if (wormHoleX < 1)
        {
            wormHoleX = 1;
        }
        if (wormHoleX > camera.viewportWidth - wormHole.bounds.width)
        {
            wormHoleX = (int) camera.viewportWidth - (int) wormHole.bounds.width;
        }
        if (wormHoleY < 1)
        {
            wormHoleY = 1;
        }
        if (wormHoleY > camera.viewportHeight /3 - wormHole.bounds.height)
        {
            wormHoleY = (int) camera.viewportHeight /3 - (int) wormHole.bounds.height;
        }

        // Sets the X,Y for the wormHole
        wormHole.bounds.setX(wormHoleX);
        wormHole.bounds.setY(wormHoleY);

        if (TimeUtils.millis() - lastDropTime > 50) RegularMeteor.spawnMeteor();
        if (TimeUtils.millis() - lastDropTime > 10) fallSpeed += 4 * Gdx.graphics.getDeltaTime();

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
