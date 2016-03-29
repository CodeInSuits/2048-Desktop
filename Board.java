/*
 * Name:       Yinji Lu
 * Login:      cs8bjf
 * Date:       April 28th 2015
 * File:       Board.java
 * Source of Help:  Sahib Grover, Textbook
 *
 * This file is used to store the information of the game board, save it when
 * user exits, help to detect whether the moves are valid and add random tiles
 * to the board when possible.
 *          
 *
 *
 *     Sample Board
 *
 *     0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 *
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */



import java.util.*;
import java.io.*;



/*
 * Name:    Board
 * Purpose: To compose the elements that will be displayed in the game, the
 *          actual board with numbered tiles. Also, it is to help save the 
 *          board and add numbers to the board when there is room left. To 
 *          detect whether the game is  over and whether the moves user 
 *          inputs is valid. It also helps to construct board which will be
 *          initialized in file GameManger.java.
 *
 *
 *
 */
public class Board
{   
    //instance varibale
    //the first number that will be placed on the board
    public final int NUM_START_TILES = 2;
    
    
    //the number of file added to the board at the beginning
    public final int TWO_PROBABILITY = 90;
    
    
    //the size of the grid, the total number of the tile on the board would 
    //be GRID_SIZE*GRID_SIZE
    public final int GRID_SIZE;
    
    
    //A number that is not 0, 1, -1
    public static final int RANGE = 100;
    
    
    //A numbered tile with number 2
    public static final int TILE_TWO = 2;
    
    
    //A numbered tile with number 4
    public static final int TILE_FOUR = 4;

    
    
    //Random class that will help to generate random number
    private final Random random;
    
    
    //the integer array array that composes the board
    //the number on the board would be based on the order of grid[row][column]
    private int[][] grid;
    
    
    //the score that user obtained so far
    private int score;

    
    
    /*
     * Name:      Board - constructor
     * Purpose:   construct a board with a grid of numbers    
     * Parameter: int boardSize - the length of board
     *            Random random - used to generate random number
     *         
     *
     */
    public Board(int boardSize, Random random)
    {
        
        
        //assign the passed random to the field of random
        this.random = random;
        
        
        //assign the passed boardSize to the filed of GRID_SIZE
        GRID_SIZE = boardSize;
        
        
        //initialized a new board with the size of boardSize*boardSize
        grid = new int[boardSize][boardSize];
        
        
        //add some random tiles to the board at the beginning of the game
        //based on NUM_START_TILES 
        for(int i=0;i<NUM_START_TILES;i++)
        {
          this.addRandomTile();
        } 
    }

    
    
    /*
     * Name:      Board - constructor
     * Purpose:   Constructor that will construct board with the content
     *            of an input board
     * Parameter: String inputBoard - the file name of the board to be input
     *            Random random - an random object that is to be used to make 
     *            random number
     *         
     *
     */
    public Board(String inputBoard, Random random) throws IOException
    {
        
        
        //assing the passed random to the field of random
        this.random = random;
        
        
        //use scanner to read in the content of the passed file
        Scanner in = new Scanner(new File(inputBoard));
        
        
        //assign the first number read from the file to GRID_SIZE
        GRID_SIZE = in.nextInt();
        
        
        //assign the second number read from the file to score
        score = in.nextInt();
        
        
        //initialize a board of the same size to prepare for assignment
        int[][] nwGrid = new int[GRID_SIZE][GRID_SIZE];
        
        
        //use a for loop to go through the passed file and assign each value
        //read from the board in the file to the new initialized board based
        //on the same order
        for(int i=0;i<GRID_SIZE;i++)
        {
          for(int z=0;z<GRID_SIZE;z++)
          {
            nwGrid[i][z] = in.nextInt();
          }
        }
        //assign the changed nwGrid to the filed of grid
        this.grid = nwGrid;
    }

    
    
