import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_SIZE = (SCREEN_HEIGHT * SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_SIZE]; // holds the x coordinates of the snake x[0] and y[0] means head of the snake
    final int y[] = new int[GAME_SIZE]; // holds the y coordinates of the snake
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;



    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);

            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i=0; i<bodyParts; i++){ // drawing the snake
                if(i == 0){  // 0 MEANS THAT IT IS THE HEAD OF THE SNAKE
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }else{
                    g.setColor(new Color(45, 180, 0));
                    //g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }

                //g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                g.setColor(Color.white);
                g.setFont(new Font("", Font.BOLD, 40));
                FontMetrics metric = getFontMetrics(g.getFont());
                g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH - metric.stringWidth("SCORE: " + applesEaten))/2, g.getFont().getSize());
            }
        }else{ // if the game is running then fine, else gameOver() screen.
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; // idk why they have multiplied bu UNIT_SIZE.


    }
    public void move(){
        for(int i=bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;

        }

    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            newApple();
            bodyParts++;
            applesEaten++;
        }
    }
    public void checkCollisions(){
        // This check if head collides with the vody of the snake;
        for(int i=bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false; // the game will stop running;
            }
        }
        // This checks if the head of the snake collides with any of the borders.
        if(x[0] < 0){
            running = false;
        }
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        if(y[0] < 0){
            running = false;
        }
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running){ // if running is false
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        // display score on game over.
        g.setColor(Color.red);
        g.setFont(new Font("", Font.BOLD, 40));
        FontMetrics metric1 = getFontMetrics(g.getFont());
        g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH - metric1.stringWidth("SCORE: " + applesEaten))/2, g.getFont().getSize());

        // setup the gameover text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metric2 = getFontMetrics(g.getFont());
        g.drawString("Game Over!!", (SCREEN_WIDTH - metric2.stringWidth("Game Over!!"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){ // IF GAME RUNNING. will return boolean value, true for if running and false for if nor running.
            move();
            checkApple();
            checkCollisions();
        }
        repaint(); //if game not running
    }
    public class MyKeyAdapter extends KeyAdapter{ // inner class
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!= 'R'){
                        direction = 'L';
                        break;
                }
                case KeyEvent.VK_RIGHT:
                    if(direction!= 'L'){
                        direction = 'R';
                        break;
                    }
                case KeyEvent.VK_UP:
                    if(direction!= 'D'){
                        direction = 'U';
                        break;              
                    }

                case KeyEvent.VK_DOWN:
                    if(direction!= 'U'){
                        direction = 'D';
                        break;
                    }
                        
                    
            }
        }

    }
    
}