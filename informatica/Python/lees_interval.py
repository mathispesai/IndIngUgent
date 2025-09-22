# https://dodona.ugent.be/nl/courses/1005/series/11149/activities/2334238

def lees_interval()->tuple:
    ondergrens = 0
    bovengrens = -1
    while ondergrens > bovengrens:
        grenzen = input()
        for teken in grenzen:
            if teken is not " " and teken == grenzen[0] and len(grenzen) == 3:
                ondergrens = int(teken)
            elif teken is not " " and len(grenzen) == 3:
                bovengrens = int(teken)
    return (ondergrens, bovengrens)
def lees_interval_in_interval(interval: tuple):
    voorwaarde = False
    while voorwaarde == False:
        binnenintreval = lees_interval()
        if binnenintreval[0] > interval[0] and binnenintreval[1] <= interval[1] and binnenintreval[1] > interval[0]:
            voorwaarde = True
    return binnenintreval

