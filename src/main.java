import java.util.*;
import java.text.*;
import java.io.*;



class main {

  public static StatPack sp = new StatPack();
  public static Matrix np = new Matrix();

  public static void main(String[] args) throws Exception {

    //double[][] X = {{3, 5, 2}, {2, 6, 4}, {1, 3, 2}, {2, 1, 6}};

    //np.PrintM(sp.Variance(X, "covariance"));

    //System.out.println(sp.QuadraticApproximation(1, 2, 0.3, 0.7));

    MultiVariableReg();
  }

  // Target Return Portfolio example demo
  public static void TargetReturn(){
    double[][] x = {{-0.03, 0.02, -0.01},
                    {0.02, -0.02, 0.01},
                    {-0.04, 0.06, 0.02},
                    {-0.01, 0.03, 0.05},
                    {0.08, 0.01, 0.03},
                    {0.07, -0.05, 0.01},
                    {-0.05, 0.02, -0.02},
                    {0.09, 0.01, -0.01}};

    double[][] W = sp.TargetRatePortfolio(x, 0.04);
    double[][] t = sp.TangentPortfolio(x, 0.02);
    np.PrintM(W);
    np.PrintM(t);
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
      double[][] X = {{1, 300, 298, 101, 2, 3},
                      {1, 320, 245, 102, 3, 5},
                      {1, 310, 233, 103, 5, 1},
                      {1, 290, 252, 108, 1, 2},
                      {1, 270, 253, 106, 2, 6},
                      {1, 320, 257, 140, 4, 7},
                      {1, 310, 254, 120, 16, 2},
                      {1, 315, 280, 101, 9, 3},
                      {1, 307, 260, 110, 8, 9},
                      {1, 312, 259, 107, 2, 0},
                      {1, 309, 251, 113, 15, 9}};

      double[] Yh = {20, 30, 50, 18, 23, 45, 66, 92, 84, 21, 55};

      double[][] beta = sp.Regression(X, Yh);

      sp.ANOVA(X, beta, Yh);

  }

}
