import java.util.*;
import java.text.*;
import java.io.*;
import java.math.*;


public class StatPack {

    public static Matrix np = new Matrix();

    public static double rx(double x, int p){
        return Math.round(x*Math.pow(10,p))/Math.pow(10,p);
    }

    // function to calculate the CDF of the t-distribution with df degrees of freedom and a given t-value
    public static double cdf(double t) {
        double factor = 1.0 / Math.sqrt(2.0*Math.PI);
        double x = Math.pow(t, 2) / 2.0;
        return factor*Math.exp(-x);
    }

    // Calculates your beta coefficents for a multi-variable regression
    public static double[][] Regression(double[][] X, double[] y) {
        double[][] Y = np.Vector(y);
        double[][] XTX = np.MultiplyMatrix(np.Transpose(X), X);
        double[][] IXTX = np.InverseMatrix(XTX);
        double[][] XTY = np.MultiplyMatrix(np.Transpose(X), Y);
        return np.MultiplyMatrix(IXTX, XTY);
    }

    // Prints out your anova table
    public static void ANOVA(double[][] X, double[][] beta, double[] y) {
        System.out.println();
        double[][] yhat = np.MultiplyMatrix(X, beta);
        double[][] Y = np.Vector(y);
        double[][] e = np.HOper(Y, yhat);
        double RSS = np.MultiplyMatrix(np.Transpose(e), e)[0][0];
        double df = X.length - X[0].length;
        double factor = RSS / df;
        double[][] mtx = np.InverseMatrix(np.MultiplyMatrix(np.Transpose(X), X));
        double[][] top = np.Ax(factor, mtx);
        double[][] sd = np.ExponentMatrix(np.Diag(top), 0.5);
        double[] stderr = np.VArray(sd);
        double[] tscore = new double[beta.length];
        double[] pvalue = new double[beta.length];
        for(int i = 0; i < beta.length; i++){
            tscore[i] = beta[i][0]/sd[i][0];
            pvalue[i] = cdf(tscore[i]);
        }
        System.out.println("ANOVA TABLE");
        String output;
        for(int i = 0; i < beta.length; i++){
            if(i == 0){
                output = "Intercept | Beta: " + np.NtoS(rx(beta[i][0], 3)) + " | StdErr: " + np.NtoS(rx(stderr[i],3)) + " | TestStat: " + np.NtoS(rx(tscore[i],3)) + " | P-Value: " + np.NtoS(rx(pvalue[i],3));
                System.out.println(output); 
            } else {
                output = "Variable: " + np.NtoS(i) + " | Beta: " + np.NtoS(rx(beta[i][0], 3)) + " | StdErr: " + np.NtoS(rx(stderr[i],3)) + " | TestStat: " + np.NtoS(rx(tscore[i],3)) + " | P-Value: " + np.NtoS(rx(pvalue[i],3));
                System.out.println(output); 
            }
            
        }
        System.out.println();
        
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

    // Calculates weights for a portfolio with an inputted target rate
    public static double[][] TargetRatePortfolio(double[][] X, double r){
        double[][] cov = Variance(X, "covariance");
        double[][] mu = Mean(X);
        cov = np.Ax(2.0, cov);
        double[][] A = new double[cov.length + 2][cov.length + 2];
        double[][] B = new double[cov.length + 2][1];
        for(int i = 0; i < cov.length; i++){
            for(int j = 0; j < cov.length; j++){
                A[i][j] = cov[i][j];
            }
            A[i][cov.length] = mu[i][0];
            A[i][cov.length + 1] = 1.0;
            A[cov.length][i] = mu[i][0];
            A[cov.length + 1][i] = 1.0;
            B[i][0] = 0.0;
        }
        B[cov.length][0] = r;
        B[cov.length + 1][0] = 1.0;
        double[][] W = np.MultiplyMatrix(np.InverseMatrix(A), B);
        double[][] w = new double[cov.length][1];
        for(int i = 0; i < w.length; i++){
            w[i][0] = W[i][0];
        }
        return w;
    }

    // Calculates weights for optimal tangent portfolio
    public static double[][] TangentPortfolio(double[][] X, double rf){
        double[][] cov = Variance(X, "covariance");
        double[][] ICOV = np.InverseMatrix(cov);
        double[][] mu = Mean(X);
        double[][] one = np.Ax(rf, np.Transpose(np.Ones(cov.length, 1.0)));
        double[][] mu1 = np.CVOper(mu, one, -1.0);
        double[][] Top = np.MultiplyMatrix(ICOV, mu1);
        double[][] ones = np.Ones(Top.length, 1.0);
        double[][] Bot = np.MultiplyMatrix(ones, Top);
        double[][] W = new double[cov.length][1];
        double bottom = Bot[0][0];
        for(int i = 0; i < Top.length; i++){
            W[i][0] = Top[i][0]/bottom;
        }
        return W;
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
