import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;


public class Breakout extends WindowProgram {
    /** Width and height of application window in pixels */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /** Dimensions of game board (usually the same) */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /** Dimensions of the paddle */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /** Offset of the paddle up from the bottom */
    private static final int PADDLE_Y_OFFSET = 30;

    /** Number of bricks per row */
    private static final int NBRICKS_PER_ROW = 10;

    /** Number of rows of bricks */
    private static final int NBRICK_ROWS = 10;

    /** Separation between bricks */
    private static final int BRICK_SEP = 4;

    /** Width of a brick */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /** Height of a brick */
    private static final int BRICK_HEIGHT = 8;

    /** Radius of the ball in pixels */
    private static final int BALL_RADIUS = 10;

    /** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET = 70;

    /** Number of turns */
    private static final int NTURNS = 3;

                    /* Additional variables */

    /** Level */
    private static final double SPEED = 1;

    /** Acceleration */
    private static final double ACCELERATION = 5;

    /** This factor transfer the degrees to radians */
    private static final double RAD = Math.PI/180;

    /* number of control points of ball */

    /** �he maximum angle of reflection from paddle */
    private static final double MAX_ANGLE = 45;

    /* Determines movement by an axis X */
    double vx = 0;

    /* Determines movement by an axis Y */
    double vy = SPEED;

    /* Specifies quantity of the turns that else remained */
    int attempt = NTURNS;
    /* Specifies quantity of bricks */
    int NumberBricks = NBRICK_ROWS*NBRICKS_PER_ROW;

    /* factor that determines the change in the angle of reflection at a certain offset from center */
    double factor = PADDLE_WIDTH/(2*MAX_ANGLE);

    /* score of match */
    double SCORE = 0;

    public void run() {

        while (true) {
            mainMenu();
            addMouseListeners();
            playGame();
        }
    }

    GRect paddle = null;
    GOval ball = null;
    GLabel text = null;
    GLabel menu = null;

    /* Outputs the starting menu */
    private void mainMenu() {
        menu = new GLabel("Play");
        menu.setFont("Verdana -30");
        add(menu, (getWidth() - menu.getWidth()) / 2, (getHeight() + menu.getHeight()) / 2);
        waitForClick();
        removeAll();
    }

    /* Game realization */
    private void playGame() {
        /* setting (reset) parameters */
         vx = 0;
         vy = SPEED;
         attempt = NTURNS;
         NumberBricks = NBRICK_ROWS*NBRICKS_PER_ROW;
        SCORE = 0;

        createPaddle();
        createBrick();
        createLabel();
        createBall();
        waitForClick();
        while (attempt>0 && NumberBricks>0) {
            moveBall(ball);
            controlBall(ball);
            controlClash();
            controlLabel();
            pause(10);
        }
        finish();

        removeAll();
    }

    /* It creates a paddle and displays it in the center  */
    private void createPaddle() {
        paddle = new GRect((getWidth()+PADDLE_WIDTH)/2,getHeight()-PADDLE_Y_OFFSET-PADDLE_HEIGHT,PADDLE_WIDTH,PADDLE_HEIGHT);
        paddle.setFilled(true);
        paddle.setFillColor(Color.BLACK);
        add(paddle);
    }

    /* Creation of bricks */
    private void createBrick() {
        /* These are variables which determine coordinates with which creation of bricks begins */
        double X = (getWidth() - (NBRICKS_PER_ROW*(BRICK_WIDTH+BRICK_SEP)-BRICK_SEP))/2;
        double Y = BRICK_Y_OFFSET;

        for (int i =0; i<NBRICK_ROWS;i++) {
            for (int j = 0; j< NBRICKS_PER_ROW; j++ ){
                Color color = getColor(i);
                drawBrick(X+j*(BRICK_WIDTH+BRICK_SEP),Y+i*(BRICK_HEIGHT+BRICK_SEP), color);
            }
        }
    }

    private void createLabel() {
        text = new GLabel("Score: -.-      Lives -   Level: - ",getWidth()/20,getHeight()-PADDLE_Y_OFFSET/3);
        add(text);
    }

