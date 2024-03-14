import javax.swing.JFrame;

class main {
    public static void main(String[] args) {

        // creating a frame
        JFrame frame = new JFrame("Java Tetris");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // adding panel sizing to the frame
        GamePanel gp = new GamePanel();
        frame.add(gp);
        
        frame.pack();

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        gp.LaunchGame();
    }
}