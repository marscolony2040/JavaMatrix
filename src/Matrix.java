import java.util.*;
import java.text.*;
import java.io.*;
import java.math.*;


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

  // Creates a single array grid bounded to a range
  public static double[] Sgrid(double a, double b, int n){
      double[] res = new double[n];
      double dx = (b - a) / (double) (n - 1);
      for(int i = 0; i < n; i++){
          res[i] = a + (double) i * dx;
      }
      return res;
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

  // Creates a zeros or ones vector
  public static double[][] Ones(int n, double coef){
      double[][] res = new double[1][n];
      for(int i = 0; i < n; i++){
          res[0][i] = coef;
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

  // Multiplies coefficient by matrix
  public static double[][] Ax(double a, double[][] X){
      double[][] Y = new double[X.length][X[0].length];
      for(int i = 0; i < X.length; i++){
          for(int j = 0; j < X[0].length; j++){
              Y[i][j] = a * X[i][j];
          }
      }
      return Y;
  }

  // Returns the diagonal of a matrix
  public static double[][] Diag(double[][] X){
      double[][] Y = new double[X.length][1];
      for(int i = 0; i < X.length; i++){
          Y[i][0] = X[i][i];
      }
      return Y;
  }

  // Roots or Powers Matrix
  public static double[][] ExponentMatrix(double[][] X, double e) {
      double[][] H = new double[X.length][X[0].length];
      for(int i = 0; i < X.length; i++){
          for(int j = 0; j < X[0].length; j++){
              H[i][j] = Math.pow(X[i][j], e);
          }
      }
      return H;
  }

  // Gives a number a Matrix as a power
  public static double[][] MxPower(double e, double[][] X){
      double[][] Z = Transfer(X);
      for(int i = 0; i < Z.length; i++){
          for(int j = 0; j < Z[0].length; j++){
              Z[i][j] = Math.pow(e, Z[i][j]);
          }
      }
      return Z;
  }

  // Takes the sum of a matrix
  public static double MxSum(double[][] X){
    double totalS = 0;
    for(int i = 0; i < X.length; i++){
      for(int j = 0; j < X[0].length; j++){
        totalS += X[i][j];
      }
    }
    return totalS;
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

  // Calculates the trace of a matrix
  public static double Trace(double[][] X){
    double sum_it = 0;
    for(int i = 0; i < X.length; i++){
      sum_it += X[i][i];
    }
    return sum_it;
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

  // Finds the determenant of a matrix
  public static double Determenant(double[][] X){
      double[][] Y = Diagonalize(X, "Triangular");
      double prod = 1.0;
      for(int i = 0; i < Y.length; i++){
          prod *= Y[i][i];
      }
      return prod;
  }


  // Sorts each column of a matrix in ascending or descending order
  public static double[][] Sorter(double[][] X, int c){
      double[][] Z = Transfer(X);
      int n = Z.length, m = Z[0].length;
      double[][] V = new double[n][m];
      double[] cld = new double[m];
      double[] gld = new double[m];
      int[][] idx = new int[n][2];

      for(int i = 0; i < m; i++){
          if(c == 0){
              cld[i] = -100000000;
              gld[i] = 100000000;
          } else {
              cld[i] = 100000000;
              gld[i] = -100000000;
          }
      }

      int[] px = new int[m];

      for(int i = 0; i < n; i++){
          for(int ii = 0; ii < n; ii++){
              for(int jj = 0; jj < m; jj++){
                  if(c == 0){
                      if(gld[jj] > Z[ii][jj] && Z[ii][jj] > cld[jj]){
                          cld[jj] = Z[ii][jj];
                      }
                  } else {
                      if(gld[jj] < Z[ii][jj] && Z[ii][jj] < cld[jj]){
                          cld[jj] = Z[ii][jj];
                      }
                  }
              }
          }

          for(int jj = 0; jj < m; jj++){
              gld[jj] = cld[jj];
              if(c == 0){
                cld[jj] = -100000000;
              } else {
                cld[jj] = 100000000;
              }
              for(int mm = 0; mm < n; mm++){
                  if(Z[mm][jj] == gld[jj]){
                      V[px[jj]][jj] = Z[mm][jj];
                      px[jj] += 1;
                  }
              }
          }
      }


      return V;
  }

}
