input()
stones = input()
removed = 0

i = 0
while i < len(stones) - 1:
    if stones[i] == stones[i+1]:
        removed += 1
        j = i + 2
        whiled = False
        while j < len(stones) and stones[j] == stones[i]:
            removed += 1
            j += 1
            whiled = True
        if whiled:
            i = j -1
    i += 1
print(removed)
