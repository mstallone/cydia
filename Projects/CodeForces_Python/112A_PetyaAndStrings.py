first = input().upper()
second = input().upper()

if first == second:
    print(0)
else:
    print(-1) if first < second else print(1)