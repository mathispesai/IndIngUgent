# https://dodona.ugent.be/nl/courses/1005/series/11156/activities/1553024133

def personaliseren(briefnaam, gegevens):
    try:
        brief = open(briefnaam, "r")
        tekst = brief.read()
        brief.close()
        geg = open(gegevens, "r")
        gegevens_lijst = geg.readlines()
        geg.close()
        gegevens_lijst = gegevens_lijst[1:]
        for item in gegevens_lijst:
            item_lijst = item.split(";")
            item_lijst1 = item_lijst[1:]
            antwoord = personaliseer(tekst, item_lijst)
            naam = item_lijst[0]
            naam_bestand = f"{naam}.txt"
            antwoordbrief = open(naam_bestand, "w")
            antwoordbrief.write(antwoord)
            antwoordbrief.close()
    except FileNotFoundError:
        print("")

def personaliseer(brief: str, velden: list) -> str:
    try:
        for i, el in enumerate(velden):
            zoek = "[" + str(i) + "]"
            brief = brief.replace(zoek, el)
    except IOError:
        print("")
    return brief

