import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;

class Main {
  static List<String> phrases = new ArrayList<String>();
  //JFrame creates GUI
  static JFrame jFrame = new JFrame();
  //JLabel
  static JLabel label = new JLabel("");
  //Tells player if letter is in word
  static JLabel letterLabel = new JLabel("");
  //Where user inputs answer
  static JTextField textBox = new JTextField("");
  //The user's input
  static char userInput;
  //Image
  static JLabel imgLabel = new JLabel(new ImageIcon("hanging.png"));
  //Image
  static JLabel headLabel = new JLabel(new ImageIcon("head.png"));
  //Image
  static JLabel bodyLabel = new JLabel(new ImageIcon("body.png"));
  //Image
  static JLabel rarmLabel = new JLabel(new ImageIcon("rightArm.png"));
  //Image
  static JLabel larmLabel = new JLabel(new ImageIcon("leftArm.png"));
  //Image
  static JLabel rlegLabel = new JLabel(new ImageIcon("rightLeg.png"));
  //Image
  static JLabel llegLabel = new JLabel(new ImageIcon("leftLeg.png"));
  //Button to process text in textBox
  static JButton button = new JButton("Enter");
  //Boolean to determine if word is guessed
  static boolean wordIsGuessed = false;
  //Boolean to determine if text in textBox is processing
  static boolean buttonPressed = false;
  //To draw hangman on when letter is incorrect
  static int hangPerson = 0;
 
  //To create parts of the GUI
  public static void main(String[] args) {
    jFrame.setResizable(false);
    jFrame.setLayout(null);
    jFrame.setSize(700, 500);
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    label.setBounds(240, 150, 300, 500);
    letterLabel.setBounds(260, 230, 400, 400);
    textBox.setBounds(260, 350, 25, 25);
    textBox.setFocusable(true);
    button.setBounds(325, 350, 100, 25);
    imgLabel.setBounds(300, 100, 100, 200);
    headLabel.setBounds(347, 115, 50, 50);
    bodyLabel.setBounds(295, 160, 80, 75);
    rarmLabel.setBounds(343, 125, 90, 100);
    larmLabel.setBounds(310, 125, 90, 100);
    rlegLabel.setBounds(343, 200, 100, 100);
    llegLabel.setBounds(300, 200, 100, 100);
    jFrame.add(label);
    jFrame.add(letterLabel);
    jFrame.add(textBox);
    jFrame.add(button);
    jFrame.add(imgLabel);
    
    //Possible words for game
    String[] words = {"game", "random", "java", "coding", "method"};

    //To pick random word
    int randomWordNum = (int) (Math.random() * words.length);
    //Array of entered letters
    char[] enteredLetters = new char [words[randomWordNum].length()];
    
    //Number of tries user took
    int numTries = 0;

    //checkString method called when button clicked
    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        checkString();
      }
    });

    //textBox
    textBox.addActionListener(button.getActionListeners()[0]);
    //iterate as long as enterLeter is true
    //if returns false, user guessed letters
    while (!wordIsGuessed) 
    {
      switch (enterLetter(words[randomWordNum], enteredLetters))
      {
        case 0:
            numTries++;
            break;
        case 1:
            numTries++;
            break;
        case 2:
            break;
        case 3:
            wordIsGuessed = true;
            //Hide button and textbox
              button.setVisible(false);
              textBox.setVisible(false);
              break;
      }
    } 

    label.setText("\nThe word is " + words[randomWordNum]);
    letterLabel.setText("");
  }

  //returns 0 if letter is not in word
  //returns 1 if letter entered first time
  //returns 2 if letter was guessed previously
  //returns 3 if all letters guessed
  public static int enterLetter(String word, char[] enteredLetters)
  {
    //Previous text cleared
    phrases.clear();
    //Strings added to List
    phrases.add("Enter a letter in word ");

    //true if word is guessed
    if (!printWord(word, enteredLetters))
    {
      return 3;
    }

    String sent = "";
    for (int i = 0; i < phrases.size(); i++)
    {
      sent += phrases.get(i);
    }
    label.setText(sent);
    int emptyPosition = findEmptyPosition(enteredLetters);

    //to execute if text is being processed
    if(buttonPressed)
    {
      if (inEnteredLetters(userInput, enteredLetters))
      {
        //letterLabel displays what was inputed is already in word
        letterLabel.setText(userInput + " is already in the word");
        //word not being processed
        buttonPressed = false;

        return 2;
      }
      else if (word.contains(String.valueOf(userInput)))
      {
        enteredLetters[emptyPosition] = userInput;
        //empty to hide label
        letterLabel.setText("");
        //word not being processed
        buttonPressed = false;

        return 1;
      }
      else
      {
        //letterLabel to display letter is not in word
        letterLabel.setText(userInput + " is not in the word");
        //word not being processed
        buttonPressed = false;
        hangPerson++;
        if (hangPerson == 1) {
          jFrame.add(headLabel);
        }
        else if (hangPerson == 2) {
          jFrame.add(bodyLabel);
        }
        else if (hangPerson == 3) {
          jFrame.add(rarmLabel);
        }
        else if (hangPerson == 4) {
          jFrame.add(larmLabel);
        }
        else if (hangPerson == 5) {
          jFrame.add(rlegLabel);
        }
        else if (hangPerson == 6) {
          jFrame.add(llegLabel);
          label.setText("You lose! The correct word was " + word);
          letterLabel.setText("");
        }
        return 0;
      }
    }
    return 2;
    }


    //Prints boxes for letters not guessed
    //Returns false if not
    public static boolean printWord(String word, char[] enteredLetters)
    {
      boolean boxPrinted = false;
      for (int i=0; i < word.length(); i++)
      {
        //check to see if letter has been entered
        char letter = word.charAt(i);
        if (inEnteredLetters (letter, enteredLetters))
        {
          phrases.add(String.valueOf(letter));
        }
        else
        {
          //box added for letters nto guessed
          phrases.add("▯");
          boxPrinted = true;
        }
      }
      return boxPrinted;
    }

    //obtain information from user input
    public static void checkString()
    {
      //avoid errors in execution and compile
      try{
        userInput = textBox.getText().charAt(0);
      }
      catch (Exception err)
      {
        return;
      }
      //make sure user input is not empty
      if (userInput != ' ')
      {
        buttonPressed = true;
        //text deleted after input
        textBox.setText(null);
      }
    }

    //check letter
    public static boolean inEnteredLetters(char letter, char [] enteredLetters)
    {
      return new String(enteredLetters).contains(String.valueOf(letter));
    }

    public static int findEmptyPosition(char[] enteredLetters)
    {
      int i = 0;
      //loop through fucntion to find empty space
      for(int num = 0; num < enteredLetters.length; num++)
      {
        if(enteredLetters[num] == '\u0000') break;
        i++; 
      }
      return i;

    }

  }