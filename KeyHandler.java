import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    public static boolean upKey, downKey, leftKey, rightKey, pausePressed;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upKey = true;
        } 
        if(code == KeyEvent.VK_A){
            leftKey = true;
        } 
        if(code == KeyEvent.VK_S){
            downKey = true;
        }
        if(code == KeyEvent.VK_D){
            rightKey = true;
        }
        if (code== KeyEvent.VK_SPACE) {
            if(pausePressed){
                pausePressed = false;
            } else {
                pausePressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
