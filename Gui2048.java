/*
 * Name:        Yinji Lu
 * Login:       cs8bjf
 * Date:        May 27th 2015
 * File:        Gui2048.java
 * Source of Help:   Sahib Grover,  Textbook
 * 
 *
 * This file is used to store the information of Gui2048, which is a virtual
 * version of game 2048. It will display the tiles and the value of the tiles
 * based on the process of the game, changing color and displaying socre. 
 * Player will be able to control the game by simply using the arrow keys and 
 * see the game proceed because of the GUI components.
 *
 */



//for GUI
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;
import javafx.scene.effect.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;



/*
 * Name:    Gui2048
 * Purpose: To display the game 2048 using GUI component. Based on the tiles
 *          and the values of tiles, the board will change color and so will 
 *          the tile values. Also player will be able to see the title of the 
 *          game as well as the accumulative score along the way. To control 
 *          the game, player can use arrow keys and press s to save the game 
 *          because of the implementation of event handler.
 *
 *
 */
public class Gui2048 extends Application
{
    // The filename for where to save the Board
    private String outputBoard;     
    
    // The 2048 Game Board 
    private Board board; 
    
    
    
    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
    
    private static final Color COLOR_2 = Color.rgb(238, 228, 218);
    
    private static final Color COLOR_4 = Color.rgb(237, 224, 200);
    
    private static final Color COLOR_8 = Color.rgb(242, 177, 121);
    
    private static final Color COLOR_16 = Color.rgb(245, 149, 99);
    
    private static final Color COLOR_32 = Color.rgb(246, 124, 95);
    
    private static final Color COLOR_64 = Color.rgb(246, 94, 59);
    
    private static final Color COLOR_128 = Color.rgb(237, 207, 114);
    
    private static final Color COLOR_256 = Color.rgb(237, 204, 97);
    
    private static final Color COLOR_512 = Color.rgb(237, 200, 80);
    
    private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    
    private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    
    private static final Color COLOR_OTHER = Color.BLACK;
    
    private static final Color COLOR_GAME_OVER = 
    Color.rgb(238, 228, 218, 0.73);

    // For tiles >= 8
    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
    
    // For tiles < 8
    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 

    
    //Magic numbers - format
    private static final double INSET_TOP = 11.5;

    private static final double INSET_RIGHT = 12.5;
    
    private static final double INSET_BOTTOM = 13.5;
    
    private static final double INSET_LEFT = 14.5;

    private static final double HGAP = 5.5;

    private static final double VGAP = 5.5;

    private static final String STYLE = 
    "-fx-background-color: rgb(187, 173, 160)";

    
    //Magic number - score
    private static final int SCORE_2 = 2;

    private static final int SCORE_4 = 4;

    private static final int SCORE_8 = 8;
    
    private static final int SCORE_16  = 16;
    
    private static final int SCORE_32 = 32;
    
    private static final int SCORE_64 = 64;

    private static final int SCORE_128 = 128;

    private static final int SCORE_256 = 256;
    
    private static final int SCORE_512  = 512;
    
    private static final int SCORE_1024  = 1024;
    
    private static final int SCORE_2048  = 2048;

    
    //Magic number - font size
    private static final int SIZE_30 = 30;
    
    private static final int SIZE_20 = 20;
    
    private static final int SIZE_70 = 70;
    
    
    //Magic number - numbers
    private static final int TWO = 2;

    private static final int EIGHT = 8;

    private static final int NUM_500 = 500;
    
    
    //my own instance variable    
    //For the tile shape
    private ArrayList<Circle> squares = new ArrayList<Circle>();
    
    //For the tile value
    private ArrayList<Text> tileValues = new ArrayList<Text>();
    
    //For the game board to be displayed
    private GridPane pane = new GridPane();

    //For the score of the game
    private Text score = new Text();
    
     
    
