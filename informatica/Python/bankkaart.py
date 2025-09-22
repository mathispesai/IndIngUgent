# https://dodona.ugent.be/nl/courses/1005/series/11153/activities/7364659
def luhn(kaartnummer:str)->int:
    kaartnummer_omgekeerd = kaartnummer[::-1]
    teller = 1
    som = 0
    while teller < (len(kaartnummer_omgekeerd)):
        dubbel = 2*int(kaartnummer_omgekeerd[teller])
        if len(str(dubbel)) == 1:
            som += dubbel
        else:
            som += (int(str(dubbel)[0]) + int(str(dubbel)[1]))
        teller += 2
    teller2 = 0
    while teller2 < (len(kaartnummer_omgekeerd)):
        som += int(kaartnummer_omgekeerd[teller2])
        teller2 += 2
    return som

def geldig(kaartnummer:str)->bool:
    voorwaarde = False
    if luhn(kaartnummer)% 10 == 0:
        voorwaarde = True
    return voorwaarde

def controle(kaartnummer:str)->str:
    kaartnummer_plus = kaartnummer + "0"
    controlegetal = 10 - luhn(kaartnummer_plus)%10
    if controlegetal == 10:
        controlegetal = 0
    return kaartnummer + str(controlegetal)
