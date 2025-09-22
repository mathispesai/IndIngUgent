# https://dodona.ugent.be/nl/courses/1005/series/11146/activities/2021287538
startgetal = int(input())
print(f"a {str(startgetal)[::-1]}")
for i in range(1,26):
    startgetal *= 2 
    gespiegeld = str(startgetal)
    startgetal = int(gespiegeld)
    gespiegeld2 = gespiegeld[::-1]
    letter = chr(97+i)
    print(f"{letter} {gespiegeld2}")
