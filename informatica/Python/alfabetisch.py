# https://dodona.ugent.be/nl/courses/1005/series/11160/activities/117689921
def alfabetisch(tekst:str)->str:
    tekst_list = tekst.split(" ")
    tekst_list.sort()
    tekst_string = str(tekst_list)
    tekst_string = tekst_string.strip(" ")
    tekst_string = tekst_string.replace("[", "")
    tekst_string = tekst_string.replace("]", "")
    tekst_string = tekst_string.replace(",", "")
    tekst_string = tekst_string.replace("'", "")
    return tekst_string