# -*- coding: utf-8 -*-
"""
Created on Sat Dec  5 00:52:15 2020

@author: mathi
"""

def reconstrueerSignaal(amplituden, plaats, hoeveelheid_Y, phi = True):
    print("reconstrueersignaal")
    '''
    
    Parameters
    ----------
    amplituden: LIST
               lijst van ak of ck waarden
    plaatsen: LIST
            lijst van bk of fase  waarden
    hoeveelheid_Y: VALUE
            Hoeveel punten je wil terugkrijgen          
    phi: faseverschuiving

    Returns
    -------
    grootte_Y : List
        Lijst met gereconstrueerde amplituden

    '''
    #eerste mogelijkheid
    if phi:
        ck = amplituden
        fasen = plaats
        eerstewaarde = ck[0]
        ck.remove(eerstewaarde)
        
        grootte_Y = []
        #reconstrueren
        for j in range(hoeveelheid_Y):
            y = eerstewaarde
            for k in range(len(ck)):
                y += ck[k]*np.cos(k*j + fasen[k])
            grootte_Y.append(y)
    #tweede mogelijkheid
    else:
        ak = amplituden
        bk = plaats
        eerstewaarde = ak[0]
        ak.remove(eerstewaarde)
        grootte_Y = []
        #reconstrueren
        for j in range(hoeveelheid_Y):
            y = eerstewaarde
            for k in range(len(ak)):
                y += ak[k] * np.cos([k * j]) + bk[k] * np.sin(k * j)
            grootte_Y.append(y)
    return grootte_Y