
import processing.core.*;
import g4p_controls.*;
import java.awt.Font;
import java.util.Random;
import javax.swing.JOptionPane;

//change name of class:
public class test extends PApplet {

    GButton nextb,startb;
    PImage home = loadImage("home.jpg");
    
    
    GOption options[] = new GOption[4];
    PImage images[] = new PImage[5];
    String imagfiles[] = {"charmander.gif", "jigglypuff.gif", "bulbasaur.gif", "picachu.gif", "squirtle.gif"};
    GToggleGroup optionGroup;
    GLabel Questionl, rightl, wrongl;
    String answer = "";
    int w = 0, r = 0;
    int num = -1;
    String letters[] = {"A", "B", "C", "D"};
    String thing = "Exit Game";
    String Questions[][] = {
        {"What is Charmanders main move", "Flame thrower", "Flame Tail", "Sword Dance", "Head Zap", "2"},
        {"What is Charmanders main move", "Flame thrower", "Flame Tail", "Sword Dance", "Head Zap", "1"},
        {"What is Charmanders main move", "Flame thrower", "Flame Tail", "Sword Dance", "Head Zap", "3"},
        {"What is Charmanders main move", "Flame thrower", "Flame Tail", "Sword Dance", "Head Zap", "4"},
        {"What is Charmanders main move", "Flame thrower", "Flame Tail", "Sword Dance", "Head Zap", "2"},};

    public void setup() {
        size(640, 500, JAVA2D);
        G4P.setGlobalColorScheme(2);
        for (int x = 0; x < 5; x++) {
            images[x] = loadImage(imagfiles[x]);
        }
        startb = new GButton(this,150,430,325,30);
        startb.setText("Click to start");
        nextb = new GButton(this, 150, 400, 325, 30);
        nextb.setText("Submit");
        nextb.setVisible(false);
        
        Questionl = makeLabel(50, 250, 525, "Question Goes Here", 24);
        wrongl = makeLabel(0, 460, 300, "Wrong: 0", 18);
        rightl = makeLabel(350, 460, 300, "Right: 0", 18);
        options[0] = makeOption(10, 300, "Pick A");
        options[1] = makeOption(320, 300, "Pick B");
        options[2] = makeOption(10, 340, "Pick C");
        options[3] = makeOption(320, 340, "Pick D");

        optionGroup = new GToggleGroup();
        for (int x = 0; x < 4; x++) {
            optionGroup.addControl(options[x]);

        }
        

    }
    public void startgame(){
    nextb.setVisible(true);
    startb.setVisible(false);
    Questionl.setVisible(true);
    rightl.setVisible(true);
    wrongl.setVisible(true);
    for (int x = 0; x < 4; x++) {
            options[x].setVisible(true);

        }
    setQuestion();
    
    }
    public void mousePressed() {
        for (int x = 0; x < 4; x++) {
            if(hitOption(options[x])){
                answer = ""+(x+1);
            }
        
        
        }
        if (hitButton(startb)){
            startgame();
        
        
        }
        if (hitButton(nextb)) {
            String bvalue = nextb.getText();
            if(nextb.getText().equals("Exit Game")){
                System.exit(0);}
            if(answer ==""){
                JOptionPane.showMessageDialog(this, "Answer the Question");
                return;            
            }
            if(answer.equals(Questions[num][5])){
                JOptionPane.showMessageDialog(this, "Correct");
                r++;
                rightl.setText("Right: "+r);       
            }
            else{
            int rightloc = Integer.parseInt(Questions[num][5]);
            String ranswer = Questions[num][rightloc];
            JOptionPane.showMessageDialog(this, "Wrong the right answer is:" +ranswer);
            w++;
            wrongl.setText("Wrong: "+w);     
            }
            setQuestion();
        }

    }

    public void setQuestion() {
        if (num == 4) {
            JOptionPane.showMessageDialog(this, "Game Over");
            nextb.setText("Exit Game");
            return;
           
        }
        num++;
        Questionl.setText(Questions[num][0]);
        for (int x = 0; x < 4; x++) {
            options[x].setText(Questions[num][x + 1]);
            options[x].setSelected(false);
        }

    }

    //this works with mousePressed to see if a button was pressed
    public boolean hitButton(GButton gb) {
        return mouseX >= gb.getX() && mouseX <= gb.getX() + gb.getWidth()
                && mouseY >= gb.getY() && mouseY <= gb.getY() + gb.getHeight() && gb.isEnabled();
    }

    //this also works with mousePressed to see if a GOption was pressed
    public boolean hitOption(GOption go) {
        if (mouseX >= go.getX() && mouseX <= go.getX() + go.getWidth()
                && mouseY >= go.getY() && mouseY <= go.getY() + go.getHeight()) {
            go.setSelected(true);
            return true;
        }
        return false;
    }

    //I made this method to make labels more easily
    //ex -> someLabel = makeLabel(100, 200, 150, "This is a label", 20);
    //              x,  y,  width,  text,    font size
    public GLabel makeLabel(int x, int y, int w, String text, int size) {
        GLabel xLabel = new GLabel(this, x, y, w, 30);
        xLabel.setOpaque(true);
        xLabel.setText(text);
        xLabel.setFont(new Font("Comic Sans MS", 0, size));
        xLabel.setVisible(false);
        return xLabel;
    }

    //this method makes GOptions more easily
    //can customize the font and the width
    public GOption makeOption(int x, int y, String text) {
        GOption xOption = new GOption(this, x, y, 300, 35);
        xOption.setOpaque(true);
        xOption.setText(text);
        xOption.setFont(new Font("Comic Sans MS", 0, 18));
        xOption.setVisible(false);
        return xOption;
    }

    public void draw() {
        background(252, 227, 3);
        if (num >= 0) {
            image(images[num], 200, 20);
        }
        else{
        image(home, 0,0);
        
        }
    }

    public static void main(String ags[]) {
        PApplet.main(new String[]{test.class.getName()});
    }

}
