drives = int(input())
size = int(input())
sizes = []
count = 0

[sizes.append(int(input())) for i in range(drives)]

for s in sorted(sizes, reverse=True):
    size -= s
    count += 1
    if size <= 0:
        break;

print(count)