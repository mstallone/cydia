i = int(input())

beacons =[]
for j in range(i):
    beacons.append(list(map(int, input().split())))

count = 0
b = len(beacons)-1
beacons = sorted(beacons)

while b > 0:
    if beacons[b][0]-beacons[b][1] <= beacons[b-1][0]:
        b-=1
        count += 1
    b -= 1

print(count)