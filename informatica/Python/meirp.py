# https://dodona.ugent.be/nl/courses/1005/series/11153/activities/1637507257
def omgekeerd_getal(getal:int)->int:
    string_getal = str(getal)
    string_getal = string_getal[::-1]
    return int(string_getal)

def ispriem(getal:int)->bool: 
    voorwaarde = False
    teller = 0 
    teller2= 1
    while teller2 < (getal + 1):
        if getal % teller2 == 0:
            teller += 1 
        teller2 += 1
    if teller == 2:
        voorwaarde = True
    return voorwaarde

def ismeirp(getal:int)->bool:
    voorwaarde = False
    omgekeerd = omgekeerd_getal(getal)
    if getal == omgekeerd:
        pass
    else:
        if ispriem(getal) and ispriem(omgekeerd):
            voorwaarde = True
    return voorwaarde