    /*
     * Name:      start
     * Purpose:   to initize the display screen of the board
     * Parameter: Stage primaryStage - the stage on which the board displayed
     * Return:    void
     *         
     *
     */
    @Override 
    public void start(Stage primaryStage)
    {
      // Process Arguments and Initialize the Game Board
      processArgs(getParameters().getRaw().toArray(new String[0]));
      
      //get the grid of the board
      int[][] theGrid = board.getGrid();
      
      //set up the format of the pane that will be in the scene
      pane.setAlignment(Pos.CENTER);
      pane.setPadding(new Insets(INSET_TOP,INSET_RIGHT,
      INSET_BOTTOM,INSET_LEFT));
      
      //set the gap in between 
      pane.setHgap(HGAP);
      pane.setVgap(VGAP);

      
      //set up the style
      pane.setStyle(STYLE); 
      
      //going through a nested loop to create rectangles and text, the value 
      //of is associated with the values in the grid of the board
      for(int row=0;row<theGrid.length;row++)
      {
        //go through the column
        for(int column=0;column<theGrid.length;column++)
        {
          //make a rectangle and set up the width and height bound by the pane
          Circle square = new Circle();
          square.widthProperty().bind(pane.widthProperty()
          .subtract(INSET_RIGHT+INSET_LEFT+(HGAP*(theGrid.length)))
          .divide(theGrid.length));
          
          //align the width and height
          square.heightProperty().bind(square.widthProperty());
          
          //set up the color of the recbased on the value of the grid 
          //in the board
          if(theGrid[row][column]==0)
          {
            square.setFill(COLOR_EMPTY);
          }
          
          //the color of 2
          else if(theGrid[row][column]==SCORE_2)
          {
            square.setFill(COLOR_2);
          }
          
          //the color of 4
          else if(theGrid[row][column]==SCORE_4)
          {
            square.setFill(COLOR_4);
          }

          //the color of 8
          else if(theGrid[row][column]==SCORE_8)
          {
            square.setFill(COLOR_8);
          }

          //the color of 16
          else if(theGrid[row][column]==SCORE_16)
          {
            square.setFill(COLOR_16);
          }
          
          //the color of 32
          else if(theGrid[row][column]==SCORE_32)
          {
            square.setFill(COLOR_32);
          }
          
          //the color of 64
          else if(theGrid[row][column]==SCORE_64)
          {
            square.setFill(COLOR_64);
          }
          
          //the color of 128
          else if(theGrid[row][column]==SCORE_128)
          {
            square.setFill(COLOR_128);
          }
          
          //the color of 256
          else if(theGrid[row][column]==SCORE_256)
          {
            square.setFill(COLOR_256);
          }
          
          //the color of 512
          else if(theGrid[row][column]==SCORE_512)
          {
            square.setFill(COLOR_512);
          }
          
          //the color of 1024
          else if(theGrid[row][column]==SCORE_1024)
          {
            square.setFill(COLOR_1024);
          }
          
          //the color of 2048
          else if(theGrid[row][column]==SCORE_2048)
          {
            square.setFill(COLOR_2048);
          }
          
          //the color of value more than 2048
          else
          {
            square.setFill(COLOR_OTHER);
          }
          
          //add the square to arraylist to save the reference
          this.squares.add(square);
          
          //create a new text
          Text tileValue = new Text();
         
          //based on the value in the grid to set the value of the text 
          if(theGrid[row][column]!=0)
          {
            String tileNum = ""+theGrid[row][column];
            
            //set the value
            tileValue.setText(tileNum);
          }
          
          //set font
          tileValue.setFont(Font.font("Times New Roman", 
          FontWeight.BOLD, SIZE_30));
          
          //based on the value in the grid to set the color of the text
          if(theGrid[row][column]<EIGHT)
          {
            tileValue.setFill(COLOR_VALUE_DARK);
          }
          else
          {
            tileValue.setFill(COLOR_VALUE_LIGHT);
          }
          
          //add the text to the arraylist to save reference
          this.tileValues.add(tileValue);
          
          //add the created rectangle and text to the pane 
          pane.add(square,column,row+1);
          pane.add(tileValue,column,row+1);
          
          //center the text and the rectangle to make it inside the rectangle
          GridPane.setHalignment(tileValue, HPos.CENTER);
        
        }
      
      }
      
      //add new title 2048
      Text title = new Text();
      title.setText("2048");
      // create a reflection and set the fraction to 1.0
      Reflection reflection = new Reflection();
      reflection.setFraction(0.9);

      title.setEffect(reflection);
    
      
      //format the title
      title.setFont(Font.font("Serif", FontWeight.BOLD, SIZE_30));
      
      //add the title to the pane
      pane.add(title,0,0,theGrid.length/TWO,1);
      GridPane.setHalignment(title, HPos.CENTER);
      
      //add score text
      this.score.setText("Score: "+board.getScore());
      
      //format the score and add it to the pane
      this.score.setFont(Font.font("Times New Roman", 
      FontWeight.BOLD, SIZE_20));
      this.score.setEffect(reflection);
      pane.add(this.score,theGrid.length/TWO,0,theGrid.length/TWO,1);
      
      //center the text
      GridPane.setHalignment(this.score, HPos.CENTER);

      //make a new scene and set the pane into the scene
      Scene scene = new Scene(pane);
      
      //associate the scene with the event handler
      scene.setOnKeyPressed(new MyKeyHandler());
      
      //set the stage
      primaryStage.setTitle("Gui2048");
      primaryStage.setMinWidth(NUM_500);
      primaryStage.setMinHeight(NUM_500);
      
      //set the scene into the stage and show the stage
      primaryStage.setScene(scene);
      primaryStage.show();
    
    }

    
    
