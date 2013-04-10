def processBlink (accuracy, THR):
    """"processBlink for Raw signals
    input: int accuracy a FOURTH of how many samples
    to use for detection.
    THR - threshold for triggering a detection.
    output: process, pass the samples to this function """
    SSIZE = accuracy
    MIN = 9999
    MAX = -9999
    i = 1
    accum = 0
    def process (sample):
        """processoutput: 1 - detected blink
                          0 - need more samples doesnt know yet
                         -1 - detected no blinks"""
        nonlocal SSIZE, MIN, MAX, accum, i
        accum += sample
        if (i % SSIZE) == 0: # For grouping
           avg = accum / SSIZE  # python 3 floatdiv
           if avg < MIN:
               MIN = avg
           if avg > MAX:
               MAX = avg
           accum = 0
        if i == SSIZE * 4:
           i = 1
           diff = MAX - MIN
           MAX = -9999
           MIN = 9999
           if diff > THR:
               return 1
           else:
               return -1
        else:
            i += 1
            return 0

    return process
