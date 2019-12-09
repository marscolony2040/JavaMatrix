# Java Matrix Algebra

A matrix algebra implementation in Java.

## Primary StatPack Functions

Source: StatPack.java <br />
Compile:
```sh
> javac StatPack.java
```

### Mean
![Equation](https://latex.codecogs.com/gif.latex?%5Chat%7B%5Cmu%7D%3D%5Cdfrac%7B1%7D%7Bm%7D1%5ETX)
```Java
public static void MeanExample(){
    Matrix np = new Matrix();
    StatPack sp = new StatPack();
    double[][] X = {{6, 6, 7, 2},
                    {14, 3, 51, 6},
                    {4, 13, 25, 18},
                    {24, 31, 15, 36},
                    {4, 13, 5, 2},
                    {4, 3, 51, 16}};

    double[][] mu = sp.Mean(X);
    np.PrintM(mu);
}
```

### Covariance & Correlation Matrix
Covariance
![Equation](https://latex.codecogs.com/gif.latex?%5CSigma%20%3D%20%5Cdfrac%7B1%7D%7Bm-1%7D%28X-%5Chat%7BX%7D%29%5ET%28X%20-%20%5Chat%7BX%7D%29) <br />

Correlation
![Equation](https://latex.codecogs.com/gif.latex?%5Cbegin%7Bmatrix%7D%20H%20%3D%20%5Csqrt%7Bdiag%28X%29%7D%20%26%20%5Crho%20%3D%20%5Cdfrac%7B%5CSigma_%7Bi%2C%20j%7D%20%7D%7B%20%5BHH%5ET%5D_%7Bi%2C%20j%7D%20%7D%5Cend%7Bmatrix%7D)

```Java
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
```


### Regression Coeffecients
![Equation](https://latex.codecogs.com/gif.latex?%5Cbeta%20%3D%20%28X%5ETX%29%5E%7B-1%7DX%5ETy)
```Java
public static void MultiVariableReg(){
    Matrix np = new Matrix();
    StatPack sp = new StatPack();
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

```


## Primary Matrix Functions

Source: Matrix.java <br />
Compile:
```sh
> javac Matrix.java
```

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



