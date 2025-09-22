# https://dodona.ugent.be/nl/courses/1005/series/11209/activities/706225272

# zet je oplossing in de functie main
def main():
    telefoonnummer = input()
    spaties=0
    mogelijkewaarden=" 0123456789"
    aantal=0
    opgeruimdtelnummer= telefoonnummer.replace(" ", "")
    for waarde in telefoonnummer:
        if waarde == " ":
            spaties+=1
    
    for waarde in telefoonnummer:
        if waarde in mogelijkewaarden:
            aantal+=1               
    if telefoonnummer[0]=="0" and (spaties == 0 or spaties==3) and opgeruimdtelnummer.isdigit():           
        if aantal- spaties == 9:
            print("is geldig")
        elif aantal- spaties== 10:
            print("is geldig")

        else:
            print("is niet geldig")        
    else:
        print("is niet geldig")




    
    
# enkel om lokaal te kunnen testen
if __name__ == '__main__':
    main()

