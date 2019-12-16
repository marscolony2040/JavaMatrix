import java.util.*;
import java.text.*;
import java.io.*;



class main {

  public static StatPack sp = new StatPack();
  public static Matrix np = new Matrix();

  public static void main(String[] args) throws Exception {

    double[][] c = {{4, 5, 6}, {3, 1, 2}, {9, 6, 6}};
    System.out.println(np.Trace(c));

    //MinVarExample();

  }

  // Min-Variance Portfolio example demo
  public static void MinVarExample(){
      double[][] x = {{-0.03, 0.02, -0.01},
                      {0.02, -0.02, 0.01},
                      {-0.04, 0.06, 0.02},
                      {-0.01, 0.03, 0.05},
                      {0.08, 0.01, 0.03},
                      {0.07, -0.05, 0.01},
                      {-0.05, 0.02, -0.02},
                      {0.09, 0.01, -0.01}};

      np.PrintM(sp.MinVarPortfolio(x));
  }

  // Mean vector example demo
  public static void MeanExample(){
      double[][] X = {{6, 6, 7, 2},
                      {14, 3, 51, 6},
                      {4, 13, 25, 18},
                      {24, 31, 15, 36},
                      {4, 13, 5, 2},
                      {4, 3, 51, 16}};

      double[][] mu = sp.Mean(X);
      np.PrintM(mu);
  }

  // Covariance & Correlation vector example demo
  public static void CovarExample(){
      double[][] X = {{6, 6, 7, 2},
                      {14, 3, 51, 6},
                      {4, 13, 25, 18},
                      {24, 31, 15, 36},
                      {4, 13, 5, 2},
                      {4, 3, 51, 16}};

      double[][] covariance = sp.Variance(X, "covariance");
      double[][] correlation = sp.Variance(X, "correlation");

      np.PrintM(covariance);
      np.PrintM(correlation);

  }

  // Multivariable Regression example demo
  public static void MultiVariableReg(){
      double[][] X = {{1, 300, 298, 101},
                      {1, 320, 245, 102},
                      {1, 310, 233, 103},
                      {1, 290, 252, 108},
                      {1, 270, 253, 106},
                      {1, 320, 257, 140},
                      {1, 310, 254, 120},
                      {1, 315, 280, 101},
                      {1, 307, 260, 110},
                      {1, 312, 259, 107},
                      {1, 309, 251, 113}};

      double[] Yh = {20, 30, 50, 18, 23, 45, 66, 92, 84, 21, 55};

      double[][] beta = sp.Regression(X, Yh);

      np.PrintM(beta);

  }

}
