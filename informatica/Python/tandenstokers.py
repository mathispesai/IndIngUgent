# https://dodona.ugent.be/nl/courses/1005/series/11146/activities/1980381934
ingelezen_vergelijking = input()
aantal_tandenstokers = 0
for i in ingelezen_vergelijking:
    #aantal nodige tandenstokers tellen
    if i == "|":
        aantal_tandenstokers+= 1
    elif i == "x" or i == "+":
        aantal_tandenstokers += 2

#berekening vormen
zonder_spatie = ingelezen_vergelijking.replace(" ","")
lijst_vermenigvuldigen = zonder_spatie.split("+")
for item in lijst_vermenigvuldigen:
    i = 0
    zaak = 0
    tellen = 0
    tellen2 = -1
    while item[i] != "x":
        tellen += 1
        i += 1
    if item[i] == "x":
        for zaak in range(item[i],len(item)+1):
            tellen2 += 1
            zaak += 1
        vermenigvuldig= tellen*tellen2

        

    
    

    

    
zonder_spatie1 = zonder_spatie.replace("x", " x ")
zonder_spatie2 = zonder_spatie1.replace("+", " + ")    
print(f"{zonder_spatie2}={oplossing} ({aantal_tandenstokers} tandenstokers)")
    
        

