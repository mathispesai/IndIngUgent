# https://dodona.ugent.be/nl/courses/1005/series/11163/activities/1741256951
def splits(afmetingen: str)-> list:
    afmetingen_lijst = afmetingen.split("x")
    for char in afmetingen_lijst:
        afmetingen_lijst[afmetingen_lijst.index(char)] = float(char)
    afmetingen_lijst.sort( reverse = True)

    return afmetingen_lijst

def check_dim(bagage: list, max: list)-> bool:
    voorwaarde = False
    tellen = 0
    for char in bagage:
        if char <= max[bagage.index(char)]:
            tellen += 1
    if tellen == len(bagage):
        voorwaarde = True
    return voorwaarde


def print_toeslag(afm_hbg, afm_p_item, limiet_kg_carry, kg_handbagage, kg_p_item):
    extra = check_dim([1,1,1],[1,1,1])
    afm_hbgl = splits(afm_hbg)
    afm_p_iteml = splits(afm_p_item)
    
    tellen = 0
    tellen1 = 0
    voorwaarde1 = "NOK"
    voorwaarde2 = "NOK"
    voorwaarde3 = "NOK"
    counter = 2
    max_hbg = [55,40,23]
    max_p_item = [40,30,10]
    for char in afm_hbgl:
        if char <= max_hbg[afm_hbgl.index(char)]:
            tellen += 1
    if tellen == len(afm_hbgl):
        voorwaarde1 = "OK"
        counter -= 1
    for char in afm_p_iteml:
        if char <= max_p_item[afm_p_iteml.index(char)]:
            tellen1 += 1
    if tellen1 == len(afm_p_iteml):
        voorwaarde2 = "OK"
        counter -= 1
    if (kg_handbagage + kg_p_item) <= limiet_kg_carry:
        voorwaarde3 = "OK"
        #counter -= 1
    toeslag = 0.0    
    if voorwaarde3 == "NOK":
        toeslag = round((kg_handbagage + kg_p_item - limiet_kg_carry)*10)*100/100
    if counter >= 1:
        toeslag += 12.5*(counter)
    print(f"Gewicht: {voorwaarde3}\nAfmetingen handbagage: {voorwaarde1}\nAfmetingen persoonlijk item: {voorwaarde2}\nToeslag: {toeslag}")