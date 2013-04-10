from telnetlib import Telnet

t = Telnet("127.0.0.1", 13854)

while True:
    data = t.read_until('\xaa\xaa')
    print "Data is %s" % str(data)
    if data:
        blink_index = data.find('\x02')
        print "POOR_SIGNAL: %d" % blink_index
        if blink_index != -1:
            print ord(data[blink_index + 1])