    /*
     * Name:      saveBoard
     * Purpose:   Saves the current board to a file 
     * Parameter: String outputBoard - the string that would be the file name
     * Return:    Void 
     *         
     *
     */
     public void saveBoard(String outputBoard) throws IOException
     {
       
       
       //with the name of the passed string
       PrintWriter out = new PrintWriter(new File(outputBoard));
       
       
       //first print the GRID_SIZE into the file and terminate the line
       out.println(GRID_SIZE);
       
       
       //second print the score without terminating the line
       out.print(score);
       
       
       //use nested for loop to print each number in the grid into the file
       //with space in between and each row starts at a different line
       for(int row=0;row<GRID_SIZE;row++)
       {
         //whenever start a new row, terminate the last lien and start a
         //new line
         out.println();
         for(int column=0;column<GRID_SIZE;column++)
         {
           //print the numbers in the same row on one line
           out.print(this.grid[row][column]+" ");
         }
       }
       
       
       //print empty line to match the correct result
       out.println();
       //close the printWriter in order to save the changed file
       out.close();
    }
     
    
    
    /*
     * Name:      addRandomTile
     * Purpose:   Adds a Random Tile (of value 2 or 4) to a Random Empty space 
     *            on the board  
     * Parameter: None
     * Return:    Void 
     *         
     *
     */
    public void addRandomTile()
    {
       //first count the number of tiles on the board that are 0
       int count = 0;
       
       
       
       //use a nested for loop to go through the file to search each numbers 
       //in the grid and increase the count whenever the number is 0
       for(int row=0;row<GRID_SIZE;row++)
       {
          for(int column=0;column<GRID_SIZE;column++)
          {
             if(this.grid[row][column]==0)
             {
               count++;
             }
          }
       }
       
       
       
       //proceed if there are still numbers in the gird that are 0
       if(count!=0)
       { 
         //to create a random object in order to generate random number
         Random generator = this.random;
         //pick a random integer number from 0 and count - 1
         int location = generator.nextInt(count);
         //assign the first location of the first searched 0 to be 0
         int currLocation = 0;
         //use a nested loop to search through all the elements that are 0
         
         
         
         for(int row=0;row<GRID_SIZE;row++)
         {
          
          
          
           for(int column=0;column<GRID_SIZE;column++)
           {
             
             
             //proceed only if the currently searched element is 0
             if(this.grid[row][column]==0)
             {
               //to check whether the currently searched element has the 
               //location to be found. If so, proceed
               
               
               if(location==currLocation)
               {
                 //to generate a number between 0 and 99
                 int value = generator.nextInt(this.RANGE);
                 //add a 2 on the board if the generated number is in between
                 //0-89
                 
                 
                 if(value<TWO_PROBABILITY)
                 {
                   this.grid[row][column] = this.TILE_TWO;
                 }
                 //add a 4 on the board if the generated number is in between
                 //90-99
                 
                 
                 else
                 {
                   this.grid[row][column] = this.TILE_FOUR;
                 }
                 //quit the nested loop
                 column = GRID_SIZE;
                 row = GRID_SIZE;

               }
               
               
               
               //if the currently searched 0 is not on the location to be 
               //found. Increase currLocation to be the location of the 
               //next searched 0
               else
               {
                 currLocation++;
               }
             
             }
           
           }

         }
       //the end of outer loop to check whether there is 0 left on the board  
       }
       
       
       //if there is no 0 left on the board, simply end the method
       else
       {
         return;
       }

    }
    
    
   
    /*
     * Name:      isGameOver
     * Purpose:   check whether there is valid move left    
     * Parameter: None
     * Return:    boolean - return true if there is valid move left
     *            if there is none, return false
     *         
     *
     */
    public boolean isGameOver()
    {
      //check whether can move to each direction
      //if any direction works return false
      if(this.canMoveRight())
      {
        return false;
      }
      
      
      
      else if(this.canMoveLeft())
      {
        return false;
      }
      
      
      
      else if(this.canMoveDown())
      {
        return false;
      }
      
      
      
      else if(this.canMoveRight())
      {
        return false;
      }
      
      
      
      //else return true
      return true;
    }

    
    
