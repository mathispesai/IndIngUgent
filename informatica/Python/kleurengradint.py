# https://dodona.ugent.be/nl/courses/1005/series/11144/activities/70713210
aantal_rgb_waarden = int(input())
koppel1_r = int(input())
koppel1_g = int(input())
koppel1_b = int(input())
koppel2_r = int(input())
koppel2_g = int(input())
koppel2_b = int(input())


for waarde in range(aantal_rgb_waarden):
    tussenwaarde_r = round(koppel1_r + waarde / (aantal_rgb_waarden-1) * (koppel2_r-koppel1_r))
    tussenwaarde_g = round(koppel1_g + waarde / (aantal_rgb_waarden-1) * (koppel2_g-koppel1_g))
    tussenwaarde_b = round(koppel1_b + waarde / (aantal_rgb_waarden-1) * (koppel2_b-koppel1_b))
    print(f"rgb({tussenwaarde_r}, {tussenwaarde_g}, {tussenwaarde_b})")