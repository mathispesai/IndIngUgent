# https://dodona.ugent.be/nl/courses/1005/series/11149/activities/2008384100

import string

def tel_tekens(zin: str, tekens: str) -> int:
    aantal = 0
    for teken in zin:
        if teken in tekens:
            aantal += 1
    return aantal

    
def sterkte_wachtwoord(woord: str) -> str:
    sterkte = 0
    sterkte_string = ""
    if len(woord) >= 8:
        sterkte += 1
    if tel_tekens(woord, string.ascii_uppercase):
        sterkte += 1
    if tel_tekens(woord, string.ascii_lowercase):
        sterkte += 1
    if tel_tekens(woord, string.digits):
        sterkte += 1
    if tel_tekens(woord, string.punctuation):
        sterkte += 1
    if sterkte == 5:
        sterkte_string = "sterk"
    if 3 <= sterkte < 5:
        sterkte_string = "matig"
    elif sterkte < 3:
        sterkte_string = "zwak"
    return sterkte_string

        





