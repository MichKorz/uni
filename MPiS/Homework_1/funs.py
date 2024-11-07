import math

def third_root_of_x(x, y):
    return x ** (1/3) >= y

def sin_x(x, y):
    return math.sin(x) >= y

def polynomial(x, y):
    return 4*x*((1-x)**3) >= y

def pi(x, y):
    return math.sqrt((1-x)**2 + (1-y)**2) <= 1