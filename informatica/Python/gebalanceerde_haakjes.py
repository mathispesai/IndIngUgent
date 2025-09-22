# https://dodona.ugent.be/nl/courses/1005/series/11146/activities/1204165679
aantal_regels = int(input())
for i in range(aantal_regels):
    start_gesloten = 0
    tellen = 0
    tekstregel = input()
    for char in tekstregel:
        if char in "({<[":
            tellen += 1
        elif char in ")}>]":
            if tellen > 0:
                tellen -= 1
            else: 
                start_gesloten += 1
    if tellen == 0 and start_gesloten == 0:
        print("gebalanceerd")
    else:
        print("ongebalanceerd")

