//Constants
int pinF = 13;
int pinL = 11;
int pinR = 10;


void setup() {
    Serial.begin(115200);
    pinMode(pinF, OUTPUT);
    pinMode(pinL, OUTPUT);
    pinMode(pinR, OUTPUT);
}

void loop() {
    if (Serial.available()) {
        char rsp = Serial.read();
        if (rsp == 'F') {
            digitalWrite(pinF, HIGH);
        } else if (rsp == 'S') {
            digitalWrite(pinF, LOW);
        } else if (rsp == 'L') {
            digitalWrite(pinL, HIGH);
            digitalWrite(pinR, LOW);
        } else if (rsp == 'R') {
            digitalWrite(pinL, LOW);
            digitalWrite(pinR, HIGH);
        }
    }
}
