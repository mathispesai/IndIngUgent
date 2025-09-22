# https://dodona.ugent.be/nl/courses/1005/series/11144/activities/990750894
willekeurig_startbedrag = int(input())
totaal = willekeurig_startbedrag
gefluisterd_bedrag = input()
aantal=0
while gefluisterd_bedrag != "stop":
    totaal += int(gefluisterd_bedrag)
    aantal+=1
    print(f"werknemer #{aantal} fluistert €{totaal}")
    gefluisterd_bedrag = input()
gemiddelde = (totaal - willekeurig_startbedrag)/aantal
print(f"gemiddeld loon: €{gemiddelde:.2f}")

