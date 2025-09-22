# https://dodona.ugent.be/nl/courses/1005/series/11159/activities/878440165
def tel_klinkers_medeklinkers(tekst:str)->tuple:
    aantal_klinkers = 0
    aantal_medeklinkers = 0
    for char in tekst:
        if char.lower() in "aeiou":
            aantal_klinkers += 1
        elif char.upper() in "BCDFGHJKLMNPQRSTVWXYZ":
            aantal_medeklinkers += 1
    return (aantal_klinkers,aantal_medeklinkers)

