import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class WordSearch{
    private char[][] data;

    //the random seed used to produce this WordSearch
    private int seed;

    //a random Object to unify your random calls
    private Random randgen;

    //all words from a text file get added to wordsToAdd, indicating that they have not yet been added
    private ArrayList<String> wordsToAdd;

    //all words that were successfully added get moved into wordsAdded.
    private ArrayList<String> wordsAdded;

    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    private int row;
    private int col;

    public WordSearch(int rows,int cols){
      if (rows >= 0 && cols >= 0){
        data = new char[rows][cols];
      }
      else{
        throw new IllegalArgumentException();
      }
      row = rows;
      col = cols;
      this.clear();
    }

    //    Choose a randSeed using the clock random
    public WordSearch( int rows, int cols, String fileName) {
      data = new char[rows][cols];
      clear();
      wordsAdded = new ArrayList<String>();
      wordsToAdd = new ArrayList<String>();
      randgen = new Random();
      seed = randgen.nextInt();
      readFile(fileName);
    }

    //  Use the random seed specified.
    public WordSearch( int rows, int cols, String fileName, int randSeed){
      data = new char[rows][cols];
      clear();
      wordsAdded = new ArrayList<String>();
      wordsToAdd = new ArrayList<String>();
      seed = randSeed;
      readFile(fileName);
    }

    private void readFile(String fileName){
      /*try{*/
        File file = new File(fileName);
        Scanner in = new Scanner(fileName);
        while(in.hasNext()){
          wordsToAdd.add(in.next());
        }
        /*addAllWords();
      }catch(FileNotFoundException e){
        System.out.println("File not found: " + fileName);
      }*/
    }

    /**Set all values in the WordSearch to underscores'_'*/
    private void clear(){
      for (int x = 0; x < row; x++){
        for (int y = 0; y < col; y++){
          data[x][y] = '_';
        }
      }
    }

    /**Each row is a new line, there is a space between each letter
     *@return a String with each character separated by spaces, and rows
     *separated by newlines.
     */
    public String toString(){
      String output = new String("");
      for (int x = 0; x < row; x++){
        for (int y = 0; y < col; y++){
          output += data[x][y] + " ";
        }
        output += "\n";
      }
      output += "Words: ";
      for(int x = 0; x < wordsAdded.size(); x++){
         if(x != wordsAdded.size() - 1){
           output += wordsAdded.get(x) + ",";
         }
         else{
           output += wordsAdded.get(x);
         }
       }
      return output;
    }


    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from left to right, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     * or there are overlapping letters that do not match, then false is returned
     * and the board is NOT modified.
     */
    private boolean addWordHorizontal(String word,int row, int col){
      if (row < 0 || col < 0 || col >= data[0].length || row >= data.length){
        return false;
      }
      if ((col + word.length()) > data[0].length){
        return false;
      }
      for (int x = 0; x < word.length(); x++){
          if (data[row][x + col] != '_' && data[row][x + col] != word.charAt(x)){
            return false;
          }
      }
      for (int y = 0; y < word.length(); y++){
        data[row][col + y] = word.charAt(y);
      }
      return true;
    }

   /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top to bottom, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     *and the board is NOT modified.
     */
    private boolean addWordVertical(String word,int row, int col){
      if (row < 0 || col < 0 || col >= data[0].length || row >= data.length){
        return false;
      }
      if ((row + word.length()) > data.length){
        return false;
      }
      for (int x = 0; x < word.length(); x++){
          if (data[row + x][col] != '_' && data[row + x][col] != word.charAt(x)){
            return false;
          }
      }
      for (int y = 0; y < word.length(); y++){
        data[row + y][col] = word.charAt(y);
      }
      return true;
    }

    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top left to bottom right, must fit on the WordGrid,
     *and must have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     */
    private boolean addWordDiagonal(String word,int row, int col){
       if (row < 0 || col < 0 || col >= data[0].length || row >= data.length){
         return false;
       }
       if ((row + word.length()) > data.length || (col + word.length()) > data[0].length){
         return false;
       }
       for (int x = 0; x < word.length(); x++){
           if (data[row + x][col + x] != '_' && data[row + x][col + x] != word.charAt(x)){
             return false;
           }
       }
       for (int y = 0; y < word.length(); y++){
         data[row + y][col + y] = word.charAt(y);
       }
       return true;
    }

    private boolean addWord( String word, int r, int c, int rowIncrement, int colIncrement){
      return true;
    }

    private void addAllWords() {

    }
}