    /*
     * Name:      canMoveUp
     * Purpose:   to check whether user can use command w  
     * Parameter: None
     * Return:    boolean - return true if the move is valid
     *            if not, return false
     *         
     *
     */
    private boolean canMoveUp()
    {
      //go through the column
      for(int column=0;column<GRID_SIZE;column++)
      {
        
        
        //assign previous to the first value of the column
        int previous = this.grid[0][column];
        //go through the row
        
        
        for(int row=0;row<GRID_SIZE;row++)
        {
          
          
          //avoid repreat assigning the first value of the column
          if(row!=0)
          {
            
            
            //get the value of the current coordinates
            int currNum = this.grid[row][column];
            
            
            //use can merge to see whether the two number can be combined
            boolean result = canMerge(previous,currNum);
            
            
            //if they can return true
            if(result)
            {
              return true;
            }
            
            
            //if they can not assign previous to current value
            else
            {
              previous = currNum;
            }
          
          }
        
        }
      
      }
      
      
      
      //if none of the number can merge return false
      return false;
    }

    
    
    /*
     * Name:      canMoveDown
     * Purpose:   to check whether user can use command s
     * Parameter: None
     * Return:    boolean - return true if the move is valid
     *            if not, return false
     *         
     *
     */
    private boolean canMoveDown()
    {
      //go through the column
      for(int column=0;column<GRID_SIZE;column++)
      {
       
       
        //assign the last value of the column to preivou
        int previous = this.grid[GRID_SIZE-1][column];
        
        
        //go through the row
        for(int row=GRID_SIZE-1;row>=0;row--)
        {
          
          
          //avoid repreat assigning previous
          if(row!=GRID_SIZE-1)
          {
            
            
            //assign currently searched number to currNum
            int currNum = this.grid[row][column];
            
            
            //check whether the two tiles can merge
            boolean result = canMerge(previous,currNum);
            
            
            //if they can return true
            if(result)
            {
              return true;
            }
            
            
            //if not assign previous to the current number
            else
            {
              previous = currNum;
            }
          
          }
        
        
        }
      
      
      }
      
      
      //return false if nothing works
      return false;
    }

    
    
    /*
     * Name:      canMoveUp
     * Purpose:   to check whether user can use command d 
     * Parameter: None
     * Return:    boolean - return true if the move is valid
     *            if not, return false
     *         
     *
     */
    private boolean canMoveRight()
    {
      
      
      //go through the row
      for(int row=0;row<GRID_SIZE;row++)
      {
        
        
        //assign the last number in the row to previous
        int previous = this.grid[row][GRID_SIZE-1];
        
        
        //go through the column
        for(int column=GRID_SIZE-1;column>=0;column--)
        {
          
          
          //avoid repeat assigning value
          if(column!=GRID_SIZE-1)
          {
            
            //get the current value based on the coordinate moved to
            int currNum = this.grid[row][column];
            
            //check whether currNum and previous can merge
            boolean result = canMerge(previous,currNum);
            
            
            //if possible return true
            if(result)
            {
              return true;
            }
            
            
            //else assign currNum to previous
            else
            {
              previous = currNum;
            }
          
          }
        
        }
      
      }
      
      
      //if none of the combination works, return false
      return false;
    }

    
    
