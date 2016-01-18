numberOfTests = input()
cap = 0
now = 0
for i in range(0, int(numberOfTests)):
    sit = input().split()
    now -= int(sit[0])
    now += int(sit[1])
    if now > cap:
        cap = now

print(cap)