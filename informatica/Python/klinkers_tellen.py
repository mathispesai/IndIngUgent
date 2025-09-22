# https://dodona.ugent.be/nl/courses/1005/series/11148/activities/1859631450

def tel_klinkers(zin: str) -> int:
    aantal_klinkers = 0
    zin = zin.lower()
    for char in  zin:
        if char in "aeiou":
            aantal_klinkers += 1
    return aantal_klinkers




