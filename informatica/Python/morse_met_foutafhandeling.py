# https://dodona.ugent.be/nl/courses/1005/series/11156/activities/2005979547
import string
def lees_morse_codes(tekstbestand)-> list:
    try:
        tekst = open(tekstbestand, "r")
        morsecodes = tekst.readlines()
        morse = []
        morse2 = []
        for item in morsecodes:
            if item[0] in "-.":
                nieuw_item = item[1:] 
            else:
                nieuw_item = item
            nieuw_item = nieuw_item.replace("\t", "")
            nieuw_item = nieuw_item.strip("#$%^&*_+[]<>\;:?!,()/@='''\n")
            nieuw_item = nieuw_item.strip('""')
            nieuw_item = nieuw_item.strip(string.whitespace)
            nieuw_item = nieuw_item.strip(string.ascii_letters)
            nieuw_item = nieuw_item.strip(string.digits)
            morse.append(nieuw_item)
        tekst.close()
        correct = ['.-', '-...', '-.-.', '-..', '.', '..-.', '--.', '....', '..', '.---', '-.-', '.-..', '--', '-.', '---', '.--.', '--.-', '.-.', '...', '-', '..-', '...-', '.--', '-..-', '-.--', '--..', '-----', '.----', '..---', '...--', '....-', '.....', '-....', '--...', '---..', '----.', '.-.-.-', '--..--', '..--..', '-....-', '-..-.', '---...', '.----.', '-....-', '-.--.-', '-.-.-', '-.--.', '-...-', '.--.-.']
        for lijn in morse: 
            if lijn in correct:
                morse2.append(lijn)
    except FileNotFoundError:
        morse2 = []
    return morse2


