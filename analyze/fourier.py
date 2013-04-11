# Real time fourier shit
import numpy as np
import matplotlib.pyplot as plt
import subprocess
from datetime import datetime, timedelta
import serial

BUFFER_SIZE = 64
SAMPLING_RATE = 512.0 # Hz
DELTA_T = 1 / SAMPLING_RATE
DELTA_F = SAMPLING_RATE / BUFFER_SIZE
TURN_THRESHOLD = 8000
GO_THRESHOLD = 60
DETECT_INTERVAL = timedelta(milliseconds=500)
buf = np.zeros(BUFFER_SIZE, dtype=np.int)
i = 0

proc = subprocess.Popen("./analyze.out", shell = True, stdout = subprocess.PIPE)
fig = plt.figure(1)
ax = fig.add_subplot(111)
ax.set_xlim(-0.1*SAMPLING_RATE, 0.1*SAMPLING_RATE)
ax.set_ylim(-10000, 10000)
rline, = ax.plot(0, 0)
plt.ion()
plt.show()

def init_serial():
    s = serial.Serial()
    s.port = "/dev/tty.usbserial-AH01KXWW"
    s.baudrate = 115200
    s.open()
    return s

def broadcast(data):
    """ Helper functions to broadcast to serial port""" 
    s.write(bytes(data))

s = init_serial()
direction = 'L'
poor = False

last_detected = datetime(1992, 12, 13)
while True:
    line = unicode(proc.stdout.readline(), encoding="utf8")
    if line != '':
        signal = line.rstrip('\n').split(": ")
        if signal[0] == 'POOR_SIGNAL':
            print line.rstrip('\n')
            poor = int(signal[1]) > 0
        if poor:
            print "Not detecting blinks due to poor signal..."
            continue
        if signal[0] == 'RAW':
            buf[i] = int(signal[1])
            i += 1
            if i % BUFFER_SIZE == 0:
                sp = np.fft.fft(buf, n=1000)
                freq = np.fft.fftfreq(sp.size) * SAMPLING_RATE
                rline.set_data(freq, sp.real)
                plt.draw()
                peak = max(abs(x) for x in sp[:80].real)
                print "middle peak: %s" % peak
                if peak > TURN_THRESHOLD:
                    if datetime.now() - last_detected > DETECT_INTERVAL:
                        direction = 'R' if direction == 'L' else 'L'
                        broadcast(direction)
                        last_detected = datetime.now()
                i = 0
        elif signal[0] == "MEDITATION":
            if int(signal[1]) > GO_THRESHOLD:
                broadcast("F")  # Forward
            else:
                broadcast("S")  # Stop
            print line
