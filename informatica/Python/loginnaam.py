# https://dodona.ugent.be/nl/courses/1005/series/11148/activities/703925580
import string
def maak_login(naam: string) -> string:
    loginnaam = naam[0]
    spatie = naam.index(" ")
    if len(naam) > spatie+5:
        for i in range(spatie+1, spatie+5):
            loginnaam += naam[i]
        
    else:
        loginnaam += naam[spatie+1:]
    loginnaam = loginnaam.lower()
    return loginnaam




