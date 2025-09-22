# -*- coding: utf-8 -*-
"""
Created on Sat Dec  5 00:53:50 2020

@author: mathi
"""

def comprimeerSignaal(Y, K , phi = True):
    print("comprimeersignaal")
    '''
    

    Parameters
    ----------
    Y: LIST
        Lijst met de datapunten
        
    K: VALUE
        Dit is de compressiefactor, bepaald hoe groot de compressie is.
        
    
    phi: faseverschuiving

    Returns
    -------
    ampl_plaats : List
       lijst met fourier getransformeerde gecomprimeerde amplituden
       en de frequentie van deze waarden.

    '''
    