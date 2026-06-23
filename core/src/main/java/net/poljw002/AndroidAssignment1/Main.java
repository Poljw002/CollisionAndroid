package net.poljw002.AndroidAssignment1;

import com.badlogic.gdx.Game;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {



    @Override
    public void create() {
        //immediately creates a new main menu screen on start up
        setScreen(new MainMenu(this));

    }
    public void render() {
        //ScreenUtils.clear(1, 0, 0, 1, true);
        super.render();
    }
    //these three functions are called using this class as a variable called mainPar.
    public void openGame(){
        setScreen(new BallGame(this));
    }
    public void openMenu(){
        setScreen(new MainMenu(this));
    }
    public void openEnd(){
        setScreen(new EndScene(this));
    }
}