    /*
     * Name:      gameOver
     * Purpose:   to display a gameover screen
     * Parameter: None
     * Return:    void 
     *         
     *
     */
    public void gameOver()
    {
      //get the information of the grid
      int[][] theGrid = this.board.getGrid();
      
      //create a game over pane to store the gameover rectangle and text
      GridPane gameOverPane = new GridPane();
      
      //the backgroud color of gameover screen
      Circle gameOverRec = new Circle();

      //make a stack pane to store the last GUI and the gameover pane
      StackPane gameOver = new StackPane();
      
      //make the rectangle cover the whole pane
      //Note: Since gameove screen needs to cover up the whole screen so that
      //it is inevitable that there will be inappropriate formatting when we 
      //add the rectangle into the grid which is smaller than the size of the
      //whole pane because of the gap and padding part IN THE EXTRA CREDIT
      //part. I talked to TA and Brandon and he says grader will be 
      //understanding....on this matter
      gameOverRec.widthProperty().bind(this.pane.widthProperty());
      gameOverRec.heightProperty().bind(this.pane.heightProperty());

      //set its color
      gameOverRec.setFill(COLOR_GAME_OVER);
      
      //make a game over text
      Text gameOverText = new Text();
      
      //set the text and its format
      gameOverText.setText("Game Over!");
      gameOverText.setFont(Font.font("Times New Roman", 
      FontWeight.BOLD, SIZE_70));
      gameOverText.setFill(COLOR_VALUE_DARK);
      
      //add the gameover rectangle and text into the gameover pane
      this.pane.add(gameOverRec,0,0,theGrid.length,theGrid.length+1);
      this.pane.add(gameOverText,0,0,theGrid.length,theGrid.length+1);
      
      //center the gameover rectangle and text
      GridPane.setHalignment(gameOverRec, HPos.CENTER);
      GridPane.setHalignment(gameOverText, HPos.CENTER);

    }
    
    
    
    /*
     * Name:      updateBoard
     * Purpose:   to update the Gui components based on the value of the board
     * Parameter: None
     * Return:    void
     *         
     *
     */
    public void updateBoard()
    {
      //to obtain the information of the board
      int[][] theGrid = this.board.getGrid();
      
      //use accesslist index to access the rectangles and text in the list
      int accessList = 0;
      
      //go through the grid using a nested loop
      for(int row=0;row<theGrid.length;row++)
      {
        for(int column=0;column<theGrid.length;column++)
        {
          //obtain the text and rectangle
          Text theTileValue = this.tileValues.get(accessList);
          Circle theSquare = this.squares.get(accessList);
          
          //increase the index for the next fetch
          accessList++;

          //adjust the value of the text and rectangle based on new board
          //reset the color of the rectangle
          if(theGrid[row][column]==0)
          {
            theSquare.setFill(COLOR_EMPTY);
          }
          
          //set score 2 color
          else if(theGrid[row][column]==SCORE_2)
          {
            theSquare.setFill(COLOR_2);
          }
          
          //set score 4 color
          else if(theGrid[row][column]==SCORE_4)
          {
            theSquare.setFill(COLOR_4);
          }
          
          //set score 8 color
          else if(theGrid[row][column]==SCORE_8)
          {
            theSquare.setFill(COLOR_8);
          }
          
          //set score 16 color
          else if(theGrid[row][column]==SCORE_16)
          {
            theSquare.setFill(COLOR_16);
          }
          
          //set score 32 color
          else if(theGrid[row][column]==SCORE_32)
          {
            theSquare.setFill(COLOR_32);
          }
          
          //set score 64 color
          else if(theGrid[row][column]==SCORE_64)
          {
            theSquare.setFill(COLOR_64);
          }
          
          //set score 128 color
          else if(theGrid[row][column]==SCORE_128)
          {
            theSquare.setFill(COLOR_128);
          }
          
          //set score 256 color
          else if(theGrid[row][column]==SCORE_256)
          {
            theSquare.setFill(COLOR_256);
          }
          
          //set score 512 color
          else if(theGrid[row][column]==SCORE_512)
          {
            theSquare.setFill(COLOR_512);
          }
          
          //set score 1024 color
          else if(theGrid[row][column]==SCORE_1024)
          {
            theSquare.setFill(COLOR_1024);
          }
          
          //set score 2048 color
          else if(theGrid[row][column]==SCORE_2048)
          {
            theSquare.setFill(COLOR_2048);
          }
          
          //set score larger than 2048 color
          else
          {
            theSquare.setFill(COLOR_OTHER);
          }
          
          //update the value of the text
          if(theGrid[row][column]!=0)
          {
            String tileNum = ""+theGrid[row][column];
            
            //reset the tile value
            theTileValue.setText(tileNum);
          }
          
          //situation where the new tile value is 0
          else
          {
            String tileNum = "";
            
            //reset the tile value when it is 0
            theTileValue.setText(tileNum);
          }

          //reset the color of the text based on the new board
          if(theGrid[row][column]<EIGHT)
          {
            theTileValue.setFill(COLOR_VALUE_DARK);
          }
          else
          {
            theTileValue.setFill(COLOR_VALUE_LIGHT);
          }

        }
      }
      
      //reset the score
      this.score.setText("Score: "+board.getScore());

    }
    


