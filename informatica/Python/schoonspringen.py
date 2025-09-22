# https://dodona.ugent.be/nl/courses/1005/series/11144/activities/937993506
aantal_juryleden = int(input())
eerste_score = int(input())
laagste_score = eerste_score
hoogste_score = eerste_score
totale_score = eerste_score
for jurylid in range(aantal_juryleden-1):
    score = int(input())
    totale_score += score
    if score > hoogste_score:
        hoogste_score = score
    if score < laagste_score:
        laagste_score = score

totale_score_min_hoogste_en_laagste = totale_score - laagste_score - hoogste_score
gemiddelde = (totale_score_min_hoogste_en_laagste)/(aantal_juryleden-2)
print(round(gemiddelde))


