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
    //all variables are created, with only few exceptions being initialised
    TiledMap baseMap;
    TiledMap secondMap;
    TiledMap[] maps;
    OrthographicCamera camera;
    Sprite player;
    TiledMapRenderer tiledMapRendererOne;
    TiledMapRenderer tiledMapRendererTwo;
    TiledMapRenderer[] renderArray;
    int currentFloor = 0;
    TiledMapTileLayer oneWalls;
    TiledMapTileLayer oneFloor;
    TiledMapTileLayer twoWalls;
    TiledMapTileLayer twoFloor;
    Stage stage = new Stage(new ScreenViewport());
    OrthographicCamera cam = new OrthographicCamera();

    SpriteBatch batch;
    SpriteBatch opacityShift;
    Ball ball;
    Main main;
    Table table;
    Skin skin;
    TextButton btn1;
    TextButton btn2;
    TextButton btn3;
    TextButton btn4;

    //used to control the input, and reset as needed.
    int[] movexy = new int[] {0,0};
    int[] resetMove = new int[] {0,0};

    private Texture playerTexture;
    public BallGame(Main mainPar) {
        //keeps the Main clas as a parameter, to later call the endscene
        main = mainPar;

        //the following creates a ui table, and uses it to store the four directional movement buttons in the four corners of the screen.
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

        //gets the width, height, and the player sprite.
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        System.out.println(Gdx.files.internal("circle.png").exists());
        playerTexture = new Texture("circle.png");
        player = new Sprite(playerTexture);
        player.setSize(32, 32);


        //the player ball object is created.
        ball = new Ball();

        //opacity shift is used for the shadows of the secondfloor before it is accessed
        opacityShift = new SpriteBatch();

        //the tilemaps and renderers are created and the layers are stored as variables.

        baseMap = new TmxMapLoader().load("baseFloor.tmx");
        tiledMapRendererOne = new IsometricTiledMapRenderer(baseMap);

        MapLayers oneLayers = baseMap.getLayers();
        oneWalls = (TiledMapTileLayer) oneLayers.get("Walls");
        oneFloor = (TiledMapTileLayer) oneLayers.get("Floor");

        secondMap = new TmxMapLoader().load("SecondFloor.tmx");
        tiledMapRendererTwo = new IsometricTiledMapRenderer(secondMap, opacityShift);

        renderArray = new TiledMapRenderer[]{tiledMapRendererOne, tiledMapRendererTwo};

        MapLayers twoLayers = secondMap.getLayers();
        twoWalls = (TiledMapTileLayer) twoLayers.get("Walls");
        twoFloor = (TiledMapTileLayer) twoLayers.get("Floor");


        //the camera is created, zoomed out at 3x to see as much as possible.
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.zoom = 3f;
        camera.update();




        //the spritebatch for the player is created, and the maps are stored in an array to access using an index using the current floor
        batch = new SpriteBatch();
        maps = new TiledMap[] {baseMap, secondMap};

        //the stage is set as the input listener, and the buttons receive their click events which alter the movexy variable.
        Gdx.input.setInputProcessor(stage);
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button 1 pressed");
                movexy = new int[] {-1,1};
            }
        });

        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button 2 pressed");
                movexy = new  int[] {1,1};
            }
        });
        btn3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button 3 pressed");
                movexy = new int[] {-1,-1};
            }
        });

        btn4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button 4 pressed");
                movexy = new int[] {1,-1};
            }
        });
        //this is here to prevent the ball spawning out of bounds.
        ball.x= 1000;

    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        //screen is wiped
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //ball is accelerate according to buttons pressed, or decelerated if none.
        ball.accelerate(movexy);
        //checks if the ball will collide if it moves, and then either moves the ball, or halts its velocity.
        if (!check_collision()){
            ball.move();
        } else{
            ball.velocity[0] = 0;
            ball.velocity[1] = 0;
        }

        //resets the variable to {0,0}
        movexy = resetMove;
        //ensures the camera follows the ball
        camera.position.set(ball.x, ball.y, 0);
        camera.update();

        if(currentFloor == 0){
            //while the currentfloor is 0 (topfloor), the secondary renderer will be darkened, and draw the second floor at a minor offset so it sits flush with the base floor.
            camera.position.y +=128;
            camera.update();
            opacityShift.setColor(0.7f,0.7f,0.7f,1);
            renderArray[1].setView(camera);
            renderArray[1].render(new int[] {0});
            renderArray[1].render(new int[] {1});
            camera.position.y -= 128;
            camera.update();
            opacityShift.setColor(1,1,1,1);
        }


        //the batch and renderer are set to the camera, and then everything is drawn
        batch.setProjectionMatrix(camera.combined);

        renderArray[currentFloor].setView(camera);
        //renders the floor
        renderArray[currentFloor].render(new int[] {0});
        batch.begin();
        //renders the player
        batch.draw(player, ball.x, ball.y);
        batch.end();
        //renders the walls
        renderArray[currentFloor].render(new int[] {1});


    }

    public void resize (int width, int height) {
        // define view port in logical units (tiles) but maintain aspect ratio if the screen/window
        //cam.viewportWidth = VIEW_SIZE;
        //cam.viewportHeight = VIEW_SIZE * height/width;
        //cam.update();
    }
    public boolean check_collision(){
        //gets the currently active maps wall layer.
        TiledMapTileLayer layer = (TiledMapTileLayer) maps[currentFloor].getLayers().get("Walls");

        //gets the x and y tile distance, originating from the top middle tile, which is 0,0 in the tmx file.
        int tileY = 48 - ((ball.x+(int) ball.velocity[0]) / 128+(ball.y+(int) ball.velocity[1])/64)/2;
        int tileX = ((ball.y+(int) ball.velocity[1]) / 64-(ball.x+(int) ball.velocity[0])/128)/2;
        //converts the coordinates into the tile
        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);

        //if the player is in a goal zone, change floors or open the end scene
        if(tileX<= 6 && tileY <= 6 && currentFloor == 0){
            currentFloor = 1;
        } else if(tileX<= 6 && tileY >= 42 && currentFloor == 1){
            main.openEnd();
        }

        //if the cell is not null, and the tile is not null, there was a collision
        if(cell != null && cell.getTile() != null){
            System.out.println("cell: " +cell.getTile());
            return true;
        }
        else{
            //else, no collision
            return false;
        }
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
        baseMap.dispose();
        secondMap.dispose();
        playerTexture.dispose();
    }
}

