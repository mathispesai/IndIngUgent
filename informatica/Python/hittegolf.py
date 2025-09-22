# https://dodona.ugent.be/nl/courses/1005/series/11144/activities/1765918609
temperatuur = input()
opeenvolgende_dagen = 0
aantal_dagen30 = 0
while temperatuur != "stop":
    if float(temperatuur) >= 30.0:
        opeenvolgende_dagen += 1
        aantal_dagen30 += 1
    elif float(temperatuur) >=25.0:
        opeenvolgende_dagen += 1
    elif (opeenvolgende_dagen < 5): 
        opeenvolgende_dagen = 0
        aantal_dagen30 = 0
    elif (aantal_dagen30 < 3): 
        opeenvolgende_dagen = 0
        aantal_dagen30 = 0
    temperatuur = input()
if (opeenvolgende_dagen >= 5) and (aantal_dagen30 >= 3):
    print("hittegolf")
else:
    print("geen hittegolf")


