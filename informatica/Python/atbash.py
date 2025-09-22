# https://dodona.ugent.be/nl/courses/1005/series/11150/activities/1206238426
aantal = int(input())
teller = 0
letter1 = "ABCDEFGHIJKLM"
letter2 = "ZYXWVUTSRQPON"

while teller < aantal:
    print_tekst = ""
    regel = input()
    for teken in regel:
        if teken.isalpha() and teken.isupper() and teken in letter1:
            print_tekst += letter2[letter1.index(teken)]
        elif teken.isalpha() and teken.isupper() and teken in letter2:
            print_tekst += letter1[letter2.index(teken)]    
        elif teken.isalpha() and teken.islower() and teken.swapcase() in letter1:
            print_tekst += letter2[letter1.index(teken.swapcase())].swapcase()
        elif teken.isalpha() and teken.islower() and teken.swapcase() in letter2:
            print_tekst += letter1[letter2.index(teken.swapcase())].swapcase()
        else:
            print_tekst += teken
    print(print_tekst)
    teller += 1