    /*
     * Name:      canMoveUp
     * Purpose:   to check whether user can use command w
     * Parameter: None
     * Return:    boolean - return true if the move is valid
     *            if not, return false
     *         
     *
     */
    private boolean canMoveLeft()
    {
      
      //go throug the row
      for(int row=0;row<GRID_SIZE;row++)
      { 
        
        
        //assign the first value in the row to be previous
        int previous = this.grid[row][0]; 
        
        //go through column
        for(int column=0;column<GRID_SIZE;column++)
        {
          
          //avoid repeat assigning value
          if(column!=0)
          {
            
            //assign currNum based on coordinate moved to
            int currNum = this.grid[row][column];
            
            //check whether currNum and previous can merge
            boolean result = canMerge(previous,currNum);
            
            //if possible return true
            if(result)
            {
              return true;
            }
            
            //else assign currNum to previous
            else
            {
              previous = currNum;
            }

          }
        
        }
      
      }
      
      //if nothing works, return false
      return false;
    }


    
    /*
     * Name:      canMerge
     * Purpose:   To see whether two tile can merge
     * Parameter: int previous - the last number of tile
     *            int currNum - the currently searched number of tile
     * Return:    boolean - return true if the move is valid
     *            if not, return false
     *         
     *
     */
    private static boolean canMerge(int previous, int currNum)
    {
       
       //can merge only when both numbers are non-zero and the same
       if(previous==currNum&&previous!=0&&currNum!=0)
       {
         return true;
       }
       
       
       //can merge if previous is 0 and currNum is non-zero 
       if(previous==0&&currNum!=0)
       {
         return true;
       }
       
       
       //return false if none fulfills the above condition
       return false;
    }
    
    
    
    /*
     * Name:      canMove
     * Purpose:   Determine if we can move in a given direction
     * Parameter: Direction direction - the direction user wants to move tile
     * Return:    boolean - return true if the move is valid, false if not
     *
     */
    public boolean canMove(Direction direction)
    {
      
      //check whether can move up
      if(direction.equals(Direction.UP))
      { 
        if(this.canMoveUp())
        {
          return true;
        }
      }

      
      //check whether can move down
      if(direction.equals(Direction.DOWN))
      {
        if(this.canMoveDown())
        {
          return true;
        }
      }

      
      //check whether can move right
      if(direction.equals(Direction.RIGHT))
      {
        if(this.canMoveRight())
        {
          return true;
        }
      }

      
      //check whether can move left
      if(direction.equals(Direction.LEFT))
      {
        if(this.canMoveLeft())
        {
          return true;
        }
      }

      
      //return false if can move no where
      return false;
    }


    
    /*
     * Name:        moveLeft 
     * Purpose:     combine the same numbered tile and move them to the left 
     *              side 
     *              of the board
     * Parameter:   None
     * Return:      Void - return nothing
     *
     */
    public void moveLeft()
    {
      
      //this nested loop will help to combine same numbered tiles
      for(int row=0;row<GRID_SIZE;row++)
      {
        
        
        //make the first number of the row first previous
        int previous = this.grid[row][0];
        
        //go through the column
        for(int column=0;column<GRID_SIZE;column++)
        {
          //starting from the next tile to avoid repetition 
          if(column!=0)
          {
            //obtain the current tile
            int currNum = this.grid[row][column];
            
            
            //if both tiles are same numbered and not 0
            //combine them
            if(currNum!=0&&previous!=0&&currNum==previous)
            {
              this.grid[row][column-1] = previous + currNum;
              
              
              //this line is to eliminate the chance of second combination
              //after each time two tiles combine
              this.grid[row][column] = 0;
              
              
              //increase the score by the combined tile
              this.score = score + currNum + previous;
            }
            
            
            //move the tile until to the next numbered tile to see
            //whether they can be combined
            else if(previous!=0&&currNum==0)
            {
              this.grid[row][column] = previous;
              this.grid[row][column-1] = 0;
            }
            
            //set the previous to the current tile
            previous = this.grid[row][column];
          }
        }
       
      }
      
      
      //this nested loop will slide tiles to the end of the direction 
      for(int row=0;row<GRID_SIZE;row++)
      {
        for(int column=0;column<GRID_SIZE;column++)
        {
          
          //if the tile is empty, enter
          if(this.grid[row][column]==0)
          {
            
            
            for(int search=column;search<GRID_SIZE;search++)
            {
              
              //move the first non-zero tile to the 0 tile
              if(this.grid[row][search]!=0)
              {
                this.grid[row][column] = this.grid[row][search];
                this.grid[row][search] = 0;
                //quit the loop to avoid second assignment
                search = GRID_SIZE;
              }
            
            }
            
          
          }
        
        }
      
      }
    
    }
    
    
    
    
    /*
     * Name:           moveRight  
     * Purpose:        combine samed numbered tiles and move them to the 
     *                 right side of the board
     * Parameter:      None 
     * Return:         Void
     *
     */
    public void moveRight()
    {
      //this nested loop will help to combine same numbered tiles
      for(int row=0;row<GRID_SIZE;row++)
      {
        
        //make the last number of the row first previous
        int previous = this.grid[row][GRID_SIZE-1];
        for(int column=GRID_SIZE-1;column>=0;column--)
        {
          
          //starting from the next tile to avoid repetition 
          if(column!=GRID_SIZE-1)
          {
            
            //obtain the current tile
            int currNum = this.grid[row][column];
            
            //if both tiles are same numbered and not 0
            //combine them
            if(previous!=0&&currNum!=0&&previous==currNum)
            {
              this.grid[row][column+1] = previous + currNum;
              this.score = score + previous + currNum;
              this.grid[row][column] = 0;
            }
            
             
            //move the tile until to the next numbered tile to see
            //whether they can be combined
            else if(previous!=0&&currNum==0)
            {
              this.grid[row][column] = previous;
              this.grid[row][column+1] = 0;
            }
            
            
            //reassignment of the previous to current tile
            previous = this.grid[row][column];
          }
        
        }
      
      }
      
      //this nested loop will slide tiles to the end of the direction 
      for(int row=0;row<GRID_SIZE;row++)
      {
        
        for(int column=GRID_SIZE-1;column>=0;column--)
        {
          
          //if the tile is empty, enter
          if(this.grid[row][column]==0)
          {
            
            for(int search=column;search>=0;search--)
            {
              
              //move the first non-zero tile to the 0 tile
              if(this.grid[row][search]!=0)
              {
                this.grid[row][column] = this.grid[row][search];
                this.grid[row][search] = 0;
                
                //quite the loop to avoid repeated assignment
                search=0;
              }
            
            }
          
          }
        
        }
      
      }
    
    }

    
    
