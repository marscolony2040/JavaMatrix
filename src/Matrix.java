import java.util.*;
import java.text.*;
import java.io.*;


public class Matrix {

  // Converts number to string
  public static String NtoS(double x){
    String y = Double.toString(x);
    return y;
  }

  // Successfully transfers matrix
  public static double[][] Transfer(double[][] X){
    double[][] Y = new double[X.length][X[0].length];
    for(int i = 0; i < X.length; i++){
      for(int j = 0; j < X[0].length; j++){
        Y[i][j] = X[i][j];
      }
    }
    return Y;
  }

  // Prints out single array
  public static void PrintA(double[] X){
    System.out.println("Vector Length: " + Integer.toString(X.length) + "\n");
    for(int i = 0; i < X.length; i++){
      System.out.println(X[i]);
    }
  }

  // Prints out your matrix
  public static void PrintM(double[][] X){
    System.out.println("Matrix Dimensions: " + Integer.toString(X.length) + ", " + Integer.toString(X[0].length));
    for(int i = 0; i < X.length; i++){
      for(int j = 0; j < X[i].length; j++){
        System.out.print(X[i][j]);
        System.out.print("\t");
      }
      System.out.println();
    }
    System.out.println();
  }

  // Creates a (n x n) grid
  public static double[][] Ngrid(double a, double b, int n, int c) {
    double[][] res = new double[n][n];
    double dv = (b - a) / (double) (n - 1);
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        if(c == 0){
          res[i][j] = a + (double) i * dv;
        } else {
          res[i][j] = a + (double) j * dv;
        }
      }
    }
    return res;
  }

  // Converts single array to (n x 1) vector
  public static double[][] Vector(double[] mmx){
    double[][] res = new double[mmx.length][1];
    for(int i = 0; i < mmx.length; i++){
      res[i][0] = mmx[i];
    }
    return res;
  }

  // Converts (n x 1) vector to single array
  public static double[] VArray(double[][] mmx){
    double[] res = new double[mmx.length];
    for(int i = 0; i < mmx.length; i++){
      res[i] = mmx[i][0];
    }
    return res;
  }

  // Adds or subtracts two matrices
  public static double[][] MOper(double[][] X, double[][] Y, double op){
    double[][] Z = new double[X.length][X[0].length];
    for(int i = 0; i < X.length; i++){
      for(int j = 0; j < Y.length; j++){
        Z[i][j] = X[i][j] + op*Y[i][j];
      }
    }
    return Z;
  }

  // Adds or subtracts a column vector from a matrix
  public static double[][] CVOper(double[][] X, double[][] a, double op) {
    double[][] Z = new double[X.length][X[0].length];
    for(int i = 0; i < X.length; i++){
      for(int j = 0; j < X[0].length; j++){
        Z[i][j] = X[i][j] + op*a[j][0];
      }
    }
    return Z;
  }

  // Transposes your matrix
  public static double[][] Transpose(double[][] X){
    double[][] Y = new double[X[0].length][X.length];
    for(int i = 0; i < X.length; i++){
      for(int j = 0; j < X[0].length; j++){
        Y[j][i] = X[i][j];
      }
    }
    return Y;
  }

  // Multiplies two matrices
  public static double[][] MultiplyMatrix(double[][] X, double[][] Y){
    double[][] Z = new double[X.length][Y[0].length];
    for(int i = 0; i < X.length; i++){
      for(int j = 0; j < Y[0].length; j++){
        Z[i][j] = 0.0;
        for(int k = 0; k < X[0].length; k++){
          Z[i][j] += X[i][k] * Y[k][j];
        }
      }
    }
    return Z;
  }

  // Row Reducing Function
  public static double[][] RowReduce(double[][] X){
    double[][] Z = Transfer(X);
    for(int i = 1; i < X.length; i++){
      for(int j = 0; j < i; j++){
        double A = Z[i][j];
        double B = Z[j][j];

        if(B == 0){
          double[] temp = new double[Z[i].length];
          for(int m = 0; m < Z[i].length; m++){
            temp[m] = Z[i][m];
            Z[i][m] = Z[j][m];
            Z[j][m] = temp[m];
          }
        }

        for(int k = 0; k < Z[i].length; k++){
          Z[i][k] = Z[i][k] - (A / B) * Z[j][k];
        }

      }
    }
    return Z;
  }

  // Diagonalizing Function
  public static double[][] Diagonalize(double[][] X, String direction) {
    double[][] Z = Transfer(X);
    if(direction.equalsIgnoreCase("Left") == true){
      return RowReduce(X);
    }
    if(direction.equalsIgnoreCase("Right") == true){
      return Transpose(RowReduce(Transpose(X)));
    }
    if(direction.equalsIgnoreCase("Triangular") == true){
      return Transpose(RowReduce(Transpose(RowReduce(X))));
    }
    return Z;
  }

  // Finds the inverse of a matrix
  public static double[][] InverseMatrix(double[][] X) {
    double[][] Z = Transfer(X);
    double[][] I = new double[X.length][X[0].length];
    double A, B, C, D;
    for(int i = 0; i < X.length; i++){
      I[i][i] = 1.0;
    }
    for(int i = 1; i < X.length; i++){
      for(int j = 0; j < i; j++){
        A = Z[i][j];
        B = Z[j][j];
        for(int k = 0; k < X[0].length; k++){
          Z[i][k] = Z[i][k] - (A/B)*Z[j][k];
          I[i][k] = I[i][k] - (A/B)*I[j][k];
        }
      }
    }
    for(int i = 1; i < X.length; i++){
      for(int j = 0; j < i; j++){
        C = Z[j][i];
        D = Z[i][i];

        for(int k = 0; k < X[0].length; k++){
          Z[j][k] = Z[j][k] - (C/D)*Z[i][k];
          I[j][k] = I[j][k] - (C/D)*I[i][k];
        }
      }
    }


    for(int i = 0; i < Z.length; i++){
      for(int j = 0; j < Z[0].length; j++){
        I[i][j] = I[i][j] / Z[i][i];
      }
    }

    return I;
  }

}