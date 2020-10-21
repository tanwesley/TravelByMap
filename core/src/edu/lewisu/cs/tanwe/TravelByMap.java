package edu.lewisu.cs.tanwe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TravelByMap extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tex, tex2;
    TextureRegion img, img2;
    OrthographicCamera cam;
    int WIDTH;
	int HEIGHT;
	int imgX, imgY;
	int imgWidth, imgHeight;
	int imgOrgX, imgOrgY;
    int imgAngle;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		tex = new Texture("zoomedoutmap.png");
		tex2 = new Texture("zoomedinmap.png");
        WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
        imgWidth = tex.getWidth();
        imgHeight = tex.getHeight();
        imgAngle = 0;
		img = new TextureRegion(tex);
		img2 = new TextureRegion(tex2); 
        imgX = 0;
		imgY = 0;
		imgOrgX = imgX/2;
		imgOrgY = imgY/2;
        cam = new OrthographicCamera(WIDTH*2,HEIGHT*2);
        cam.translate(WIDTH,HEIGHT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
	}
	public void handleInput() {
        boolean shiftHeld = false;
        boolean cameraNeedsUpdating = false;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
            shiftHeld = true;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            if (shiftHeld) {
                //zoom
                cam.zoom += 0.1;
            } else {
                cam.translate(0,1);
            }
            cameraNeedsUpdating = true;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            if (shiftHeld) {
                //zoom
                cam.zoom -= 0.1;
            } else {
                cam.translate(0,-1);
            }
            cameraNeedsUpdating = true;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if (shiftHeld) {
                //rotate
                cam.rotate(1);
            } else {
                cam.translate(-1,0);
            }
            cameraNeedsUpdating = true;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            if (shiftHeld) {
                //rotate
                cam.rotate(-1);
            } else {
                cam.translate(1,0);
            }
			cameraNeedsUpdating = true;
		}

        if (cameraNeedsUpdating) {
            updateCamera();
		}
    }
	public void updateCamera() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
		batch.begin();
		batch.draw(img, imgX, imgY, imgOrgX, imgOrgY, imgWidth, imgHeight, 1f, 1f, imgAngle);
		if (cam.zoom > .6) {
			batch.draw(img, imgX, imgY, imgOrgX, imgOrgY, imgWidth, imgHeight, 1f, 1f, imgAngle);
		} else if (cam.zoom < .6) {
			batch.draw(img2, imgX, imgY, imgOrgX, imgOrgY, imgWidth, imgHeight, 1f, 1f, imgAngle);
		}
        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
