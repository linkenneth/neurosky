# Real time fourier shit
import numpy as np
import matplotlib.pyplot as plt
import time

BUFFER_SIZE = 2**14
buf = np.zeros([None] * BUFFER_SIZE, dtype=np.int)
i = 0

proc = subprocess.Popen("./analyze.out", shell = True, stdout = subprocess.PIPE)
fig = plt.figure(1)
ax = fig.add_subplot(111)
ax.set_xlim(-np.pi, np.pi)
ax.set_ylim(-1000, 1000)
line, = ax.plot(x, y, 'ko-')

while True:
    line = str(proc.stdout.readline(), encoding="utf8")
    if line != '':
        signal = line.rstrip('\n').split(": ")
        if signal[0] == 'RAW':
            buf[i] = int(signal[1])
            i += 1
            if i % BUFFER_SIZE == 0:
                sp = np.fft.fft(buf, n=500)
                freq = np.fft.fftfreq(buf.shape[-1])
                line.set_data(freq, sp.real)
                plt.draw()
                time.sleep(0.05)