    /*
     * Name:              moveUp
     * Purpose:           combine the same numbered tile and move to the top 
     *                    side of the board
     * Parameter:         None
     * Return:            Void 
     *
     */
    public void moveUp()
    {
      
      //this nested loop will help to combine same numbered tiles
      for(int column=0;column<GRID_SIZE;column++)
      {
        
        //make the first number of the column first previous
        int previous = this.grid[0][column];
        for(int row=0;row<GRID_SIZE;row++)
        {
          
          //starting from the next tile to avoid repetition 
          if(row!=0)
          {
            
            int currNum = this.grid[row][column];
            
            //if both tiles are same numbered and not 0
            //combine them
            if(currNum!=0&&previous!=0&&previous==currNum)
            {
              
              this.grid[row-1][column] = currNum + previous;
              
              //increase the score by the combined tile
              this.score = score + currNum + previous;
              this.grid[row][column] = 0;
            }
            
            
            //move the numbered tile until it meets the next
            //numbered tile to check their similarity
            else if(previous!=0&&currNum==0)
            {
              this.grid[row-1][column] = 0;
              this.grid[row][column] = previous;
            }
            
            
            //reassignment the current tile to previous
            previous = this.grid[row][column];
          
          }
        
        }
      
      }

      
      //nested loop that will slide the tile to the end of the direction
      for(int column=0;column<GRID_SIZE;column++)
      {
        for(int row=0;row<GRID_SIZE;row++)
        {
          
          //enter if it is an empty tile
          if(this.grid[row][column]==0)
          {
            
            for(int search=row;search<GRID_SIZE;search++)
            {
             
              //move until the it meets a numbered tile
              if(this.grid[search][column]!=0)
              {
                this.grid[row][column] = this.grid[search][column];
                this.grid[search][column] = 0;
                
                //quit the loop to avoid reassignment
                search = GRID_SIZE;
              }
            
            }
          
          }
        
        }
      
      }
    
    }
    
    
    
