# https://dodona.ugent.be/nl/courses/1005/series/11153/activities/1205242433
def langste_staart(woord1:str,woord2:str)->str:
    teller= 1
    omgekeerd_woord1 = woord1[::-1].strip(" ")
    omgekeerd_woord2 = woord2[::-1].strip(" ")
    while omgekeerd_woord1[:teller] == omgekeerd_woord2[:teller] and (teller < (len(woord1) + 1) or teller < (len(woord2)+1)):
        teller += 1
    rijm = omgekeerd_woord1[:teller-1]
    return rijm[::-1]

def zijn_rijmwoorden(woord1:str,woord2:str)->bool:
    voorwaarde = False
    rijmstuk = langste_staart(woord1, woord2)
    if rijmstuk != "" and rijmstuk.strip("bcdfghjklmnpqrstvwqyz") != "":
        voorwaarde = True
    return voorwaarde