    /* Creation of ball  */
    private void createBall() {
        ball = new GOval((getWidth()- BALL_RADIUS)/2,(getHeight()- BALL_RADIUS)/2,BALL_RADIUS*2,BALL_RADIUS*2);
        ball.setFilled(true);
        ball.setFillColor(Color.MAGENTA);
        add(ball);

        /* Gives to a ball a random direction */
        RandomGenerator rgen = RandomGenerator.getInstance();
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5)) {
            vx = -vx;
        }
    }

    /* Moves a ball */
    private void moveBall(GOval ball) {
        /* Coefficient which is used for calculation of acceleration of a ball */
        int koef = 10000;

        if (vy > 0) {
            vy += ACCELERATION/koef;
        } else {
            vy -= ACCELERATION/koef;
        }

        ball.move(vx,vy);
    }

    /* Controls the movement of a ball */
    private void controlBall(GOval ball) {
        /* Cases when the ball concerns a wall */
        if (ball.getX()<0  && vx<0) { vx=-vx; }
        if (ball.getX()+BALL_RADIUS*2>getWidth() && vx>0) { vx=-vx; }
        if (ball.getY()<0) { vy=-vy; }

        /* Case when the player doesn't reflect a ball */
        if (ball.getY()+BALL_RADIUS*2>getHeight()) {
            attempt--; /* Reduces quantity of lives */
            controlLabel();
            /* restores a ball */
            if (attempt > 0) {
                remove(ball);
                createBall();
                waitForClick();
            }
        }
    }

    /* Controls collisions of a ball with objects */
    private void controlClash() {
        GObject collider = getCollidingObject();
        /* Collisions with a paddle */
        if (collider == paddle && ball.getY()+BALL_RADIUS*2-vy < collider.getY()){
            vy=-vy;

            /* Definition of a corner of reflection depending on shift from the center */
            double offset =(ball.getX()+BALL_RADIUS)-(paddle.getX()+PADDLE_WIDTH/2);
            double angle = offset/factor;
            vx = -Math.tan(angle*RAD)*vy;

        }
        /* Collisions with a brick */
        if(collider != paddle && collider !=null && collider !=text  ){

             SCORE+= getScore(collider.getColor()); // Determination of value of a brick
            remove(collider);
            vy=-vy;
            NumberBricks--;
        }
    }

        /* Controls of information string */
    private void controlLabel() {
        int level = (int)vy;
        if (level<0) {level *=-1;}
        text.setLabel("Score: " + SCORE + "    Lives: " + attempt + "     Level: " + level);
    }

    /* Prints information after completion of game */
    private void finish() {

        if (NumberBricks == 0 ){
            menu.setLabel(" Your win!!!  " +  "Score: " + (int)SCORE);
        }
        if (attempt == 0 ){
            menu.setLabel(" Your lose!!! " +  "Score: " +  (int)SCORE);
        }
        add(menu, (getWidth() - menu.getWidth()) / 2, (getHeight() + menu.getHeight()) / 2);
        waitForClick();
    }

    /**
     * Ties the movement of the rocket to the movement of a mouse
     *  and controls that the racket didn't go beyond a field
     */
    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent.getX()-PADDLE_WIDTH/2>0 && mouseEvent.getX()+PADDLE_WIDTH/2<WIDTH) {
            paddle.setLocation(mouseEvent.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
        }
    }

    /**
     * Set color of a brick.
     * @param i Indicates the row number in which the bricks.
     */
    private Color getColor(int i) {
        if (i == 0 || i== 1) { return Color.RED;}
        if (i == 2 || i== 3) { return Color.ORANGE;}
        if (i == 4 || i== 5) { return Color.YELLOW;}
        if (i == 6 || i== 7) { return Color.GREEN;}
        if (i > 7) { return Color.CYAN;}
        return Color.WHITE;
    }

    /**
     * Draws a brick on the set parameters.
     * @param x coordinate is Width.
     * @param y coordinate is Height.
     * @param c color of a brick.
     */
    private void drawBrick(double x, double y, Color c) {
        GRect brick = new GRect(x,y,BRICK_WIDTH,BRICK_HEIGHT);
        brick.setFilled(true);
        brick.setFillColor(c);
        brick.setColor(c);
        add(brick);
    }

    /**
     * Checks, whether there was a collision with object if yes - returns the link to this object
     * @return object to which there was a collision
     */
    private GObject getCollidingObject(){

        /* Checks four angular points of a ball */
        if (getElementAt(ball.getX(), ball.getY())!=null){
            return getElementAt(ball.getX(), ball.getY());
        }

        if (getElementAt(ball.getX()+BALL_RADIUS*2, ball.getY())!=null){
            return getElementAt(ball.getX()+BALL_RADIUS*2, ball.getY());
        }

        if (getElementAt(ball.getX(), ball.getY()+BALL_RADIUS*2)!=null){
            return getElementAt(ball.getX(), ball.getY()+BALL_RADIUS*2);
        }

        if (getElementAt(ball.getX()+BALL_RADIUS*2, ball.getY()+BALL_RADIUS*2)!=null){
            return getElementAt(ball.getX()+BALL_RADIUS*2, ball.getY() + BALL_RADIUS*2);
        }

        return null;
    }

    /**
     * Returns points depending on the color.
     * @param c color of object.
     * @return quantity of points for this color.
     */
    private double getScore(Color color) {
        if ( color == Color.RED ) { return  30; }
        if ( color == Color.ORANGE ) { return  20; }
        if ( color == Color.YELLOW ) { return  15; }
        if ( color == Color.GREEN ) { return  10; }
        if ( color == Color.CYAN ) {  return  5;  }
        return 1;
    }

}