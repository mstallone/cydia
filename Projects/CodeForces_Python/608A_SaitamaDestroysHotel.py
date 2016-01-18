i, floor = input().split()

floors =[]
for j in range(int(i)):
    floors.append(list(map(int, input().split())))

current = int(floor)
f = len(floors) - 1
time = 0

while f >= 0:
    time += (current - floors[f][0])
    current = floors[f][0]
    if floors[f][1] > time:
        time += (floors[f][1] - time)
    f -= 1

print(time+current)