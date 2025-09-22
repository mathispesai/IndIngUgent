##
# Het script taylor.py berekent en tekent veeltermbenaeringen van een functie

import sympy as sp
import numpy as np
import matplotlib.pyplot as plt

def main():
    def f(x): return sp.sin(x/2)*x**2
    print(taylorCoefficient(f,0,3))
    print([taylorCoefficient(f,0,k) for k in range(10)])
    p = taylorVeelterm(f,0,10)
    print(p)
    x = sp.symbols('x')
    print(p(x))
    p(x)
    print(kwadratischeOppervlakte(f,0,1))
    taylorConvergentie(f,0,6)
    taylorFiguur(f,0,3,4,'graad3.pdf')
    taylorFiguur(f,0,4,4,'graad4.pdf')
    taylorFiguur(f,0,5,4,'graad5.pdf')
    taylorFiguur(f,0,6,4,'graad6.pdf')

def taylorCoefficient(f,a,n):
    x = sp.symbols('x')
    return sp.diff(f(x),x,n).subs(x,a)/sp.factorial(n)

def taylorVeelterm(f,a,n):
    x = sp.symbols('x')
    som = 0
    for k in range(n+1):
        som += taylorCoefficient(f,a,k)*(x-a)**k
    def p(x0): return som.subs(x,x0)
    return p

def kwadratischeOppervlakte(f,a,b):
    x = sp.symbols('x')
    return float(sp.integrate((f(x)**2),(x,a,b)))

def taylorConvergentie(f,a,r):
    x = sp.symbols('x')
    n = 20
    K = []
    F = []
    for k in range(0,n+1):
        c = taylorCoefficient(f,a,k)
        if c != 0:
            def term(x): return c*(x-a)**k
            F += [-sp.log(kwadratischeOppervlakte(term,a-r,a+r))/sp.log(10)/2]
            K += [k]
    plt.figure()
    plt.plot(K,F,'k*')
    plt.savefig('convergentie.pdf')

def taylorFiguur(f,a,n,r,naam):
    x = sp.symbols('x')
    p = taylorVeelterm(f,a,n)
    X = np.linspace(a-r,a+r,200)
    Y = sp.lambdify(x,f(x),'numpy')(X)
    Z = sp.lambdify(x,p(x),'numpy')(X)
    plt.figure()
    plt.plot(X,Y,'k-',lw=2)
    plt.plot(X,Z,'b--',lw=2)
    K = np.linspace(a-r,a+r,50)
    for k in K:
        F = float(f(k))
        P = float(p(k))
        if F > P:
            plt.plot([k,k],[F,P],'r:')
        else:
            plt.plot([k,k],[F,P],'g:')
    plt.savefig(naam)
    plt.close()

main()