input()
numbers = list(map(int, input().split()))
evens = 0; odds = 0; espot = -1; ospot = -1
for i in range(0, len(numbers)):
    if numbers[i]%2 == 0:
        evens += 1
        espot = i
    else:
        odds += 1
        ospot = i

print(espot+1) if evens==1 else print(ospot+1)