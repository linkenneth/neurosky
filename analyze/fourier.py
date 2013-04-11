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
ax.set_xlim(-0.05, 0.05)
ax.set_ylim(0, 50000)
rline, = ax.plot(0, 0)
plt.ion()
plt.show()

# Source code from numpy
def rfftfreq(n, d=1.0):
    """
    Return the Discrete Fourier Transform sample frequencies 
    (for usage with rfft, irfft).

    The returned float array `f` contains the frequency bin centers in cycles 
    per unit of the sample spacing (with zero at the start).  For instance, if 
    the sample spacing is in seconds, then the frequency unit is cycles/second.

    Given a window length `n` and a sample spacing `d`::

      f = [0, 1, ...,     n/2-1,     n/2] / (d*n)   if n is even
      f = [0, 1, ..., (n-1)/2-1, (n-1)/2] / (d*n)   if n is odd

    Unlike `fftfreq` (but like `scipy.fftpack.rfftfreq`)
    the Nyquist frequency component is considered to be positive.

    Parameters
    ----------
    n : int
        Window length.
    d : scalar, optional
        Sample spacing (inverse of the sampling rate). Defaults to 1.

    Returns
    -------
    f : ndarray
        Array of length ``n//2 + 1`` containing the sample frequencies.

    Examples
    --------
    >>> signal = np.array([-2, 8, 6, 4, 1, 0, 3, 5, -3, 4], dtype=float)
    >>> fourier = np.fft.rfft(signal)
    >>> n = signal.size
    >>> sample_rate = 100
    >>> freq = np.fft.fftfreq(n, d=1./sample_rate)
    >>> freq
    array([  0.,  10.,  20.,  30.,  40., -50., -40., -30., -20., -10.])
    >>> freq = np.fft.rfftfreq(n, d=1./sample_rate)
    >>> freq
    array([  0.,  10.,  20.,  30.,  40.,  50.])

    """
    if not (isinstance(n,types.IntType) or isinstance(n, integer)):
        raise ValueError("n should be an integer")
    val = 1.0/(n*d)
    N = n//2 + 1
    results = arange(0, N, dtype=int)
    return results * val

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
                sp = np.fft.rfft(buf, n=250)
                freq = np.fft.rfftfreq(sp.size) * SAMPLING_RATE
                rline.set_data(freq, sp.real)
                plt.draw()
                i = 0
                time.sleep(0.05)