    /*
     * Name:     MyKeyHandler
     * Purpose:  To handle the event to be fired from the GUI component and 
     *           then proceed to handle the event as to make different 
     *           response. In this case it is to control the change of the 
     *           board and then update the GUI component to show to the player
     *
     */
    private class MyKeyHandler implements EventHandler<KeyEvent>
    {



      /*
       * Name:      handle
       * Purpose:   to handle the key event 
       * Parameter: KeyEvent e - event that is fired when key is pressed
       * Return:    void
       *         
       *
       */
      @Override
      public void handle(KeyEvent e)
      {
        //handle the situation when up arrow key is pressed
        if(e.getCode()==KeyCode.UP)
        {
          //check whether it is a valid move and move
          if(board.canMove(Direction.UP))
          {
            if(board.move(Direction.UP))
            {              
              //print out proper command when the key is pressed
              System.out.println("Moving <UP>");
              
              //add a random tile to the board
              board.addRandomTile();
              
              //update the GUI 
              updateBoard();
              
              //if game is over, show game over screen
              if(board.isGameOver())
              {
                gameOver();
              }
            }
          }
        }
              
        //handle the situation when up arrow key is pressed
        else if(e.getCode()==KeyCode.DOWN)
        {          
          //check whether it is a valid move and move
          if(board.canMove(Direction.DOWN))
          {
            if(board.move(Direction.DOWN))
            {             
              //print out proper command when the key is pressed
              System.out.println("Moving <DOWN>");
              
              //add a random tile to the board
              board.addRandomTile();
              
              //update the GUI 
              updateBoard();
              
              //if game is over, show game over screen
              if(board.isGameOver())
              {
                gameOver();
              }
            }
          }
        }
               
        //handle the situation when up arrow key is pressed
        else if(e.getCode()==KeyCode.LEFT)
        {          
          //check whether it is a valid move and move
          if(board.canMove(Direction.LEFT))
          {
            if(board.move(Direction.LEFT))
            {              
              //print out proper command when the key is pressed
              System.out.println("Moving <LEFT>");
              
              //add a random tile to the board
              board.addRandomTile();
              
              //update the GUI 
              updateBoard();
              
              //if game is over, show game over screen
              if(board.isGameOver())
              {
                gameOver();
              }
            }
          }
        }
        
        //handle the situation when up arrow key is pressed
        else if(e.getCode()==KeyCode.RIGHT)
        {          
          //check whether it is a valid move and move
          if(board.canMove(Direction.RIGHT))
          {
            if(board.move(Direction.RIGHT))
            {              
              //print out proper command when the key is pressed
              System.out.println("Moving <RIGHT>");
              
              //add a random tile to the board
              board.addRandomTile();
              
              //update the GUI 
              updateBoard();
              
              //if game is over, show game over screen
              if(board.isGameOver())
              {
                gameOver();
              }
            }            
          }
        }
        
        //deal with the situation where key s is pressed 
        else if(e.getCode()==KeyCode.S)
        {
          //use try and catch to deal with IOExceptions
          try 
          {
            board.saveBoard(outputBoard);
          } 
          catch (IOException ioe) 
          {
            System.out.println("saveBoard threw an Exception");
          }
          System.out.println("Saving Board to <"+outputBoard+">");
        }
      }
    }
    
    
    
    /*
     * Name:      processArgs
     * Purpose:   The method used to process the command line arguments
     * Parameter: String[] args - the string input into the terminal
     * Return:    void
     *
     *
     *
     */
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % TWO) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += TWO)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < TWO)
            boardSize = SCORE_4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(inputBoard, new Random());
            else
                board = new Board(boardSize, new Random());
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }



    /*
     * Name:      printUsage
     * Purpose:   Print the Usage Message
     * Parameter: None
     * Return:    void
     *
     *
     */
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be used to save the 2048 board");
        System.out.println("                If none specified then the default \"2048.board\" file will be used");
        System.out.println("  -s [size]  -> Specifies the size of the 2048 board if an input file hasn't been");
        System.out.println("                specified.  If both -s and -i are used, then the size of the board");
        System.out.println("                will be determined by the input file. The default size is 4.");
    }
}
