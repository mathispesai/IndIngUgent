# https://dodona.ugent.be/nl/courses/1005/series/11159/activities/286541767
def splitsing(woord:str)->tuple:
    prefix = ""
    suffix = ""
    while woord:
        if woord[0] not in "aeiouAEIOU":
            prefix += woord[0]
            woord = woord[1:]
        else:
            suffix = woord
            woord = ""
    return (prefix,suffix)

def kruising(woord1:str, woord2:str)->tuple:
    een = splitsing(woord1)
    twee = splitsing(woord2)
    return (een[0]+twee[1],twee[0]+een[1])