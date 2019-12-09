# Java Matrix Algebra

A matrix algebra implementation in Java.

## Example w/ Multi-Variable Regression


#### Beta Coeffecient Formula
![Equation](https://latex.codecogs.com/gif.latex?%5Cbeta%20%3D%20%28X%5ETX%29%5E%7B-1%7DX%5ETy)

```Java
public class main {
  
  public static void main(String[] args) {
  
    Matrix stat = new Matrix();
    
    // Creating your 'X' values dataframe w/ 3 variables. The first column = 1 to solve for b0
    
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
    
    // Actual 'Y' values converted to (n x 1) vector
    double[] y = {10, 30, 50, 18, 23, 45, 66, 92, 84, 21, 55};
    double[][] Y = stat.Vector(y);
    
    // Calculate X transpose times X
    
    double[][] XT = stat.MultiplyMatrix(stat.Transpose(X), X);
    
    // Take inverse of 'X^T * X'
    
    double[][] IVX = stat.InverseMatrix(XT);
    
    // Solve the second piece (X^T * y)
    
    double[][] XTY = stat.MultiplyMatrix(stat.Transpose(X), Y);
    
    // Final beta vector
    
    double[][] Beta = stat.MultiplyMatrix(IVX, XTY);
    
    // Prints your Beta vector
    stat.PrintM(Beta);
  
  }

}

```

## Primary Functions

### Matrix Multiplication
```Java
public class main {

  public static void main(String[] args) {
  
    // Creation of Object
    
    Matrix np = new Matrix();
    
    // Multiplying Two Matrices
    
    double[][] X = {{2, 3}, {5, 2}};
    double[][] B = {{3, 2}, {6, 1}};
    
    double[][] result = np.MultiplyMatrix(X, B);
    
    // Printing your result
    
    np.PrintM(result);
    
  }

}
```
### Transpose Matrix
```Java
public class main {

  public static void main(String[] args) {
  
    // Creation of Object
    
    Matrix np = new Matrix();
    
    // Printing the transpose matrix of 'X'
    
    double[][] X = {{2, 3}, {5, 2}};
    
    np.PrintM(np.Transpose(X));
  }

}
```

### Row Reduce
```Java
public class main {

  public static void main(String[] args) {
  
    // Creation of Object
    
    Matrix np = new Matrix();
    
    double[][] X = {{3, 5, 8, 4}, {2, 6, 1, 3}, {9, 1, 5, 2}};
    
    // Row Reduced form of 'X'
    
    double[][] result = np.RowReduce(X);
    
  }

}
```

### Diagonalize Matrix
```Java
public class main {
  
  public static void main(String[] args){
    
    Matrix F = new Matrix();
    
    double[][] a = {{3, 6, 9}, {2, 1, 5}, {8, 4, 2}};
    
    // Diagonalizes the left
    double[][] LD = F.Diagonalize(a, "Left");
    
    // Diagonalizes the right
    double[][] RD = F.Diagonalize(a, "Right");
    
    // Diagonalizes both sides in a triangular form
    double[][] TN = F.Diagonalize(a, "Triangular");
  
  }

}

```

### Inverse Matrix
```Java
public class main {
  
  public static void main(String[] args) {
    Matrix Mx = new Matrix();
    
    double[][] X = {{3, 2, 5}, {1, 9, 12}, {22, 11, 5}};
    
    // Calculates the inverse of your matrix
    double[][] InverseMatrix = Mx.InverseMatrix(X);
    
  }

}

```

### Adding or Subtracting two Matrices 
```Java
public class main {

  public static void main(String[] args) {
  
    // Creation of Object
    
    Matrix np = new Matrix();
    
    double[][] X = {{2, 3}, {5, 2}};
    double[][] B = {{3, 2}, {6, 1}};
    
    // Adding matrix 'X' to matrix 'B'
    double[][] resultAdd = np.MOper(X, B, 1);
    
    // Subtracting matrix 'B' from matrix 'X'
    double[][] resultSub = np.MOper(X, B, -1);
    
  }

}
```


### Adding or Subtracting a Column Vector from a Matrix 
```Java 
public class main {
  
  public static void main(String[] args) {
    Matrix np = new Matrix();
    
    double[][] X = {{3, 2, 5}, {1, 9, 12}, {22, 11, 5}};
    double[][] col_vect = {{2},{4},{1}};
    
    // Adds your column vector to matrix
    
    double[][] resAdd = np.CVOper(X, col_vect, 1);
    
    // Subtracts your column vector from your matrix
    
    double[][] resSub = np.CVOper(X, col_vect, -1);
  }
  
}
```

### Meshgrid in X,Y Plane
```Java 
public class main {
  
  public static void main(String[] args) {
    
    Matrix Q = new Matrix();
    
    // Creates meshgrid between (-3, 3) w/ 20 slabs in X direction
    double[][] X = Q.Ngrid(-3, 3, 20, 0);
    
    // Creates meshgrid between (-4, 2) w/ 15 slabs in Y direction
    double[][] X2 = Q.Ngrid(-4, 2, 15, 1);
  
  }
  
}
```



