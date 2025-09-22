# https://dodona.ugent.be/nl/courses/1005/series/11148/activities/23570674
import string
aantal_zinnen = int(input())
for i in range(aantal_zinnen):
    zin = input()
    zin = zin.lower()
    zin = zin.strip(" ")
    for char in zin:
        if char not in string.ascii_letters:
            zin = zin[:zin.index(char)] + zin[zin.index(char)+1:]
    if zin == zin[::-1]:
        print("palindroomzin")
    else:
        print("geen palindroomzin")        

