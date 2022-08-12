import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import os

def errorz(a, b):
    sumt = 0
    for i, j in zip(a, b):
        sumt += pow(i - j, 2)
    n = len(a)
    return (sumt/(n - 1))/np.sqrt(n)

data = pd.read_csv('output.csv', header=None)
print(os.popen('rm -rf output.csv').read())

x1, y1, x2, y2 = data.values.T.tolist()
a = range(len(x1))

fig = plt.figure(figsize=(10, 8))
ax = fig.add_subplot(211)
ay = fig.add_subplot(212)

e1 = round(errorz(x1, y1), 4)
ax.set_title("Variable (X) | Std. Err: {}".format(e1))
ax.plot(a, x1, color='red', label='Actual')
ax.plot(a, y1, color='blue', label='Predicted')
ax.legend(loc='best')

e2 = round(errorz(x2, y2), 4)
ay.set_title("Variable (Y) | Std. Err: {}".format(e2))
ay.plot(a, x2, color='red', label='Actual')
ay.plot(a, y2, color='blue', label='Predicted')

plt.show()

