# https://dodona.ugent.be/nl/courses/1005/series/11163/activities/1366750143
def splits(afmetingen: str)-> list:
    afmetingen_lijst = afmetingen.split("x")
    for char in afmetingen_lijst:
        afmetingen_lijst[afmetingen_lijst.index(char)] = float(char)
    afmetingen_lijst.sort( reverse = True)

    return afmetingen_lijst

def check_dim(bagage: list, max: list)-> bool:
    voorwaarde = False
    tellen = 0
    for char in bagage:
        if char <= max[bagage.index(char)]:
            tellen += 1
    if tellen == len(bagage):
        voorwaarde = True
    return voorwaarde