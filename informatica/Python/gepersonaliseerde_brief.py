# https://dodona.ugent.be/nl/courses/1005/series/11163/activities/1701274884
import string 
def personaliseer(brief: string, velden: list)-> string:
    brief = brief.replace("[","")
    brief = brief.replace("]","")
    i = 0
    while len(velden)!= 0:
        brief = brief.replace(f"{i}", velden[0])
        i += 1
        velden = velden[1:]
    return brief
           

