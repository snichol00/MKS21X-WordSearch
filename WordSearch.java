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

    /*public WordSearch(int rows,int cols){
      if (rows >= 0 && cols >= 0){
        data = new char[rows][cols];
      }
      else{
        throw new IllegalArgumentException();
      }
      row = rows;
      col = cols;
      this.clear();
    }*/

    //    Choose a randSeed using the clock random
    /*public WordSearch( int rows, int cols, String fileName) {
      data = new char[rows][cols];
      clear();
      wordsAdded = new ArrayList<String>();
      wordsToAdd = new ArrayList<String>();
      randgen = new Random();
      seed = randgen.nextInt();
      readFile(fileName);
    }*/

    //  Use the random seed specified.
    public WordSearch( int rows, int cols, String fileName, int randSeed){
      data = new char[rows][cols];
      clear();
      wordsAdded = new ArrayList<String>();
      wordsToAdd = new ArrayList<String>();
      addAllWords();
      seed = randSeed;
      randgen = new Random(randSeed);
      readFile(fileName);

    }

    public static void main(String[] params){
      if (params.length < 3){
        System.out.println("Inadequete number of arguments");
      }
      else if (params.length == 3){
        try{
          int rows = Integer.parseInt(params[0]);
          int cols = Integer.parseInt(params[1]);
          String fileName = params[2];
          Random rand = new Random();
          int randomseed = rand.nextInt();
          WordSearch ws = new WordSearch(rows, cols, fileName, randomseed);
          ws.fillRandomLetters();
          System.out.println(ws);
        }
        catch(Exception e){
          System.out.println("Formatting error. Review instructions");
        }
      }
      else if (params.length == 4){
        try{
          int rows = Integer.parseInt(params[0]);
          int cols = Integer.parseInt(params[1]);
          String fileName = params[2];
          int seed = Integer.parseInt(params[3]);
          if (seed < 0 || seed > 1000){
            throw new Exception();
          }
          WordSearch ws = new WordSearch(rows, cols, fileName, seed);
          ws.fillRandomLetters();
          System.out.println(ws);
        }
        catch(Exception e){
          System.out.println("Formatting error. Review instructions");
        }
      }
      else {
        try{
          int rows = Integer.parseInt(params[0]);
          int cols = Integer.parseInt(params[1]);
          String fileName = params[2];
          int seed = Integer.parseInt(params[3]);
          if (seed < 0 || seed > 1000){
            throw new Exception();
          }
          WordSearch ws = new WordSearch(rows, cols, fileName, seed);
          ws.formatAnswers();
          System.out.println(ws);
        }
        catch(Exception e){
          System.out.println("Formatting error. Review instructions");
        }
      }
    }

    private void readFile(String fileName){
      try{
        File file = new File(fileName);
        Scanner in = new Scanner(file);
        while(in.hasNext()){
          wordsToAdd.add(in.next());
        }
        addAllWords();
      }catch(FileNotFoundException e){
        System.out.println("File not found: " + fileName);
      }
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
    /*private boolean addWordHorizontal(String word,int row, int col){
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
    }*/

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
    /*private boolean addWordVertical(String word,int row, int col){
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
    }*/

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
    /*private boolean addWordDiagonal(String word,int row, int col){
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
    }*/

    private boolean addWord( String word, int r, int c, int rowIncrement, int colIncrement){
      if (r < 0 || c < 0 || c >= data[0].length || r >= data.length){
        return false;
      }
      if ((r + word.length()) > data.length || (c + word.length()) > data[0].length){
        return false;
      }
      for (int x = 0; x < word.length(); x++){
           if (data[r + rowIncrement][c + colIncrement] != '_' && data[r + rowIncrement][c + colIncrement] != word.charAt(x)){
             return false;
           }
       }
       for (int y = 0; y < word.length(); y++){
         data[r + rowIncrement][c + colIncrement] = word.charAt(y);
       }
      return true;
    }

    private void addAllWords() {
      int colIncrement;
      int rowIncrement;
      int r;
      int c;
      while (wordsToAdd.size() > 0){
        colIncrement = randgen.nextInt() % 2;
        rowIncrement = randgen.nextInt() % 2;
        if (rowIncrement == 1){
          r = randgen.nextInt() % (row - wordsToAdd.get(0).length() + 1);
        }
        else if (rowIncrement == -1){
          r = randgen.nextInt() % (row - wordsToAdd.get(0).length() + 1) + wordsToAdd.get(0).length();
        }
        else{
          r = randgen.nextInt() % row;
        }
        if (colIncrement == 1){
          c = randgen.nextInt() % (col - wordsToAdd.get(0).length() + 1);
        }
        else if (colIncrement == -1){
          c = randgen.nextInt() % (col - wordsToAdd.get(0).length() + 1) + wordsToAdd.get(0).length();
        }
        else {
          c = randgen.nextInt() % col;
        }
        if (addWord(wordsToAdd.get(0), r, c, rowIncrement , colIncrement)){
          wordsAdded.add(wordsToAdd.get(0));
          wordsToAdd.remove(0);
        }
      }
    }

    public void formatAnswers(){
      for(int row=0; row<data.length; row++){
        for(int col=0; col<data[row].length; col++){
          if(data[row][col] == '_'){
            data[row][col] = ' ';
          }
        }
      }
    }

    private void fillRandomLetters(){
      int ran = randgen.nextInt() % 26;
      if (ran < 0){
        ran *= -1;
      }
      char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
      for (int x = 0; x < data.length; x++)
        for (int y = 0; y < data[0].length; y++){
          if (data[x][y] == '_'){
            data[x][y] = alphabet[ran];
          }
        }
    }
}
