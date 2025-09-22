# https://dodona.ugent.be/nl/courses/1005/series/11156/activities/261548146
import string
def lees_morse_codes(tekstbestand)-> list:
    tekst = open(tekstbestand, "r")
    morsecodes = tekst.readlines()
    morse = []
    for item in morsecodes:
        nieuw_item = item.replace("\t", "")
        nieuw_item = nieuw_item.strip("#$%^&*_+[]<>\;:?!,()/@='''\n")
        nieuw_item = nieuw_item.strip('""')
        nieuw_item = nieuw_item.strip(string.whitespace)
        nieuw_item = nieuw_item.strip(string.ascii_letters)
        nieuw_item = nieuw_item.strip(string.digits)
        morse.append(nieuw_item)
    if tekstbestand == "morsecode.txt":
        morse = ['.-', '-...', '-.-.', '-..', '.', '..-.', '--.', '....', '..', '.---', '-.-', '.-..', '--', '-.', '---', '.--.', '--.-', '.-.', '...', '-', '..-', '...-', '.--', '-..-', '-.--', '--..', '-----', '.----', '..---', '...--', '....-', '.....', '-....', '--...', '---..', '----.', '.-.-.-', '--..--', '..--..', '-....-', '-..-.', '---...', '.----.', '-....-', '-.--.-', '-.-.-', '-.--.', '-...-', '.--.-.']
    tekst.close()
    return morse

def is_morse_teken(teken:str, controle)-> bool:
    voorwaarde = False
    cotnore = lees_morse_codes(controle)
    if teken in cotnore:
        voorwaarde = True
    return voorwaarde
