# https://dodona.ugent.be/nl/courses/1005/series/11146/activities/1476719838
getal = input()
som = 0
for i in getal:
    som += int(i)**len(getal)
if som == int(getal):
    print("narcistisch")
else: 
    print("niet narcistisch")
