# https://dodona.ugent.be/nl/courses/1005/series/11155/activities/1320951900
def zoektekst(bestandsnaam:str)->str:
    return_tekst = ""
    with open(bestandsnaam, "r") as invoer:
        for lijn in invoer:
            lengte_lijst = lijn.split(" ")
            if int(lengte_lijst[0]) != len(lengte_lijst[1].strip()):
                return_tekst += lengte_lijst[1].strip() + " "

    return return_tekst.strip()