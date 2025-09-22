# https://dodona.ugent.be/nl/courses/1005/series/11209/activities/479272303

# zet je oplossing in de functie main
def main():
    getal= input()
    omgekeerdgetal= getal[::-1]
    som= str(int(getal)+int(omgekeerdgetal))
    if som == som[::-1]:
        print("voldoet")
    else:
        print("voldoet niet")

    
    
# enkel om lokaal te kunnen testen
if __name__ == '__main__':
    main()

