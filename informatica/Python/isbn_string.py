# https://dodona.ugent.be/nl/courses/1005/series/11146/activities/326198308
ISBN_code = input()
som = 0
tellen = 1
ingegeven_controle_getal = ISBN_code[9]
ISBN_code = ISBN_code[:9]
for i in ISBN_code:
    som += int(i)*tellen
    tellen+=1
    ISBN_code = ISBN_code[1:]
controlegetal =  som%11
if controlegetal  == 10:
    if "X" == ingegeven_controle_getal:
        print("OK")
    else:
        print("FOUT")    
elif str(controlegetal) == ingegeven_controle_getal:
    print("OK")
else: 
    print("FOUT")        

