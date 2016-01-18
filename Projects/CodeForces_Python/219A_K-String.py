k = int(input())
string = list(input())

counts = {}
fails = False
for ch in set(string):
    number = string.count(ch)
    if number%k == 0:
        counts[ch] = int(number/k)
    else:
        print(-1)
        fails = True
        break

output = ""
if not fails:
    for key in counts.keys():
        output += key*int(counts[key])
    print(output*k)