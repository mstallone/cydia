numberOfTests = input()
numberOfYesTests = 0

for i in range(0, int(numberOfTests)):
    test = input().split()
    count = 0
    for c in test:
        if c == "1":
            count += 1
    if count >= 2:
        numberOfYesTests += 1

print(numberOfYesTests)