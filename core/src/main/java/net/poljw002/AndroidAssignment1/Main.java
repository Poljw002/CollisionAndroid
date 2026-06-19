package net.poljw002.AndroidAssignment1;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    TiledMapTileLayer oneWalls;
    TiledMapTileLayer oneFloor;

    OrthographicCamera cam = new OrthographicCamera();
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();



        tiledMap = new TmxMapLoader().load("baseFloor.tmx");
        tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap);
        //tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        MapLayers oneLayers = tiledMap.getLayers();
        oneWalls = (TiledMapTileLayer) oneLayers.get("Walls");
        oneFloor = (TiledMapTileLayer) oneLayers.get("Floor");



        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 30f;
        cam.update();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //SpriteBatch batch = (SpriteBatch) tiledMapRenderer.getBatch();
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(new int[] {0,1});


    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        image.dispose();
    }
}
