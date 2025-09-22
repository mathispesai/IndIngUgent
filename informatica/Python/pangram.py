# https://dodona.ugent.be/nl/courses/1005/series/11148/activities/327952493

def is_pangram(zin: str) -> bool:
    voorwaarde = False
    alfabet = "qwertyuiopasdfghjklzxcvbnm"
    zin = zin.lower()
    for char in zin:
        if char in alfabet:
            alfabet = alfabet[:alfabet.index(char)] + alfabet[alfabet.index(char)+1:]
    if not alfabet:
        voorwaarde = True
    return voorwaarde