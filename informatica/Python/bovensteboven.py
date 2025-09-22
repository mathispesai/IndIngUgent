# https://dodona.ugent.be/nl/courses/1005/series/11153/activities/581452346

def bovensteboven(n: int) -> bool:
    n = str(n)
    bovensteboven_woord = ""
    voorwaarde1 = True
    voorwaarde2 = False
    for char in n:
        if char in "23457":
            voorwaarde1 = False
        else:
            if char == "6":
                bovensteboven_woord += "9"
            elif char == "9":
                bovensteboven_woord += "6"
            elif char == "1":
                bovensteboven_woord += "1"
            elif char == "0":
                bovensteboven_woord += "0"    
            else:
                bovensteboven_woord += "8"
    bovensteboven_woord = bovensteboven_woord[::-1]
    if bovensteboven_woord == n and voorwaarde1:
        voorwaarde2 = True 
    return voorwaarde2

def volgende(n: int) -> int:
    n += 1
    while not bovensteboven(n):
        n += 1
    return n


        

