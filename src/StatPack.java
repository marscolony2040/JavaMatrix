import java.util.*;
import java.text.*;
import java.io.*;


public class StatPack {

    // Calculates your beta coefficents for a multi-variable regression

    public static double[][] Regression(double[][] X, double[] y) {
        Matrix np = new Matrix();
        double[][] Y = np.Vector(y);
        double[][] XTX = np.MultiplyMatrix(np.Transpose(X), X);
        double[][] IXTX = np.InverseMatrix(XTX);
        double[][] XTY = np.MultiplyMatrix(np.Transpose(X), Y);
        return np.MultiplyMatrix(IXTX, XTY);
    }

    // Calculates your mean vector
    public static double[][] Mean(double[][] X) {
        Matrix np = new Matrix();
        int m = X.length, n = X[0].length;
        return np.Transpose(np.Ax(1.0 / (double) m, np.MultiplyMatrix(np.Ones(m, 1), X)));
    }

    // Calculates your covariance or correlation matrix
    public static double[][] Variance(double[][] X, String choice){
        Matrix np = new Matrix();
        int m = X.length, n = X[0].length;
        double[][] mu = Mean(X);
        double[][] x_mu = np.CVOper(X, mu, -1);
        double[][] cov = np.Ax(1.0/(double) (m - 1), np.MultiplyMatrix(np.Transpose(x_mu), x_mu));
        if(choice.equalsIgnoreCase("covariance")){
            return cov;
        }
        if(choice.equalsIgnoreCase("correlation")){
            double[][] sd = np.ExponentMatrix(np.Diag(cov), 0.5);
            sd = np.MultiplyMatrix(sd, np.Transpose(sd));
            for(int i = 0; i < cov.length; i++){
                for(int j = 0; j < cov[0].length; j++){
                    cov[i][j] /= sd[i][j];
                }
            }
            return cov;
        }
        return cov;
    }


}
