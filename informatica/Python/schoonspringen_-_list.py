# https://dodona.ugent.be/nl/courses/1005/series/11160/activities/315865179
def lees_scores()->list:
    score = input()
    scores_list = []
    while score != "stop":
        scores_list.append(int(score))
        score = input()
    return scores_list

def bereken_score(scores:list)->int:
    scores1 = []
    for i in scores:
        if 0 <= i <= 100:
            scores1.append(i)
    maximum = max(scores1)
    minimum = min(scores1)
    som = 0
    aantal_meegerekende_scores = 0
    teller1 = 0
    teller2 = 0
    for item in scores1:
        if item == maximum and teller1 == 0:
            teller1 += 1
        elif item == minimum and teller2 == 0:
            teller2 += 1
        else:    
            som += item
            aantal_meegerekende_scores += 1
    gemiddelde = som / aantal_meegerekende_scores
    eindscore = round(gemiddelde)
    return eindscore