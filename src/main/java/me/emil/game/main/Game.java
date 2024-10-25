package me.emil.game.main;

import me.emil.game.help.LoadSave;
import javax.swing.JFrame;
import me.emil.game.manager.TileManager;
import me.emil.game.scenes.Editing;
import me.emil.game.scenes.GameOver;
import me.emil.game.scenes.Menu;
import me.emil.game.scenes.Playing;
import me.emil.game.scenes.Settings;

/** Sets the properties of the game.
 * Makes the game screen visible.
 * 
 */
public class Game extends JFrame implements Runnable {

    private GameScreen gameScreen;
    private Thread gameThread;

    private final double fpsSet = 120;
    private final double upsSet = 60;

    //Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;
    private GameOver gameOver;

    private TileManager tileManager;


    /** Sets the properties of the game.
     * 
     */
    public Game() {
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setResizable(false);

        initClasses();
        createDefaultLevel();

        add(gameScreen);
        pack();

        setVisible(true);

    }

    private void createDefaultLevel() {
        int[] arr = new int[400];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }

        LoadSave.createLevel("new_level", arr);
    }

    private void initClasses() {

        tileManager = new TileManager();
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
        gameOver = new GameOver(this);

        
    }

    private void start() {
        gameThread = new Thread(this) {};
        gameThread.start();
    }

    private void updateGame() {
        switch (GameStates.gameState) {
            case EDITING:
                playing.update();
                break;
            case MENU:
                break;
            case PLAYING:
                playing.update();
                break;
            case SETTINGS:
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        System.out.println("I like women");

        Sound2.testSound();

        Game game = new Game();
        game.gameScreen.initInputs();;


        game.start();

      
    }

    public void run() {
        double timePerFrame = 1000000000.0 / fpsSet;
        double timePerUpdate = 1000000000.0 / upsSet;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();
        
        int frames = 0;
        int updates = 0;

        long now;

        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }

            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    // Getters and setters
    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    
    public Settings getSettings() {
        return settings;
    }
    
    public Editing getEditing() {
        return editing;
    }
    
    public GameOver getGameOver() {
        return gameOver;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

}

