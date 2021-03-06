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
      row = rows;
      col = cols;
      clear();
      wordsAdded = new ArrayList<String>();
      wordsToAdd = new ArrayList<String>();
      seed = randSeed;
      randgen = new Random(randSeed);
      readFile(fileName);
    }

    public static void main(String[] params){
      if (params.length < 3){
        System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
      }
      else if (params.length == 3){
        try{
          int numrows = Integer.parseInt(params[0]);
          int numcols = Integer.parseInt(params[1]);
          String fileName = params[2];
          Random rand = new Random();
          int randomseed = rand.nextInt() % 10001;
          if (randomseed < 0){
            randomseed *= -1;
          }
          if (randomseed > 10000){
            throw new Exception();
          }
          WordSearch ws = new WordSearch(numrows, numcols, fileName, randomseed);
          ws.fillRandomLetters();
          System.out.println(ws);
        }
        catch(Exception e){
          System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
        }
      }
      else if (params.length == 4){
        try{
          int numrows = Integer.parseInt(params[0]);
          int numcols = Integer.parseInt(params[1]);
          String fileName = params[2];
          int seed = Integer.parseInt(params[3]);
          if (seed < 0 || seed > 1000){
            throw new Exception();
          }
          WordSearch ws = new WordSearch(numrows, numcols, fileName, seed);
          ws.fillRandomLetters();
          System.out.println(ws);
        }
        catch(Exception e){
          System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
        }
      }
      else {
        try{
          int numrows = Integer.parseInt(params[0]);
          int numcols = Integer.parseInt(params[1]);
          String fileName = params[2];
          int seed = Integer.parseInt(params[3]);
          if (seed < 0 || seed > 1000){
            throw new Exception();
          }
          WordSearch ws = new WordSearch(numrows, numcols, fileName, seed);
          ws.formatAnswers();
          System.out.println(ws);
        }
        catch(Exception e){
          System.out.println("usage: java WordSearch [rows cols filename [randomSeed [answers]]]");
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
        output += "|";
        for (int y = 0; y < col; y++){
          output += data[x][y] + " ";
        }
        output += "|\n";
      }
      output += "Words: ";
      for(int x = 0; x < wordsAdded.size(); x++){
         if(x != wordsAdded.size()){
           output += wordsAdded.get(x) + ",";
         }
         else{
           output += wordsAdded.get(x);
         }
       }
       output += "\nSeed: " + seed;
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
      if (rowIncrement == 0 && colIncrement == 0){
        return false;
      }
      if (c >= data[0].length || r >= data.length){
        return false;
      }
      if ((r + word.length() * rowIncrement) > data.length || (c + word.length() * colIncrement) > data[0].length){
        return false;
      }
      if (((r + word.length() * rowIncrement) < 0) || (c + word.length() * colIncrement) < 0){
        return false;
      }
      int therow = r;
      int thecol = c;
      for (int x = 0; x < word.length(); x++){
           if (data[therow + rowIncrement][thecol + colIncrement] != '_' && data[therow + rowIncrement][thecol + colIncrement] != word.charAt(x)){
             return false;
           }
           therow += rowIncrement;
           thecol += colIncrement;
       }
      for (int y = 0; y < word.length(); y++){
         data[r + rowIncrement][c + colIncrement] = word.charAt(y);
         r += rowIncrement;
         c += colIncrement;
       }
      return true;
    }

    private void addAllWords() {
      String word;
      for (int x = 0; x < wordsToAdd.size(); x++){
        word = wordsToAdd.get(x);
        for (int y = 0; y < 1000; y++){
          int colIncrement = randgen.nextInt() % 2;
          int rowIncrement = randgen.nextInt() % 2;
          int r = randgen.nextInt() % row;
          int c = randgen.nextInt() % col;
          if (r < 0){
            r *= -1;
          }
          if (c < 0){
            c *= -1;
          }
          if (addWord(word.toUpperCase(), r, c, rowIncrement , colIncrement)){
            wordsAdded.add(word);
            wordsToAdd.remove(x);
            //x--;
            y = 1000;
          }
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
      int ran;
      char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
      for (int x = 0; x < data.length; x++)
        for (int y = 0; y < data[0].length; y++){
          ran = randgen.nextInt() % 26;
          if (ran < 0){
            ran *= -1;
          }
          if (data[x][y] == '_'){
            data[x][y] = alphabet[ran];
          }
        }
    }
}
