import subprocess

proc = subprocess.Popen(["python", "test.py"], stdout=subprocess.PIPE)

while True:
    print "lol"
    line = proc.stdout.readline()
    print "lol2"
    print line
    if line != '':
        print "Greeting: ", line
    else:
        break
    time.sleep(0.5)
