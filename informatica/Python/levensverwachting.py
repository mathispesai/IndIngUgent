# https://dodona.ugent.be/nl/courses/1005/series/11151/activities/849566952
def levensverwachting(geslacht: str, roker: bool, sport: int, alcohol: int, fastfood: bool) -> float:
    basis = 70
    if geslacht == "vrouw":
        basis += 4
    if roker:
        basis -= 5
    else:
        basis += 5
    if not sport:
        basis -= 3
    else: 
        basis += sport
    if not alcohol:
        basis += 2
    else:
        if (alcohol - 7) > 0:
            basis -= (alcohol - 7)/2
    if not fastfood:
        basis += 3
    return float(basis)

