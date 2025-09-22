# https://dodona.ugent.be/nl/courses/1005/series/11155/activities/412537739
def alle_postcodes(provincie:str)->list:
    lijst_met_steden = []
    with open("provincieSteden.txt", "r") as invoer:
        for line in invoer:
            if provincie in line:
                lijst_per_stad = line.split(" ")
                lijst_met_steden.append(lijst_per_stad[1].strip())
    l3 = list(dict.fromkeys(lijst_met_steden))
    for item in l3:
        item = int(item)
    l4= l3.sort()
    for item in l4:
        item = str(item)
    return l4