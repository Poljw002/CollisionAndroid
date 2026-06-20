package net.poljw002.AndroidAssignment1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenu implements Screen {
    Main main;
    public TextButton startButton;
    public TextButton quitButton;
    public Stage stage;
    OrthographicCamera camera;
    FitViewport menu;
    Skin style;


    public MainMenu(Main mainPar){
        main = mainPar;

    }

    @Override
    public void show() {
        camera = new OrthographicCamera(800,450 );
        style = new Skin(Gdx.files.internal("uiskin.json"));



        startButton = new TextButton("Start", style, "default");
        quitButton = new TextButton("Quit", style, "default");

        menu = new FitViewport(800, 450);
        stage = new Stage(menu);

        startButton.setSize(200, 200);
        quitButton.setSize(200, 200);
        startButton.setPosition((100), 100);
        quitButton.setPosition((500), 100);

        stage.addActor(startButton);
        stage.addActor(quitButton);

        Gdx.input.setInputProcessor(stage);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(null);
                main.openGame();
            }


        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
            }


        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.8f, 0.6f, 1, true);
        menu.apply();
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {

    }
}
