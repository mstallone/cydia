numberOfTests = input()
x = 0
for i in range(0, int(numberOfTests)):
    if "+" in input():
        x += 1
    else:
        x -= 1

print(x)