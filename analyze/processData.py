import time
import subprocess
import re

proc = subprocess.Popen("./analyze.out", shell=True, stdout=subprocess.PIPE)

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

def processBlink (accuracy, THR):
""""processBlink 
        input: int accuracy how many sample to use for detection
               THR - threshold for triggering a detection
        output: process, pass the samples to this function"""
    SSIZE = accuracy
    i = 0
    accum = 0
    def process (sample):
        """processoutput: 1 - detected blink
                          0 - need more samples doesnt know yet
                         -1 - detected no blinks"""
        nonlocal SSIZE, accum, i
        if (i != SSIZE):
           accum += sample
           return 0
        else:
           i = 0
           if (accum/sample > THR):
               return 1
           else:
               return -1
    return process


        
    

