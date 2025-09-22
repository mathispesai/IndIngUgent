# https://dodona.ugent.be/nl/courses/1005/series/11155/activities/1584086136
def geeflijn(bestandsnaam:str, lijnnummer:str)->str:
    with open(bestandsnaam, "r") as invoer:
        bestand = invoer.readlines()
        if lijnnummer > len(bestand):
            return_string = ""
        elif lijnnummer < 0: 
            return_string = bestand[-1]
        else:
            return_string = bestand[lijnnummer-1]
    return return_string.strip()