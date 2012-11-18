int fwd = 10;
int bcwd = 11;
int left = 12;
int right = 13;
char value;

void setup() {
  pinMode(fwd, OUTPUT);
  pinMode(bcwd, OUTPUT);
  pinMode(left, OUTPUT);
  pinMode(right, OUTPUT);
  
  digitalWrite(fwd, HIGH);
  digitalWrite(bcwd, HIGH);
  digitalWrite(left, HIGH);
  digitalWrite(right, HIGH);
  Serial.begin(9600);

  
}

void loop() {
  if (Serial.available() > 0) {
    value = Serial.read();
    Serial.print(value);
    if (value == 'F') {
      digitalWrite(fwd, LOW);
      digitalWrite(bcwd, HIGH);
      delay(500);
      digitalWrite(fwd, HIGH);
    }
    if (value == 'B') {
      digitalWrite(bcwd, LOW);
      digitalWrite(fwd, HIGH);
      delay(500);
      digitalWrite(bcwd, HIGH);
    } 
    if (value == 'R') {
      digitalWrite(right, LOW);
      delay(500);
      digitalWrite(right, HIGH);
    } 
    if (value == 'L') {
      digitalWrite(left, LOW);
      delay(500);
      digitalWrite(left, HIGH);
    }
  }
}
    
    
