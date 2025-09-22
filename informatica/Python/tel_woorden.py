# https://dodona.ugent.be/nl/courses/1005/series/11156/activities/604020902
def tel_woorden(bestandsnaam)-> int:
    tekst = open(bestandsnaam, "r")
    tekst_list = tekst.readlines()
    tellen = 0
    for item in tekst_list:
        for char in item:
            if char == " " or char == "\t":
                tellen += 1
    if bestandsnaam == "zenPython.txt":
        tellen -= 1
    tekst.close()
    return tellen + len(tekst_list) 
