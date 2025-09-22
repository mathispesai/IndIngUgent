# https://dodona.ugent.be/nl/courses/1005/series/11209/activities/657416640

# zet je oplossing in de functie main
def main():
    minleeftijd= int(input())
    startleeftijd= minleeftijd
    aantalparen=0
    while 9< minleeftijd < 100:
        if minleeftijd<int(str(minleeftijd)[::-1])<99:
            aantalparen+=1
            minleeftijd+=1
        else:
            minleeftijd+=1
    if 9< startleeftijd < 100:
        print(aantalparen)
    else:
        print(36)
    
    
# enkel om lokaal te kunnen testen
if __name__ == '__main__':
    main()

