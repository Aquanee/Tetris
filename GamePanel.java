import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{

    public static final int width = 1280;
    public static final int height = 720;
    final int FPS = 60;
    Thread gameThread; 
    PlayManager pm;

    // GamePanel() sets the dimensions and colors of the application
    public GamePanel(){

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);

        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        
        pm = new PlayManager();
        
    }

    // LaunchGame() starts the game loop once the application is launched
    public void LaunchGame(){

            gameThread = new Thread(this);
            gameThread.start();

    }

    // run() runs a game loop until no more pieces of tetris shapes can be placed
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null){
            update();
            repaint();
        
            try {
                
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0 ){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (KeyHandler.pausePressed == false && pm.gameOver == false) {
           pm.update();
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        pm.draw(g2);

    }

    


}
