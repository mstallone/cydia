import math
length = int(input())
servers = list(map(int, input().split()))
average = sum(servers)/length
upper = math.ceil(average)
lower = int(average)

overs = 0
unders = 0

for server in servers:
    if server >= upper:
        overs += server-upper
    if server < lower:
        unders += lower-server

print(overs+(overs-unders))