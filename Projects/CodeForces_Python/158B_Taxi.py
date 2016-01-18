import math
input()
groups = list(map(int, input().split()))
g1 = groups.count(1)
g2 = groups.count(2)
g3 = groups.count(3)
taxis = groups.count(4)
while g1 > 0 and g3 > 0:
    g1 -= 1
    g3 -= 1
    taxis += 1

taxis += g3

while g2 > 1:
    g2 -= 2
    taxis += 1

while g1 > 0 and g2 > 0:
    g1 -= 2
    g2 -= 1
    taxis += 1

taxis += g2
taxis += math.ceil(g1/4)
print(int(taxis))