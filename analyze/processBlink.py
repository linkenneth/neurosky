def processBlink (accuracy, THR):
    """"processBlink for Raw signals
    input: int accuracy a FOURTH of how many samples
    to use for detection.
    THR - threshold for triggering a detection.
    output: process, pass the samples to this function """
    SSIZE = accuracy
    MIN = -1
    MAX = 9999
    i = 0
    accum = 0
    def process (sample):
        """processoutput: 1 - detected blink
                          0 - need more samples doesnt know yet
                         -1 - detected no blinks"""
        nonlocal SSIZE, MIN, MAX, accum, i
        if (i == SSIZE*4):
           i = 0
           diff = MAX - MIN
           MAX = 9999
           MIN = -1
           if (diff > THR):
               return 1
           else:
               return -1
        if ((i % SSIZE) == 0): #For grouping
           avg = accum/SSIZE
           if (avg < MIN or MIN == -1):
               MIN = avg
           if (avg > MAX or MAX == 9999):
               MAX = avg
           accum = 0
           i += 1
        elif (i != SSIZE*4): #SSIZE*4: total amount of samples
           accum += sample
           i += 1
           return 0

    return process
