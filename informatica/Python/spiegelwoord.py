# https://dodona.ugent.be/nl/courses/1005/series/11152/activities/1347988041

def is_spiegelwoord(woord: str) -> bool:
    voorwaarde = False
    tellen = 0
    for letter in woord:
        if letter in "BCDEHIKOX":
            tellen += 1
    if tellen == len(woord):
        voorwaarde = True
    return voorwaarde
    


