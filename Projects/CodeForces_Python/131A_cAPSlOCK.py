string = input()
completed = False
for i in range(1, len(string)):
    if string[i].islower():
        print(string)
        completed = True
        break;
if completed == False:
    print(''.join(c.lower() if c.isupper() else c.upper() for c in string))