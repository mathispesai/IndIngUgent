# https://dodona.ugent.be/nl/courses/1005/series/11152/activities/367427984
def som_omgekeerd(getal: int) -> int:
    omgekeerde_getal = str(getal)[::-1]
    som = getal + int(omgekeerde_getal)
    return som

def is_palindroom(getal: int) -> bool:
    voorwaarde = False
    if str(getal) == str(getal)[::-1]:
        voorwaarde = True
    return voorwaarde