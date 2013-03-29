int bPin = 13;
int fPin = 10;
int lPin = 9;
int rPin = 8;

void setup() {
  pinMode(fPin, OUTPUT);
  pinMode(bPin, OUTPUT);
  pinMode(lPin, OUTPUT);
  pinMode(rPin, OUTPUT);
  digitalWrite(lPin, HIGH);
  digitalWrite(rPin, HIGH);
  digitalWrite(bPin, HIGH);
  digitalWrite(fPin, HIGH);
  Serial.begin(115200);
}

void loop() {
  if (Serial.available()) {
    char r = Serial.read();
    if (r == 'L') {
      digitalWrite(lPin, LOW);
      delay(500);
      digitalWrite(lPin, HIGH);
    } else if (r == 'R') {
      digitalWrite(rPin, LOW);
      delay(500);
      digitalWrite(rPin, HIGH);
    } else if (r == 'B') {
      digitalWrite(bPin, LOW);
      delay(500);
      digitalWrite(bPin, HIGH);
    } else if (r == 'F') {
      digitalWrite(fPin, LOW);
      delay(500);
      digitalWrite(fPin, HIGH);
    } 
  }
}
