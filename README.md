# Java Matrix Algebra :alien:

A matrix algebra implementation in Java.

## Importing :computer:

Source: Matrix.java <br />
Compile:
```sh
> javac Matrix.java
```
Import:
```Java

public class test {

  public static void main(String[] args) {
    Matrix mx = new Matrix();
  }
}
```
## Primary Functions :satellite:

#### Multiply
````Java

double[][] C = mx.MultiplyMatrix(double[][] A, double[][] B);

````

#### Transpose

````Java

double[][] XT = mx.Transpose(double[][] X);

````

#### Row Reduce

````Java

double[][] XR = mx.RowReduce(double[][] X);

````

#### Diagonalize

````Java

double[][] XD = mx.Diagonalize(double[][] X, "Left" | "Right" | "Triangular");

````

#### Inverse

````Java

double[][] XI = mx.InverseMatrix(double[][] X);

````

#### Determenant

````Java

double xdet = mx.Determenant(double[][] X);

````

#### Trace

````Java

double xtr = mx.Trace(double[][] X);

````

#### Add/Subtract Two Matrices

````Java

double[][] C = mx.Moper(double[][] A, double[][] B, 1 or -1);

````

#### Multiply matrix by coefficient

````Java

double[][] B = mx.Ax(double coef, double[][] A);

````

#### Get Diagonal Of Matrix

````Java

double[][] XD = mx.Diagonal(double[][] X);

````

#### Give a power to each variable of a matrix

````Java

double[][] B = mx.ExponentMatrix(double[][] A, double exp);

````

#### Give a coefficient a matrix as an exponent

````Java

double[][] B = mx.MxPower(double coef, double[][] EXP);

````


## Other Examples :partly_sunny:

Compile & Import ```StatPack.java```

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
![Equation](https://latex.codecogs.com/gif.latex?%5Cbegin%7Bmatrix%7D%20H%3D%5Csqrt%7Bdiag%5Cbig%28%7B%5CSigma%7D%5Cbig%29%7D%20%26%26%20%5Crho%20%3D%20%5Cdfrac%7B%5CSigma_%7Bi%2C%20j%7D%7D%7B%5BHH%5ET%5D_%7Bi%2C%20j%7D%7D%20%5Cend%7Bmatrix%7D)

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
    double[][] X = {{1, 2, 3, -15},
                      {1, 3, 5, -2},
                      {1, 5, 1, -8},
                      {1, 1, 2, 1},
                      {1, 2, 6, -9},
                      {1, 7, 6, 4},
                      {1, 4, 2, -5},
                      {1, 9, 3, -3},
                      {1, 8, 9, 10},
                      {1, 2, 0, 2},
                      {1, 15, 9, 0}};

      double[] Yh = {20, 30, 50, 18, 23, 45, 66, 92, 84, 21, 55};

      double[][] beta = sp.Regression(X, Yh);

      sp.ANOVA(X, beta, Yh);

}

```
### ANOVA Table
```Java
sp.ANOVA(X, beta, Yh);
```
![alt](https://github.com/marscolony2040/JavaMatrix/blob/master/anova.png)

### Target Rate and Tangent Portfolio

#### Target Rate
```math
\Bigg ( \begin{matrix} 2\Sigma & \mu & 1 \\ \mu^T & 0 & 0 \\ 1^T & 0 & 0 \end{matrix} \Bigg ) ^{-1} \Bigg ( \begin{matrix} 0 \\ r_t \\ 1 \end{matrix} \Bigg ) = \Bigg ( \begin{matrix} w \\ \lambda_1 \\ \lambda_2 \end{matrix} \Bigg )
```

#### Tangent Rate
```math
t = \dfrac{\Sigma^{-1}(\mu - r_f)}{1^T\Sigma^{-1}(\mu - r_f)}
```
```Java
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
```
