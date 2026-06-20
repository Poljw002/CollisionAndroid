package net.poljw002.AndroidAssignment1;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BallGame implements Screen
{
    TiledMap tiledMap;
    OrthographicCamera camera;
    Sprite player;
    TiledMapRenderer tiledMapRenderer;
    TiledMapTileLayer oneWalls;
    Stage stage = new Stage(new ScreenViewport());
    TiledMapTileLayer oneFloor;

    OrthographicCamera cam = new OrthographicCamera();

    SpriteBatch batch;
    Ball ball;
    Main main;
    Table table;
    Skin skin;
    TextButton btn1;
    TextButton btn2;
    TextButton btn3;
    TextButton btn4;

    private Texture playerTexture;
    public BallGame(Main mainPar) {
        main = mainPar;

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        table = new Table();
        table.setFillParent(true);

        btn1 = new TextButton("1", skin);
        btn2 = new TextButton("2", skin);
        btn3 = new TextButton("3", skin);
        btn4 = new TextButton("4", skin);

        table.add(btn1).expand().fill();
        table.add(btn2).expand().fill();
        table.row();

        table.add(btn3).expand().fill();
        table.add(btn4).expand().fill();

        stage.addActor(table);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        System.out.println(Gdx.files.internal("circle.png").exists());
        playerTexture = new Texture("circle.png");
        player = new Sprite(playerTexture);
        player.setSize(32, 32);

        ball = new Ball();

        tiledMap = new TmxMapLoader().load("baseFloor.tmx");
        tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap);
        //tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        MapLayers oneLayers = tiledMap.getLayers();
        oneWalls = (TiledMapTileLayer) oneLayers.get("Walls");
        oneFloor = (TiledMapTileLayer) oneLayers.get("Floor");


        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button 1 pressed");
                ball.accelerate(new int[] {1,1});
            }
        });

        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button 2 pressed");
                ball.accelerate(new int[] {1,-1});
            }
        });
        btn3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button 3 pressed");
                ball.accelerate(new int[] {-1,1});
            }
        });

        btn4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button 4 pressed");
                ball.accelerate(new int[] {-1,-1});
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //SpriteBatch batch = (SpriteBatch) tiledMapRenderer.getBatch();
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(new int[] {0});
        batch.begin();
        batch.draw(player, ball.x, ball.y);
        batch.end();
        tiledMapRenderer.render(new int[] {1});


    }

    public void resize (int width, int height) {
        // define view port in logical units (tiles) but maintain aspect ratio if the screen/window
        //cam.viewportWidth = VIEW_SIZE;
        //cam.viewportHeight = VIEW_SIZE * height/width;
        //cam.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose() {
        tiledMap.dispose();
        playerTexture.dispose();
    }
}

