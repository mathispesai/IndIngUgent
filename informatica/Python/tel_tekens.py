# https://dodona.ugent.be/nl/courses/1005/series/11149/activities/1787952364

def tel_tekens(zin: str, tekens: str) -> int:
    aantal = 0
    for teken in zin:
        if teken in tekens:
            aantal += 1
    return aantal





