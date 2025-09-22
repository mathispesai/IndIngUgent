# https://dodona.ugent.be/nl/courses/1005/series/11153/activities/766972059
def palindromisch(getal: int) -> bool:
    voorwaarde = False
    getal = str(getal)
    if getal == getal[::-1]:
        voorwaarde = True
    return voorwaarde
    
def palindroomveelvouden(getal: int, c: int) -> int:
    aantal = 0
    max = "1000000"
    if len(str(getal)) <= c:
        for i in range(1,int(max[:c+1])):
            if palindromisch(i*getal) and len(str(getal*i)) == c:
                aantal += 1
    return aantal
