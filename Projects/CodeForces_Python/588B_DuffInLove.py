import math
n = int(input())

def factors(n):
    return set(x for tup in ([i, n//i] for i in range(1, int(n**0.5)+1) if n % i == 0) for x in tup)

for factor in sorted(factors(n), reverse=True):
    failed = False
    for f in factors(factor):
        if f != 1 and f == int(math.sqrt(f))**2:
            failed = True
            break
    if not failed:
        print(factor)
        break
