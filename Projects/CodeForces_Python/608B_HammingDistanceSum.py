a = input()
b = input()
sum = 0

if len(a) == len(b):
    for i in range(len(a)):
        sum += abs(int(a[i])-int(b[i]))
    print(sum)
else:
    if len(a) < len(b):
        c = a
        a = b
        b = c

    for i in range(0, len(a)-len(b)+1, 1):
        for j in range(len(b)):
            sum += abs(int(a[i+j])-int(b[j]))
    print(sum)