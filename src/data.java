import java.util.*;
import java.text.*;
import java.io.*;

public class data {
    
    // Adds a vector to a dataframe
    public static double[][] BuildDF(double[][] df1, double[] row, int i){
        df1[i] = row;
        return df1;
    }

    // Used for Regression where 1.0 is added to the beginning of each row in order to 
    // compute the intercept for it
    public static double[][] RegBeta(double[][] X){
        double[][] Y = new double[X.length][X[0].length + 1];
        for(int i = 0; i < Y.length; i++){
            for(int j = 0; j < Y[0].length; j++){
                if(j == 0){
                    Y[i][j] = 1.0;
                } else {
                    Y[i][j] = X[i][j-1];
                }
            }
        }
        return Y;
    }

    // Extracts a numeric column from the dataframe
    public static double[] extract_column(String[][] data, String col_name){
        double[] res = new double[data.length - 1];
        String[] cols = data[0];
        int col_index = 0;
        for(int i = 0; i < cols.length; i++){
            if(cols[i].equalsIgnoreCase(col_name)){
                col_index = i;
                break;
            }
        }

        String[][] dfT = TransposeDF(data);
        for(int i = 1; i < data.length; i++){
            res[i-1] = Double.parseDouble(dfT[col_index][i]);
        }

        return res;
    }

    // Transposes a dataframe
    public static String[][] TransposeDF(String[][] df){
        String[][] result = new String[df[0].length][df.length];
        for(int i = 0; i < df.length; i++){
            for(int j = 0; j < df[0].length; j++){
                result[j][i] = df[i][j];
            }
        }
        return result;
    }

    // Reads a CSV file
    public static String[][] CSV(String filename) throws Exception {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
  
        // Determine the number of rows and columns in the CSV file
        int rows = 0;
        int cols = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            cols = Math.max(cols, line.split(",").length);
            rows++;
        }
  
        // Create a 2D array to store the CSV data
        String[][] data = new String[rows][cols];
  
        // Reset the scanner to read from the beginning of the file
        scanner = new Scanner(file);
  
        // Read each line of the file and parse the data
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            for (int col = 0; col < parts.length; col++) {
                data[row][col] = parts[col];
            }
            row++;
        }
  
        scanner.close();
  
        return data;
    }
}
