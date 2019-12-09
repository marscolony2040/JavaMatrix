import java.util.*;
import java.text.*;
import java.io.*;



class main {

  public static void main(String[] args) throws Exception {
    Matrix np = new Matrix();

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

    double[] Yh = {10, 30, 50, 18, 23, 45, 66, 92, 84, 21, 55};
    double[][] Y = np.Vector(Yh);

    double[] beta = np.VArray(np.MultiplyMatrix(np.InverseMatrix(np.MultiplyMatrix(np.Transpose(X), X)),np.MultiplyMatrix(np.Transpose(X), Y)));

    np.PrintA(beta);

  }




}
