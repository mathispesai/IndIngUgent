# https://dodona.ugent.be/nl/courses/1005/series/11144/activities/1950433559
getal = input()
som_cijfers = 0
for cijfer in getal:
    som_cijfers += int(cijfer)
if not(int(getal) % som_cijfers):
    print(f"{getal} is een Harshadgetal")
else:
    print(f"{getal} is geen Harshadgetal")



