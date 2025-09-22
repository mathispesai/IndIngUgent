# https://dodona.ugent.be/nl/courses/1005/series/11144/activities/1070180227
import math
n=11
benadering = 0
for i in range(1,n+1):
    benadering += i/math.factorial(i)
    if i > 2.53:
        print(f"{benadering:.7f}")
    else:
        print(f"{benadering:.1f}")

    

