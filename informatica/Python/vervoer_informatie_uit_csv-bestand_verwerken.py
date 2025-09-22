# https://dodona.ugent.be/nl/courses/1005/series/11155/activities/1689367998
def tel(soort:str,bestandsnaam: str)->int:
    teller = 0
    with open(bestandsnaam, "r") as invoer:
        for line in invoer:
            lijn_in_lijst = line.split(";")
            if lijn_in_lijst[0] == soort:
                teller += 1
    return teller

def print_type_treinen(bestandsnaam:str):
    with open(bestandsnaam, "r") as invoer:
        for line in invoer:
            lijn_in_lijst1 = line.split(";")
            if lijn_in_lijst1[0] == "TREIN":
                print(lijn_in_lijst1[-1].strip())

def aantal_overladen(bestandsnaam:str)->int:
    teller = 0
    with open(bestandsnaam, "r") as invoer:
        for line in invoer:
            lijn_in_lijst2 = line.split(";")
            if lijn_in_lijst2[0] == "MT":
                if (int(lijn_in_lijst2[2]) + int(lijn_in_lijst2[1])) > int(lijn_in_lijst2[3]):
                    teller += 1
    return teller
                

    