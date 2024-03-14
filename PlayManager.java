import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {
     
    // Play Window
    final int width = 360;
    final int height = 600;
    public static int leftX;
    public static int rightX;
    public static int topY;
    public static int bottomY;

    // Piece
    BlockTemplates currBlockTemplates;
    final int blockStartX;
    final int blockStartY;
    BlockTemplates nextBlockTemplates;
    final int nextBlockX;
    final int nextBlockY;
    public static int dropInterval = 60; 
    public static ArrayList<Block> staticBlocks = new ArrayList<>();
    boolean gameOver = false;

    // score
    int level = 1;
    int lines = 0;
    int score = 0;
    int lineCounter = 0; 

    public PlayManager() {
        leftX = (GamePanel.width/2) - (width/2);
        rightX = leftX + width;
        topY = 50;
        bottomY = topY + height;

        blockStartX = leftX + width/2 - Block.size;
        blockStartY = topY + Block.size;

        nextBlockX = rightX + 175;
        nextBlockY = topY + 500;

        // Set the current Mino
        currBlockTemplates = pickBlock();
        currBlockTemplates.setXY(blockStartX, blockStartY);
        nextBlockTemplates = pickBlock();
        nextBlockTemplates.setXY(nextBlockX, nextBlockY);
    }

    private BlockTemplates pickBlock() {
        BlockTemplates Block = null;
        int i = new Random().nextInt(7);
        
        switch(i) {
            case 0: Block = new BlockL1(); break;
            case 1: Block = new BlockL2(); break;
            case 2: Block = new BlockT(); break;
            case 3: Block = new BlockBar(); break;
            case 4: Block = new BlockZ1(); break;
            case 5: Block = new BlockZ2(); break;
            case 6: Block = new BlockSquare(); break;
        }
        return Block;
    }

    public void update(){

        if (currBlockTemplates.active == false) {
            staticBlocks.add(currBlockTemplates.b[0]);
            staticBlocks.add(currBlockTemplates.b[1]);
            staticBlocks.add(currBlockTemplates.b[2]);
            staticBlocks.add(currBlockTemplates.b[3]);

            if (currBlockTemplates.b[0].x == blockStartX && currBlockTemplates.b[0].y == blockStartY) {
                gameOver = true;
            }
            currBlockTemplates.deactive = false;

            currBlockTemplates = nextBlockTemplates;
            currBlockTemplates.setXY(blockStartX, blockStartY);
            nextBlockTemplates = pickBlock();
            nextBlockTemplates.setXY(nextBlockX, nextBlockY);

            checkDelete();
            
        } else {
            currBlockTemplates.update();
        }
        
    }
    private void checkDelete() {
        int x = leftX;
        int y = topY;
        int blockCount = 0;
        while (x < rightX && y < bottomY) {

            for (int i = 0; i < staticBlocks.size(); ++i)  {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    blockCount++;
                }
            }

            x += Block.size;
            if (x == rightX) {
                
                if (blockCount == 12) {
                    for (int i = staticBlocks.size() - 1; i > -1; --i) {
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    ++lines;
                    ++lineCounter;

                    if (lines % 10 == 10 && dropInterval > 1) {
                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval = 1;
                        }
                    }

                    for (int i = 0; i < staticBlocks.size(); ++i) {
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.size;
                        }
                    }
                }

                blockCount = 0;
                x = leftX;
                y += Block.size;

            }
        }
        if (lineCounter > 0) {
            score += lineCounter * level * 10;
            lineCounter = 0;
        }
    }
    // draw(g2) draws out all the attributes on the screen each time it is called
    public void draw(Graphics2D g2){
        // rectangle containing the main area
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(leftX-4, topY-4, width+8, height+8);

        // next piece frame
        g2.drawRect(rightX + 100, bottomY - 200, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.drawString("NEXT", rightX + 160, bottomY - 140);

        g2.drawRect(rightX + 100, topY, 300, 300);
        g2.drawString("Level: " + level, rightX + 140, topY + 80);
        g2.drawString("Score: " + score, rightX + 140, topY + 150);
        g2.drawString("Lines Cleared: " + lines, rightX + 140, topY + 230);

        // drawing the playing blocks
        if (currBlockTemplates != null) {
            currBlockTemplates.draw(g2);
            nextBlockTemplates.draw(g2);
        }

        for (int i = 0; i < staticBlocks.size(); ++i) {
            staticBlocks.get(i).draw(g2);
        }

        // Draw Pause
        g2.setColor(Color.YELLOW);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            g2.drawString("GAME OVER", leftX + 100, topY + 320);
        }
        if (KeyHandler.pausePressed) {
            g2.drawString("Paused", leftX + 100, topY + 320);
        }
    }
}