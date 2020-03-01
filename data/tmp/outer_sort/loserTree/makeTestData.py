file_count = 7
pre_size = 10000

exter_files = [0, 3, 2]

exter_count = [1, 8, 4]

exter_max = 8

fs=[]
for i in range(0,file_count):
    f = open('data/run'+str(i),'w')
    fs.append(f)

i = 1
for r in range(0, pre_size):
    for f in fs:
        f.write(str(i) + ' ')
        i += 1

for e in range(0, exter_max):
    for j in range(0, len(exter_files)):
        index = exter_files[j]
        if exter_count[j] > 0:
            fs[index].write(str(i) + ' ')
            i+=1
            exter_count[j] -= 1

for f in fs:
    f.close()

print(i - 1)
