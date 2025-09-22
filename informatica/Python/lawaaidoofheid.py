# https://dodona.ugent.be/nl/courses/1005/series/11153/activities/1563511392
def maximale_blootstelling(getal: float) -> float:
    tijd = 0
    uur_seconden = 3600
    if getal < 80:
        tijd = -1
    elif 80 <= getal < 83:
        tijd= uur_seconden * 8
    elif 83 <= getal < 86:
        tijd= uur_seconden * 4
    elif 86 <= getal < 89:
        tijd= uur_seconden * 2
    elif 89 <= getal < 92:
        tijd= uur_seconden * 1
    elif 92 <= getal < 95:
        tijd= uur_seconden / 2
    elif 95 <= getal < 98:
        tijd= uur_seconden / 4
    elif 98 <= getal < 101:
        tijd= uur_seconden * 8
    elif 101 <= getal < 104:
        tijd= uur_seconden / 16
    elif 104 <= getal < 107:
        tijd= uur_seconden / 32
    elif 107 <= getal < 110:
        tijd= uur_seconden / 64
    elif 110 <= getal < 113:
        tijd= uur_seconden / 128
    elif 113 <= getal < 116:
        tijd= uur_seconden / 256
    elif 116 <= getal < 119:
        tijd= uur_seconden / 512
    else:
        tijd= uur_seconden / 1024
    return float(tijd)
   





