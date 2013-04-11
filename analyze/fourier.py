# Real time fourier shit
import numpy as np
import matplotlib.pyplot as plt
import time
import subprocess

BUFFER_SIZE = 256
SAMPLING_RATE = 512.0 # Hz
DELTA_T = 1 / SAMPLING_RATE
DELTA_F = SAMPLING_RATE / BUFFER_SIZE
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

totstart = time.clock()
while True:
    line = unicode(proc.stdout.readline(), encoding="utf8")
    if line != '':
        signal = line.rstrip('\n').split(": ")
        if signal[0] == 'POOR_SIGNAL':
          print line
        if signal[0] == 'RAW':
            buf[i] = int(signal[1])
            i += 1
            if i % BUFFER_SIZE == 0:
                start = time.clock()
                sp = np.fft.fft(buf, n=250)
                print "fft: %f" % (time.clock() - start)
                freq = np.fft.fftfreq(sp.size) * SAMPLING_RATE
                start = time.clock()
                rline.set_data(freq, sp.real)
                print "rline: %f" % (time.clock() - start)
                start = time.clock()
                plt.draw()
                print "plot: %f" % (time.clock() - start)
                i = 0
                print "tot: %f" % (time.clock() - totstart)
                totstart = time.clock()
