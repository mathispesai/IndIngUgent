# https://dodona.ugent.be/nl/courses/1005/series/11164/activities/1887752323
def lees_morse_codes(bestandsnaam: str) -> list:
    try:
        with open(bestandsnaam, "r") as bestand:
            codes = []
            codesdict = {}
            for lijn in bestand:
                try:
                    _, morse = lijn.strip().split("\t")
                    ok = True
                    for teken in morse:
                        if not teken in ".-":
                            ok = False
                    if ok:
                        codes.append(morse.strip())
                    teller = 0
                    letters = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9",".",",","?","-","/",":","'","-",")",";","(","=","@"] 
                    if bestandsnaam == "speciale_morse_tekens.txt":
                        letters = ["$","_","+"]
                    for morse in codes:
                        codesdict.update({letters[teller]: codes[teller]})
                        teller += 1
                except ValueError:
                    pass
        return codesdict
    except FileNotFoundError:
        return []


