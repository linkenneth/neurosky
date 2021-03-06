import time
import subprocess
import serial
from processBlink import processBlink

def init_serial():
    s = serial.Serial()
    s.port = "/dev/tty.usbserial-AH01KXWW"
    s.baudrate = 115200
    s.open()
    return s

def broadcast(data):
    """ Helper functions to broadcast to serial port""" 
    s.write(bytes(data, encoding = "UTF8"))

proc = subprocess.Popen("./analyze.out", shell = True, stdout = subprocess.PIPE)

s = init_serial()
poor = False
SAMPLE_LEN = 60
direction = 'L'
sProcessor = processBlink(SAMPLE_LEN, 700) #signal processor

while True:
    line = str(proc.stdout.readline(), encoding="UTF8")
    if line != '':
        signal = line.rstrip('\n').split(": ")
        if signal[0] == "POOR_SIGNAL":
            print(signal[1])
            if int(signal[1]) != 0:
                poor = True
            else:
                poor = False
            print(line)
        if poor:
            broadcast('S')
            continue
        if signal[0] == "RAW":
            rsp = sProcessor(int(signal[1]))
            if (rsp == 1):
                direction = 'R' if direction == 'L' else 'L' #toggle direction
                broadcast(direction) #Send command through serial
        elif signal[0] == "MEDITATION":
            if int(signal[1]) > 75:
                # move forward
                broadcast("F") #Forward
            else:
                broadcast("S") #Stop
            print(line)
