# https://dodona.ugent.be/nl/courses/1005/series/11209/activities/2045671604

# zet je oplossing in de functie main
def main():
    import string
    woord= input()
    letterteller=0
    for letter in woord:
        letterteller+=1
        print(letter*(letterteller)+ string.punctuation[(letterteller-1)]*(len(woord)-letterteller))
    
    
# enkel om lokaal te kunnen testen
if __name__ == '__main__':
    main()

