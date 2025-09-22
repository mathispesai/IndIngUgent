# https://dodona.ugent.be/nl/courses/1005/series/11155/activities/76247163
def kopieer(bron:str, doel:str):
    with open(bron,mode='r') as br, open(doel, mode='w') as dl:
        for line in br:
            print(line,file=dl, end='')

