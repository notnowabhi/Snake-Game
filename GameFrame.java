import javax.swing.JFrame;

public class GameFrame extends JFrame{

    GameFrame(){
        //GamePanel panel = new GamePanel();
        //this.add(panel);

        this.add(new GamePanel()); //creates a new instance
        this.setTitle("Snake"); // sets title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // tells what to do if user closes the window
        this.setResizable(false); // not resizable
        this.pack(); // packs all of this together
        this.setVisible(true); // makes the window visable
        this.setLocationRelativeTo(null); // makes the window appear in the middle
        

    }
}