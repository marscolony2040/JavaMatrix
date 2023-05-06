import java.util.*;
import java.text.*;
import java.io.*;
import java.math.*;


public class StatPack {

    public static Matrix np = new Matrix();

    // Modified round function
    public static double rx(double x, int p){
        return Math.round(x*Math.pow(10,p))/Math.pow(10,p);
    }

    private static double N(double x){
        double factor = 1.0/Math.sqrt(2*Math.PI);
        double e = Math.exp(-Math.pow(x, 2)/2);
        return factor*e;
    }

    private static double PVal(double t){
        double prob = 0.0;
        double t0 = 0;
        double slabs = 311;
        t = Math.abs(t);
        double dt = (t - t0)/(slabs - 1);
        for(int i = 0; i < (int) slabs; i++){
            if(i == 0 || i == slabs - 1){
                prob += N(t0 + i*dt)*1.0;
            } else if(i % 2 == 0){
                prob += 2.0*N(t0 + i*dt);
            } else {
                prob += 4.0*N(t0 + i*dt);
            }
        }
        prob *= dt / 3.0;
        return 1 - (prob + 0.5);
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
        double y_mu = Mean(Y)[0][0];
        
        double[][] e = np.HOper(Y, yhat);
        double TSS = np.MpSum(np.Dx(y_mu, Y));
        double RSS = np.MultiplyMatrix(np.Transpose(e), e)[0][0];
        double ESS = TSS - RSS;
        double rsq = 1.0 - RSS/TSS;
        double df = X.length - X[0].length;
        double n = X.length;
        double m = X[0].length - 1;
        double adj_rsq = 1 - (1 - rsq)*(n - 1)/(n - m - 1);
        double F = (ESS/m)/(RSS/(n - m - 1));
        double standard_error = Math.sqrt(RSS/(n - m - 1));
        double factor = RSS / df;
        double[][] mtx = np.InverseMatrix(np.MultiplyMatrix(np.Transpose(X), X));
        double[][] top = np.Ax(factor, mtx);
        double[][] sd = np.ExponentMatrix(np.Diag(top), 0.5);
        double[] stderr = np.VArray(np.Scalar(sd, 1.0));
        double[] tscore = new double[beta.length];
        double[] pvalue = new double[beta.length];
        for(int i = 0; i < beta.length; i++){
            tscore[i] = beta[i][0]/stderr[i];
            pvalue[i] = PVal(tscore[i]);
        }
        System.out.println("ANOVA TABLE");
        System.out.println();
        System.out.println("R-Squared: " + np.NtoS(rsq));
        System.out.println("Adj-RSquared: " + np.NtoS(adj_rsq));
        System.out.println("F-Statistic: " + np.NtoS(F));
        System.out.println("Standard Error: " + np.NtoS(standard_error));
        System.out.println();
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
