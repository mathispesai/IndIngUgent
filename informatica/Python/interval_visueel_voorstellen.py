# https://dodona.ugent.be/nl/courses/1005/series/11149/activities/160171733
def teken_as(ass:tuple, interval:tuple)->str:
    getekende_as = ""
    tweede_lijn = ""
    laagste = ass[0]
    
    while laagste <= ass[1]:
        if interval[0] <= laagste < interval[1]:
            getekende_as = getekende_as + "*****"
        elif laagste == interval[1] and laagste != ass[1]:
            getekende_as = getekende_as + "*----"
        elif laagste == ass[1] and laagste != interval[1]:
            getekende_as = getekende_as + "-"
        elif laagste == ass[1] and laagste == interval[1]:
            getekende_as = getekende_as + "*"
        else:
            getekende_as = getekende_as + "-----"
        if laagste != ass[1] and len(str(laagste)) == 1:
            tweede_lijn += str(laagste) + "    "
        elif laagste != ass[1] and len(str(laagste)) == 2:
            tweede_lijn += str(laagste) + "   "
        else:
            tweede_lijn += str(laagste) 
        laagste += 1
    print(getekende_as)
    print(tweede_lijn)