import time
import subprocess
import serial

s = serial.Serial(14, 9600)  # COM15

def broadcast(data):
    s.write(bytes(data))

proc = subprocess.Popen("analyze.o", shell = True, stdout = subprocess.PIPE)

poor = False
i = 0
SAMPLE_LEN = 40
values = [0] * SAMPLE_LEN
direction = 'L'
blinking = False

while True:
    line = proc.stdout.readline()
    if line != '':
        signal = line.split(": ")
        if signal[0] == "POOR_SIGNAL":
            if int(signal[1]) == 200:
                poor = True
            else:
                poor = False
            print line
        elif poor:
            continue
        if signal[0] == "RAW":
            values[i] = int(signal[1])
            i += 1
            if i == SAMPLE_LEN - 1:
                diff = max(values) - min(values)
                if diff > 2000:
                    blinking = True
                    print "RAW DIFF: %d" % diff
                elif blinking:
                    direction = 'R' if direction == 'L' else 'L'
                    broadcast(direction)
                    blinking = False
                i = 0
            continue
        elif signal[0] == "MEDITATION":
            if int(signal[1]) > 55:
                # move forward
                broadcast("F")
            else:
                # stop
                broadcast("S")
            print line
        else:
            print line
