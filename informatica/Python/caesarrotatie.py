# https://dodona.ugent.be/nl/courses/1005/series/11150/activities/105361566
verschuiving = int(input())
zin = input()
print_tekst = ""
alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
alfabet2 = 26* " "
alfabet2_list = list(alfabet2)
for letter in alfabet:
    if alfabet.index(letter)+verschuiving < 25:
        alfabet2_list[alfabet.index(letter)+verschuiving] = letter
    else:
        alfabet2_list[alfabet.index(letter)+verschuiving-26] = letter
alfabet3 = str(alfabet2_list)
alfabet3 = alfabet3.strip(" ,''[]', ")
alfabet3 = alfabet3.replace(",", "")
alfabet3 = alfabet3.replace(" ", "")
alfabet3 = alfabet3.replace("'", "")
for teken in zin:
    if teken in alfabet:
        print_tekst += alfabet3[alfabet.index(teken)]
    elif teken.swapcase() in alfabet:
        print_tekst += alfabet3[alfabet.index(teken.swapcase())].swapcase()
    else:
        print_tekst += teken

print(print_tekst)