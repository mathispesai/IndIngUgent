# https://dodona.ugent.be/nl/courses/1005/series/11152/activities/1125371542
def is_nuldeler(x: int, n: int) -> bool:
    voorwaarde = False
    for i in range(1,n):
        if not x*i % n:
            voorwaarde = True
    return voorwaarde

