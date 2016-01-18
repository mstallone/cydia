input()
coins = sorted(list(map(int, input().split())), reverse=True)
sum = sum(coins)
me = 0
i = 0
while me <= sum/2 and i <= len(coins):
    me += coins[i]
    i += 1
print(i)