    /*
     * Name:              moveDown
     * Purpose:           combine the same numbered tiles and move them to
     *                    the bottom of the board
     * Parameter:         None
     * Return:            Void
     *
     */
    public void moveDown()
    {
      //this nested loop is to combine tiles that have the same number
      for(int column=0;column<GRID_SIZE;column++)
      {
        
        
        //assign the last tile of the column to be previous
        int previous = this.grid[GRID_SIZE-1][column];
        for(int row=GRID_SIZE-1;row>=0;row--)
        {
          
          
          //start at the next tile to avoid repeated assigment
          if(row!=GRID_SIZE-1)
          {
            
            //obtain the current tile
            int currNum = this.grid[row][column];
            
            
            
            //combine the two tiles if they have the same number
            if(currNum!=0&&previous!=0&&currNum==previous)
            {
              
              this.grid[row+1][column] = previous + currNum;
              
              
              //increase the score by the combined tile
              this.score = score + currNum + previous;
              this.grid[row][column] = 0;
            }

            //move the numbered tile until the next numbered tile
            if(currNum==0&&previous!=0)
            {
              
              this.grid[row][column] = previous;
              this.grid[row+1][column] = 0;
            }
            
            
            //reassignment the current tile to previous
            previous = this.grid[row][column];
          
          }
        
        }
      
      }

      //to slide tile to the end of the direction inputted
      for(int column=0;column<GRID_SIZE;column++)
      {
        
        for(int row=GRID_SIZE-1;row>=0;row--)
        {
          
          
          //enter if the tile is empty
          if(this.grid[row][column]==0)
          {
            
            
            for(int search=row;search>=0;search--)
            {
              
              
              //slide the first numbered tile to the first empty space
              if(this.grid[search][column]!=0)
              {
                
                this.grid[row][column] = this.grid[search][column];
                this.grid[search][column] = 0;
                
                //quit the loop to avoid second assignment
                search = 0;
              }
            
            }
          
          }
        
        }
      
      }
    
    }
    


    /*
     * Name:         move
     * Purpose:      move the tiles based on the user input
     * Parameter:    Direction direction - the direction with which the tiles
     *               will be moving towards
     * Return:       boolean - whether the move performs successfully
     *
     */
    public boolean move(Direction direction)
    {
      
      //move right if entered command is d
      if(direction.equals(Direction.RIGHT))
      {
        this.moveRight();
      }
      
      
      //move up if entered command is w
      if(direction.equals(Direction.UP))
      {
        this.moveUp(); 
      }

      
      //enter left if entered command is a
      if(direction.equals(Direction.LEFT))
      {
        this.moveLeft();
      }

      
      //move down if entered command is s
      if(direction.equals(Direction.DOWN))
      {
        this.moveDown();
      }

      
      //return true after successful performance
      return true;
    }

    
    /*
     * Name:       getGrid
     * Purpose:    Return the reference to the 2048 Grid
     * Parameter:  None
     * Return:     int[][] - the current grid
     *
     */
    public int[][] getGrid()
    {
        return grid;
    }

    
    
    /*
     * Name:      getScore
     * Purpose:   return the current score
     * Parameter: None
     * Return:    int - the current score
     *
     */
    public int getScore()
    {
        return score;
    }

    
    
    /*
     * Name:      toString
     * Purpose:   to print out the board in specific format
     * Parameter: None
     * Return:    String - return the score and the grid itself
     *
     */
    public String toString()
    {
        //use string builder to store the board
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        //print the board with certain format
        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                                    String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        
        return outputString.toString();
    }
}
