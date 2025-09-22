# https://dodona.ugent.be/nl/courses/1005/series/11163/activities/1755017014
def veelterm(p: int, v: list)-> str:
    lijst_gepriemd = []
    print_veelterm = ""
    tellen = len(v) -1
    indexen = 0
    lijst_kopie = []
    for char in v:
        lijst_gepriemd.append(char%p)
    for char in lijst_gepriemd:
        lijst_kopie = lijst_gepriemd[indexen+1:]
        if char not in [0,1]:
            kl = char
            if tellen == 0:
                print_veelterm += f"{kl}"
            elif tellen == 1 and lijst_gepriemd[indexen + 1] != 0:
                print_veelterm += f"{kl}x + "
            elif tellen == 1:
                print_veelterm += f"{kl}x"
            elif lijst_kopie.count(0) == len(lijst_kopie):
                print_veelterm += f"{kl}x^{tellen}"
            else:
                print_veelterm += f"{kl}x^{tellen} + "
        elif char == 1:
            kl = char
            if tellen == 0:
                print_veelterm += "1"
            elif tellen == 1 and lijst_gepriemd[indexen + 1] != 0:
                print_veelterm += "x + "
            elif tellen == 1:
                print_veelterm += "x"
            elif lijst_kopie.count(0) == len(lijst_kopie):
                print_veelterm += f"x^{tellen}"
            else:
                print_veelterm += f"x^{tellen} + "
        tellen -= 1
        indexen += 1
    return print_veelterm