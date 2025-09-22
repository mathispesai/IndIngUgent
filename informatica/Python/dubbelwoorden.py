# https://dodona.ugent.be/nl/courses/1005/series/11148/activities/342021252
aantal_woorden = int(input())
for i in range(aantal_woorden):
    woord = input()
    origineel_woord = woord
    woord = woord.lower()
    dubbelchecker = 0
    begin = 0
    for char in woord:
        tellen = 0         
        woord = woord[1:]
        woord_kopie = woord
        if char not in woord_kopie and char == origineel_woord[0] and dubbelchecker == 0:
            begin += 1
        while char in woord_kopie and woord_kopie :
            if char == woord_kopie[0]:
                tellen += 1                
            woord_kopie = woord_kopie[1:]
            if tellen == 1:
                dubbelchecker += 1
                woord_kopie = woord_kopie.strip(char)



    if dubbelchecker == int(len(origineel_woord)/2) and not begin:
        print(origineel_woord)
