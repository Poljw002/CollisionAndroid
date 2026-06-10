package net.poljw002.AndroidAssignment1;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Game {
    OrthographicCamera cam = new OrthographicCamera();
    public void create () {

        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 30f;
        cam.update();

    }


    public void resize (int width, int height) {
        // define view port in logical units (tiles) but maintain aspect ratio if the screen/window
        //cam.viewportWidth = VIEW_SIZE;
        //cam.viewportHeight = VIEW_SIZE * height/width;
        //cam.update();
    }
}

