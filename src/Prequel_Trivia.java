
import processing.core.*;
import g4p_controls.*;
import java.awt.Font;
import java.util.Random;
import javax.swing.JOptionPane;
import static processing.core.PConstants.JAVA2D;

//change name of class:
public class Prequel_Trivia extends PApplet {
    //buttons
    GButton nextb, startb, hintb;
    //images
    PImage home = loadImage("home.jpg");
    PImage back = loadImage("back.gif");
    PImage endimage;
    PImage images[] = new PImage[15];
    // checkbox things 
    GOption options[] = new GOption[4];
    
    // alll of the names of the images we use so they can be used in tjhe list of images
    String imagfiles[] = {"wookie.jpg", "pod.jpg", "darth.jpg", "farming.png",
        "alive.jpg", "66.jpg", "yes.jpg", "shoot.jpg", "treason.jpg",
        "sand.jpg", "itsover.jpg", "speak.jpg", "suprise.jpg", "brother.jpg", "hello.jpg"};
    GToggleGroup optionGroup;
    //labels
    GLabel Questionl, rightl, wrongl;
    String answer = "";
    //hoiw many u got right/wrong
    int w = 0, r = 0;
    //question number
    int num = -1;
    String letters[] = {"A", "B", "C", "D"};
    String thing = "Exit Game";
    String rank = "";
    //the list of hints
    String hints[] = {"It's not #1","It's not #2","It's not #3","It's not #2","It's not #2","It's not #1","It's not #4",
        "It's not #4","It's not #3","It's not #2","It's not #3","It's not #1","It's not #4","It's not #4","It's not #3" };
    //the list of questions 1-15
    String Questions[][] = {
        {"What about the Droid Attack on the ____", "Mandalorians", "Wookies", "Gungans", "Ewoks", "2"},
        {"Now this is ___________________", "the Olympics", "Back Fliping", "Light Saber Dueling", "Pod Racing", "4"},
        {"Have you heard of the tragedy of __________", "Darth Plaguis the Wise", "Darth Sidious", "Darth Vader", "Count Dooku", "1"},
        {"______, really a man of your talents", "Singing ", "Mining ", "Building", "Farming", "4"},
        {"He is to _______ to be left alive", "Happy", "Powerful", "Dangerous", "Scary", "3"},
        {"Execute Order ___", "34", "99", "69", " 66", "4"},
        {"Ah yes the __________", "General", "Negotiator", "Jedi", "Bold One", "2"},
        {"He cant do that! ____ them or something", "Stop", "Shoot", "Kill", "Capture", "2"},
        {"Its _____ then", "Treason", "A fight To The Death", "Time", "Time for you to leave", "1"},
        {"I dont like _______, its coarse and rough and iratating", "gravel", "snow", "sand", "dirt", "3"},
        {"Its over Anakin I have the ______", "Advantage", "High Ground", "Force with me", "Lightsaber", "2"},
        {"The ability to ___ does not make you intelligent", "think", "talk", "laugh", "speak", "4"},
        {"A ______ to be sure, but, a welcome one", "Disturbance", "Interuption", "Suprise", "Shock", "3"},
        {"You were like a _____ to me Anakin", "Father", "Grandfather", "Brother", "Uncle", "3"},
        {"Hello there, _________", "General Kenobi", "Master Anakin ", "Master Yoda", "Master Windu", "1"},};

    public void setup() {
        size(640, 640, JAVA2D);
        G4P.setGlobalColorScheme(7);
        for (int x = 0; x < 15; x++) {
            images[x] = loadImage(imagfiles[x]);
        }
        // make the hint button set the text and make it invisble and disbable it 
        hintb = new GButton(this, 50, 600, 125, 30);
        hintb.setText("Hint");
        hintb.setVisible(false);
        hintb.setEnabled(false);
        // make the sart button set the text and make it visble
        startb = new GButton(this, 150, 530, 325, 30);
        startb.setText("Click to start");
        // make the next button set the text and make it invisble and disbable it so you cant click 
        nextb = new GButton(this, 150, 500, 325, 30);
        nextb.setText("Submit");
        nextb.setVisible(false);
        nextb.setEnabled(false);
        //make the lables
        Questionl = makeLabel(50, 350, 525, "Question Goes Here", 24);
        wrongl = makeLabel(0, 560, 300, "Wrong: 0", 18);
        rightl = makeLabel(350, 560, 300, "Right: 0", 18);
        //make the options
        options[0] = makeOption(10, 400, "Pick A");
        options[1] = makeOption(320, 400, "Pick B");
        options[2] = makeOption(10, 440, "Pick C");
        options[3] = makeOption(320, 440, "Pick D");
        // put the options in a group so you cant click more then 1
        optionGroup = new GToggleGroup();
        for (int x = 0; x < 4; x++) {
            optionGroup.addControl(options[x]);

        }

    }

