# https://dodona.ugent.be/nl/courses/1005/series/11155/activities/1473624685
def schrijf_gemeenten(postcode:int)->str:
    teller = 0
    with open("provincieSteden.txt", "r") as invoer:
        for line in invoer:
            if len(str(postcode)) == 4:
                if str(postcode) in line:
                    gemeente_lijst = line.split(" ")
                    if len(gemeente_lijst) == 3:
                        print(gemeente_lijst[2].strip())
                        teller += 1
                    else: 
                        print(gemeente_lijst[2].strip() + " " + gemeente_lijst[3].strip())
                        teller += 1
        if teller == 0 :
            print("Geen enkele")