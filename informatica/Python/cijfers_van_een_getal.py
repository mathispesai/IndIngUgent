# https://dodona.ugent.be/nl/courses/1005/series/11209/activities/19150984

# zet je oplossing in de functie main
def main():
    ingegevengetal=input()
    mogelijkegetallen="0123456789"
    bijhouden=0
    for getal in ingegevengetal:
        if getal in mogelijkegetallen:
            bijhouden+=1
            mogelijkegetallen= mogelijkegetallen[:mogelijkegetallen.index(getal)] + mogelijkegetallen[mogelijkegetallen.index(getal)+1:]
    if bijhouden==10:
        print("alle cijfers komen voor")
    else:
        print("er ontbreken",10-bijhouden,"cijfers")
    
    
# enkel om lokaal te kunnen testen
if __name__ == '__main__':
    main()