    public void startgame() {
        //make the nextbutton clickable and seeable
        nextb.setEnabled(true);
        nextb.setVisible(true);
        //make the start button unclickable and unseeable
        startb.setVisible(false);
        startb.setEnabled(false);
        //make the labels seeable
        Questionl.setVisible(true);
        rightl.setVisible(true);
        wrongl.setVisible(true);
        //make the hint button clickable and seeable
        hintb.setVisible(true);
        hintb.setEnabled(true);
        //make the option radios seeable
        for (int x = 0; x < 4; x++) {
            options[x].setVisible(true);

        }
        setQuestion();

    }

    public void mousePressed() {
        //for all 4 radios if anyone of them is hit make the answer variable 1,2,3 or 4 depending on which one is hit
        for (int x = 0; x < 4; x++) {
            if (hitOption(options[x])) {
                answer = "" + (x + 1);
            }

        }
        // IF THE HINT BUTTON IS PRESSED GIVE AN ALERT WITH THE  HINT CORROSPONING TO THAT QUESTION 
        if (hitButton(hintb)) {
            JOptionPane.showMessageDialog(this, hints[num]);
        }
        //if the start button is hot run the start function/method that will start the game
        if (hitButton(startb)) {
            startgame();

        }
        //if the next button is hit
        if (hitButton(nextb)) {
            //get the text the next button is set to 
            String bvalue = nextb.getText();
            // if the text the next button is set to "Exit Game" close the window
            if (nextb.getText().equals("Exit Game")) {
                System.exit(0);
            }
            // if not option was chosen tell the user to choose one
            if (answer == "") {
                JOptionPane.showMessageDialog(this, "Answer the Question");
                return;
            }
            // if the answer is the same as the 6th item in the questions list
            if (answer.equals(Questions[num][5])) {
                //tell the user correct and add 1 to right
                JOptionPane.showMessageDialog(this, "Correct");
                r++;
                rightl.setText("Right: " + r);
            } else {
                //find the right answern trun it into a string and say the right asnwer and a 1 to wrong
                int rightloc = Integer.parseInt(Questions[num][5]);
                String ranswer = Questions[num][rightloc];
                JOptionPane.showMessageDialog(this, "Wrong the right answer is:" + ranswer);
                w++;
                wrongl.setText("Wrong: " + w);
            }
            //reset the answer
            answer = "";
            setQuestion();
        }

    }

    public void end() {
        //set the buittons text to exit
        nextb.setText("Exit Game");
        //make labels and buttons hidden
        hintb.setVisible(false);
        Questionl.setVisible(false);
        rightl.setVisible(false);
        wrongl.setVisible(false);
        // hide radios
        for (int x = 0; x < 4; x++) {
            options[x].setVisible(false);

        }
        // if you got 0-3 image is outlaw
        if (r >= 0 && r <= 3) {
            rank = "Outlaw";
            endimage = loadImage("outlaw.png");
        }
        // if you got 4-6 image is yougnling
        if (r >= 4 && r <= 6) {
            rank = "Younglin";
            endimage = loadImage("young.jpg");
        }
        // if you got 7-10 image is padawan
        if (r >= 7 && r <= 10) {
            rank = "Padawan";
            endimage = loadImage("padawan.png");
        }
        // if you got 11-14 image is jhedi knight
        if (r >= 11 && r <= 14) {
            rank = "Jedi Knight";
            endimage = loadImage("knight.png");
        }
        // if you got perfectr image is master
        if (r == 15) {
            rank = "Jedi Master";
            endimage = loadImage("master.png");
        }
        
        num = 15;
        //  tells you what you got 
        JOptionPane.showMessageDialog(this,"You got " + r + " questions right out of 15");

    }

    public void setQuestion() {
        if (num == 14) {
            //if question 15 is answerd alert game over ands set a button to exit and run the end function
            JOptionPane.showMessageDialog(this, "Game Over");
            nextb.setText("Exit Game");
            end();
            return;

        }
        //add 1 to num set the question based on the num and set the options based on num and tthe 2d array
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
        GLabel xLabel = new GLabel(this, x, y, w, 50);
        xLabel.setOpaque(false);
        xLabel.setText(text);
        xLabel.setFont(new Font("Georgia", 0, size));
        xLabel.setVisible(false);

        return xLabel;
    }

    //this method makes GOptions more easily
    //can customize the font and the width
    public GOption makeOption(int x, int y, String text) {
        GOption xOption = new GOption(this, x, y, 300, 35);
        xOption.setOpaque(false);
        xOption.setText(text);
        xOption.setFont(new Font("Roboto", 0, 18));
        xOption.setVisible(false);
        return xOption;
    }

    public void draw() {
        background(181, 112, 91);
        // if it is not the start page and num is not after 15 set backround image s and set the image that is for the cuurrent questiion 
        if (num >= 0 & num != 15) {
            image(back, 0, 0);
            image(images[num], 100, 20);
            // if it is not during the gmae or after it set begining image
        } else if (num != 15) {
            image(home, 0, 0);
            // it is the end so draw the end question
        } else {
            image(endimage, 0, 0);
            

        }
    }

    public static void main(String ags[]) {
        PApplet.main(new String[]{Prequel_Trivia.class.getName()});
    }

}
