# https://dodona.ugent.be/nl/courses/1005/series/11151/activities/1869973806

def is_schrikkeljaar(jaar: int) -> bool:
    schrikkeljaar = False
    if ((not jaar % 400) or (not jaar % 4)) and  ((not jaar % 400) or ( jaar % 100)):
        schrikkeljaar = True
    return schrikkeljaar