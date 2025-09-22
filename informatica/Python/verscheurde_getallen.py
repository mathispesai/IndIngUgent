# https://dodona.ugent.be/nl/courses/1005/series/11146/activities/832327794
getal = input()
verscheurd = 0
for i in range(0,len(getal)):
    deel1 = getal[:i]
    deel2 = getal[i:]    
    if deel1 !="" and deel2 !="":
        fromule = (int(deel1)+int(deel2))**2
        if (int(deel1)+int(deel2))**2 == int(getal):       
            verscheurd += 1
if verscheurd != 0:
    print("verscheurd")
else:
    print("niet verscheurd")


 
