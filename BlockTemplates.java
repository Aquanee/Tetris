import java.awt.Color;
import java.awt.Graphics2D;

public class BlockTemplates {
    
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1; // 1 is 0 degrees, 2 is 90 degrees, 3 is 180 degrees, 4 is 270 degrees
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactive = false;
    int activeCounter = 0;

    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY(int x, int y) {}
    public void updateXY(int direction) {

        checkRotationCollision();

        if(leftCollision == false && rightCollision == false & bottomCollision == false) {
        this.direction = direction;
        b[0].x = tempB[0].x;
        b[0].y = tempB[0].y;
        b[1].x = tempB[1].x;
        b[1].y = tempB[1].y;
        b[2].x = tempB[2].x;
        b[2].y = tempB[2].y;
        b[3].x = tempB[3].x;
        b[3].y = tempB[3].y;


        }
    }
    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}
    public void checkMovementCollision() {
        
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        // left wall
        for (int i = 0; i < b.length; ++i) {
            if (b[i].x == PlayManager.leftX) {
                leftCollision = true;
            }
        }

        // right wall
        for (int i = 0; i < b.length; ++i) {
            if (b[i].x + Block.size == PlayManager.rightX) {
                rightCollision = true;
            }
        }

        // bottom floor
        for (int i = 0; i < b.length; ++i) {
            if (b[i].y + Block.size == PlayManager.bottomY) {
                bottomCollision = true;
            }
        }
    }
    public void checkRotationCollision() {

        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        // left wall
        for (int i = 0; i < b.length; ++i) {
            if (tempB[i].x < PlayManager.leftX) {
                leftCollision = true;
            }
        }

        // right wall
        for (int i = 0; i < b.length; ++i) {
            if (tempB[i].x + Block.size > PlayManager.rightX) {
                rightCollision = true;
            }
        }

        // bottom floor
        for (int i = 0; i < b.length; ++i) {
            if (tempB[i].y + Block.size > PlayManager.bottomY) {
                bottomCollision = true;
            }
        }
    }
    private void checkStaticBlockCollision() {
        for (int i = 0; i < PlayManager.staticBlocks.size(); ++i) {
            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            for (int j = 0; j < b.length; ++j) {
                if (b[j].y + Block.size == targetY && b[j].x == targetX) {
                    bottomCollision = true;
                }
            }

            for (int j = 0; j < b.length; ++j) {
                if (b[j].x - Block.size == targetX && b[j].y == targetY) {
                    leftCollision = true;
                }
            }

            for (int j = 0; j < b.length; ++j) {
                if (b[j].x + Block.size == targetX && b[j].y == targetY) {
                    rightCollision = true;
                }
            }
          }
    }
    public void update(){

        if(deactive) {
            deactive();
        }

        if(KeyHandler.upKey){
            switch(direction){
                case 1: getDirection2(); break;
                case 2: getDirection3(); break;
                case 3: getDirection4(); break;
                case 4: getDirection1(); break;
            }
            KeyHandler.upKey = false;
        } 

        checkMovementCollision();

        if(KeyHandler.downKey){
            if (bottomCollision == false) {
            b[0].y += Block.size;
            b[1].y += Block.size;
            b[2].y += Block.size;
            b[3].y += Block.size;

            autoDropCounter = 0;
            }

            KeyHandler.downKey = false;
            
        }
        if(KeyHandler.leftKey){
            if (leftCollision == false) {
            b[0].x -= Block.size;
            b[1].x -= Block.size;
            b[2].x -= Block.size;
            b[3].x -= Block.size;
            }

            KeyHandler.leftKey = false;
        }
        if(KeyHandler.rightKey){
            if (rightCollision == false) {
            b[0].x += Block.size;
            b[1].x += Block.size;
            b[2].x += Block.size;
            b[3].x += Block.size;
            }

            KeyHandler.rightKey = false;
        }

        if (bottomCollision) {
            active = false;
        } else {
            autoDropCounter++;
            if (autoDropCounter == PlayManager.dropInterval){
                b[0].y += Block.size;
                b[1].y += Block.size;
                b[2].y += Block.size;
                b[3].y += Block.size;
                autoDropCounter = 0;
            }
        }
    }
    private void deactive() {
        ++activeCounter;

        if (activeCounter == 45) {
            activeCounter = 0;
            checkMovementCollision();

            if (bottomCollision){
                active = false;
            }
        }
    }
    public void draw(Graphics2D g2) {
        int border = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x + border, b[0].y + border, Block.size - border*2, Block.size - border*2);
        g2.fillRect(b[1].x + border, b[1].y + border, Block.size - border*2, Block.size - border*2);
        g2.fillRect(b[2].x + border, b[2].y + border, Block.size - border*2, Block.size - border*2);
        g2.fillRect(b[3].x + border, b[3].y + border, Block.size - border*2, Block.size - border*2);

    }
}
