package net.poljw002.AndroidAssignment1;

import com.badlogic.gdx.Game;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {



    @Override
    public void create() {
        setScreen(new MainMenu(this));

    }
    public void render() {
        //ScreenUtils.clear(1, 0, 0, 1, true);
        super.render();
    }
    public void openGame(){
        setScreen(new BallGame(this));
    }
    public void openMenu(){
        setScreen(new MainMenu(this));
    }
}
