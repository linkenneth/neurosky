import time
import subprocess
import re

proc = subprocess.Popen("./analyze.o", shell=True, stdout=subprocess.PIPE)

poor = True
i = 0
SAMPLE_LEN = 10
values = [0]*SAMPLE_LEN
while True:
    line = proc.stdout.readline()
    if line != '':
        signal = line.split(": ")
        if signal[0] == "POOR_SIGNAL":
            if int(signal[1]) == 0:
                poor = False
            else:
                poor = True
        elif poor:
            continue
        elif signal[0] == "RAW":
            print i, signal[i]
            values[i] = int(signal[1])
            i += 1
            if i == SAMPLE_LEN - 1:
                print "RAW: %d" % max(values) - min(values)
                i = 0
            continue
        else:
            print line
