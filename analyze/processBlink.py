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
