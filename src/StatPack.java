import java.util.*;
import java.text.*;
import java.io.*;
import java.math.*;


public class StatPack {

    public static Matrix np = new Matrix();

    // Calculates your beta coefficents for a multi-variable regression
    public static double[][] Regression(double[][] X, double[] y) {
        double[][] Y = np.Vector(y);
        double[][] XTX = np.MultiplyMatrix(np.Transpose(X), X);
        double[][] IXTX = np.InverseMatrix(XTX);
        double[][] XTY = np.MultiplyMatrix(np.Transpose(X), Y);
        return np.MultiplyMatrix(IXTX, XTY);
    }

    // Calculates your mean vector
    public static double[][] Mean(double[][] X) {
        int m = X.length, n = X[0].length;
        return np.Transpose(np.Ax(1.0 / (double) m, np.MultiplyMatrix(np.Ones(m, 1), X)));
    }

    // Calculates your covariance or correlation matrix
    public static double[][] Variance(double[][] X, String choice){
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

    // Calculates weights for a min-variance portfolio
    public static double[][] MinVarPortfolio(double[][] X){
        double[][] cov = Variance(X, "covariance");
        cov = np.Ax(2.0, cov);
        double[][] A = new double[cov.length + 1][cov.length + 1];
        double[][] B = new double[cov.length + 1][1];
        for(int i = 0; i < cov.length; i++){
            for(int j = 0; j < cov.length; j++){
                A[i][j] = cov[i][j];
            }
            A[i][cov.length] = 1.0;
            A[cov.length][i] = 1.0;
            B[i][0] = 0;
        }
        B[cov.length][0] = 1;
        double[][] W = np.MultiplyMatrix(np.InverseMatrix(A), B);
        double[][] w = new double[W.length - 1][1];
        for(int i = 0; i < cov.length; i++){
            w[i][0] = W[i][0];
        }
        return w;
    }

    // Calculates coordinates for quadratic approximation
    public static double QuadraticApproximation(double x, double y, double x0, double y0){
        // Function f(x, y) = sin(x^2 + y^2)
        // Function df/dx = cos(x^2 + y^2)*2x
        // Function df/dy = cos(x^2 + y^2)*2y
        // Function d2f/dx2 = 2[cos(x^2 + y^2) - x^2sin(x^2 + y^2)]
        // Function d2f/dy2 = 2[cos(x^2 + y^2) - y^2sin(x^2 + y^2)]
        // Function d2f/dxdy = -4xysin(x^2 + y^2)
        double result;
        double[][] X = {{x},{y}};
        double[][] Xh = {{x0},{y0}};
        double FXh = Math.sin(Math.pow(x0, 2) + Math.pow(y0, 2));
        double[][] K = np.CVOper(X, Xh, -1);
        double[][] J = {{Math.cos(Math.pow(x0, 2)+Math.pow(y0,2))*2*x0},
                        {Math.cos(Math.pow(x0, 2)+Math.pow(y0,2))*2*y0}};
        double[][] H = {{2*(Math.cos(Math.pow(x0, 2)+Math.pow(y0, 2))-Math.pow(x0, 2)*Math.sin(Math.pow(x0, 2)+Math.pow(y0,2))), -4*x0*y0*Math.sin(Math.pow(x0, 2) + Math.pow(y0, 2))},
                        {-4*x0*y0*Math.sin(Math.pow(x0, 2) + Math.pow(y0, 2)), 2*(Math.cos(Math.pow(x0, 2)+Math.pow(y0, 2))-Math.pow(y0, 2)*Math.sin(Math.pow(x0, 2)+Math.pow(y0,2)))}};
                        
        double[][] FTHF = np.Ax(0.5, np.MultiplyMatrix(np.Transpose(K), np.MultiplyMatrix(H, K)));
        double[][] JHF = np.MultiplyMatrix(np.Transpose(J), K);
        result = FXh + JHF[0][0] + FTHF[0][0];  
        return result;
    }
    

}
