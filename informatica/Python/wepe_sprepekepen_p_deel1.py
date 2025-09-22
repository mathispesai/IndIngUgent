# https://dodona.ugent.be/nl/courses/1005/series/11150/activities/919621001

invoer_string = input()
uitvoer_string = ""
while invoer_string:
    if invoer_string[0] in "aeiouAIEOU" and len(invoer_string) != 1:
        if invoer_string[1] in "aeioujAEIOUJ":
            if invoer_string[2] in "aeiouAEIOU":
                if invoer_string[3] in "aeiouAEIOU":
                    uitvoer_string = uitvoer_string + invoer_string[0] + invoer_string[1] + invoer_string[2] + invoer_string[3] + "p" + invoer_string[0].lower() + invoer_string[1].lower() + invoer_string[2].lower() +  invoer_string[3].lower()
                    invoer_string = invoer_string[3:]
                else:
                    uitvoer_string = uitvoer_string + invoer_string[0] + invoer_string[1] + invoer_string[2] + "p" + invoer_string[0].lower() + invoer_string[1].lower() + invoer_string[2].lower()
                    invoer_string = invoer_string[2:]
            else:
                uitvoer_string = uitvoer_string + invoer_string[0] + invoer_string[1] + "p" + invoer_string[0].lower() + invoer_string[1].lower()
                invoer_string = invoer_string[1:]
        else:
            uitvoer_string = uitvoer_string + invoer_string[0] + "p" + invoer_string[0].lower() 
    elif invoer_string[0] in "aeiouAIEOU" and len(invoer_string) == 1:
        uitvoer_string = uitvoer_string + invoer_string[0] + "p" + invoer_string[0].lower()
    else:
        uitvoer_string += invoer_string[0]
    invoer_string = invoer_string[1:] 

print(uitvoer_string)
        
