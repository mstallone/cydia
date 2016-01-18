n = int(input().split()[0])
total = (n*(n-1))/2

books = input().split()

for b in set(books):
    count = books.count(b)
    if count > 1:
        total -= (count*(count-1))/2

print(int(total))