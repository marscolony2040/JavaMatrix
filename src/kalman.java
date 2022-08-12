import java.util.*;
import java.text.*;
import java.time.temporal.TemporalAdjusters;
import java.io.*;
import java.math.*;


public class kalman {
    
    public static Matrix np = new Matrix();
    public static StatPack sp = new StatPack();

    public static void main(String[] args) throws Exception{
        Kalman();
    }


    public static void Kalman() throws Exception {
        FileWriter fp = new FileWriter("output.csv");
        BufferedWriter op = new BufferedWriter(fp);
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your size: ");
        int n = in.nextInt();
        double[][] xk1 = new double[2][2];
        double[][] pk1 = new double[2][2];
        double[][] K1 = new double[2][2];
        double[][] x1k = new double[2][1];
        double[][] p1k = new double[2][2];
        double[][] H = {{1, 0}, {0, 1}};
        double[][] Q = {{Rand(), Rand()},{Rand(), Rand()}};
        double[][] R = {{Rand(), Rand()},{Rand(), Rand()}};
        
        
        double[][] zk = new double[2][1];
        double[][] xk = {{3},{2}};
        double[][] Pk = {{0.13, 0.1}, {0.1, 0.21}};
        double[][] store_noise = new double[n][2];
        double[][] store_x = new double[n][2];
        double[][] store_z = new double[n][2];

        for(int i = 0; i < n; i++){
            xk1 = XK1(xk);
            store_x = Store(i, store_x, xk1[0][0], xk1[1][0]);
            zk[0][0] = RandX();
            zk[1][0] = RandX();
            op.write(Double.toString(zk[0][0]) + "," + Double.toString(xk1[0][0]) + "," + Double.toString(zk[1][0]) + "," + Double.toString(xk1[1][0]) + "\n");
            op.flush();
            store_noise = Store(i, store_noise, zk[0][0] - xk1[0][0], zk[1][0]-xk1[1][0]);
            store_z = Store(i, store_z, zk[0][0], zk[1][0]);
            
            if(i > 0){
                Pk = sp.Variance(store_x, "covariance");
                Q = sp.Variance(store_noise, "covariance");
                R = sp.Variance(store_z, "covariance");
                
                pk1 = PK1(Pk, Q);
                
            } else {
                pk1 = PK1(Pk, Q);
            }

            double[][] u = np.MultiplyMatrix(pk1, np.Transpose(H));
            double[][] v = np.MultiplyMatrix(H, np.MultiplyMatrix(pk1, np.Transpose(H)));
            double[][] w = np.MOper(v, R, 1.0);
            K1 = np.MultiplyMatrix(u, np.InverseMatrix(w));
                
            double[][] y = np.CVOper(zk, np.MultiplyMatrix(H, xk1), -1.0);
            double[][] th = np.MultiplyMatrix(K1, y);
            x1k = np.CVOper(xk1, th, 1.0);
            p1k = np.CVOper(pk1, np.MultiplyMatrix(K1, np.MultiplyMatrix(H, xk1)), -1.0);

            xk = x1k;
            Pk = p1k;
            
            
        }
    }

    public static double[][] Store(int i, double[][] sn, double x, double y){
        if(i < sn.length){
            sn[i][0] = x;
            sn[i][1] = y;
        } else {
            for(int k = 1; k < sn.length; ++k){
                sn[k-1][0] = sn[k][0];
                sn[k-1][1] = sn[k][1];
            }
            sn[sn.length - 1][1] = x;
            sn[sn.length - 1][1] = y;
        }
        return sn;
    }

    public static double[][] XK1(double[][] xk){
        double[][] FK = {{1, 1}, {0, 1}};
        double[][] a = {{1/2}, {1}};
        double[][] XK = np.CVOper(np.MultiplyMatrix(FK, xk), a, 1.0);
        return XK;
    }

    public static double[][] PK1(double[][] Pk, double[][] Q){
        double[][] FK = {{1, 1}, {0, 1}};
        double[][] A = np.MultiplyMatrix(FK, np.MultiplyMatrix(Pk, np.Transpose(FK)));
        double[][] B = np.MOper(A, Q, 1);
        return B;
    }

    public static double Rand() {
        return Math.random();
    }

    public static double RandX() {
        return Math.floor(Math.random()*10);
    }


}
