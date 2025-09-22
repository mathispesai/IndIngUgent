# https://dodona.ugent.be/nl/courses/1005/series/11155/activities/1469049593
def lees(bestandsnaam:str)->str:
    with open(bestandsnaam, "r") as invoer:
        inhoud = invoer.read()
        print(inhoud.strip())