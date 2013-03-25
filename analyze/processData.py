import serial
import subprocess

SERIAL_NUM = 0

def main(process_turn, process_forward):
    proc = subprocess.Popen("./analyze.out", shell=True, stdout=subprocess.PIPE)
    poor = True
    ser = serial.Serial(SERIAL_NUM)
    turn_state = 'R'
    forward_state = 'F'

    while True:
        line = proc.stdout.readline()
        if line != '':
            signal = line.split(": ")
            print signal[0], signal[1]
            if signal[0] == "POOR_SIGNAL":
                poor = int(signal[1]) != 0
            elif poor:
                continue
            else:
                signal = signal[1]
                turn = process_turn(signal)
                forward = process_forward(signal)
                if turn:
                    turn_state = 'R' if 'L' else 'R'
                    ser.write(turn_state)
                if forward:
                    forward_state = 'F' if 'B' else 'F'
                    ser.write(forward_state)

if __name__ == "__main__":
    pass
    # main(FUNCTIONS HERE)
