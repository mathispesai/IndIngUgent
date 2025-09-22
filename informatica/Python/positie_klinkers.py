# https://dodona.ugent.be/nl/courses/1005/series/11148/activities/1094048635

def positie_klinkers(zin: str) -> str:
    zin = zin.lower()
    posities = ""
    tellen = 0
    for char in  zin:
        if char in "aeiou":
            posities += str(zin.index(char)+tellen)
            posities += " "
            zin = zin[1:]
            tellen += 1
        else:
            tellen += 1
            zin = zin[1:]
    print(posities )
    
            