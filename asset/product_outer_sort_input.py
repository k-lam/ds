import random

round_count = 5000
pre_round = 1000
random_from = 1
random_to = 2147483648 - 1

f = open('outer_sort_input', 'w')
for i in range(0, round_count):
    s = ''
    for j in range(0, pre_round):
        s += str(random.randint(random_from + j, int(random_to / (i + 1))))
        s += ' '
    f.write(s + '\r\n')

f.close()
