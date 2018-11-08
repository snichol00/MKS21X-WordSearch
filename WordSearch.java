public class WordSearch{
    private char[][] data;

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
    public boolean addWordHorizontal(String word,int row1, int col1){
      char[][] data2 = new char[row][col];
      for (int x1 = 0; x1 < row; x1++){
        for (int y1 = 0; y1 < col; y1++){
          data2[x1][y1] = data2[x1][y1];
        }
      }
      for (int x = 0; x < word.length(); x++){
        for (int y = 0; y < col1; y++){
          if (data2[row1][y] == '_' || data2[row1][y] == word.charAt(x)){
            data2[row1][y] = word.charAt(x);
          }
          else{
            return false;
          }
        }
        if (x != word.length() - 1){
          return false;
        }
      }
      data = data2;
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
    public boolean addWordVertical(String word,int row, int col){
      char[][] data2 = new char[row][col];
      for (int x1 = 0; x1 < row; x1++){
        for (int y1 = 0; y1 < col; y1++){
          data2[x1][y1] = data[x1][y1];
        }
      }
      for (int x = 0; x < word.length(); x++){
        for (int y = 0; y < row; y++){
          if (data2[col][y] == '_' || data2[col][y] == word.charAt(x)){
            data2[col][y] = word.charAt(x);
          }
          else{
            return false;
          }
        }
        //if (x != word.length() - 1){
          //return false;
        //}
      }
      data = data2;
      return true;
    }
}
