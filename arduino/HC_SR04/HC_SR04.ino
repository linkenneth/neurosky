int rear = 13;
int front = 12;
int rearBrake = 8;
int frontBrake = 9;
int trigPin = 6;
int echoPin = 5;

void setup() {
  Serial.begin(9600);
  /* Begin HC-SR04 setup */
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  
  /* Begin Motor Shield setup */
  pinMode(rear, OUTPUT);
  pinMode(front, OUTPUT);
  pinMode(frontBrake, OUTPUT);
  pinMode(rearBrake, OUTPUT);
  digitalWrite(frontBrake, HIGH);
  digitalWrite(rearBrake, LOW);
  analogWrite(11, 450);
  
  /* Initialize rear drive */
  digitalWrite(rear, HIGH);
}

void loop() {
  int distance = readDistance();
  Serial.println(distance);
  /*if (distance < 30 && distance > 0) {
    digitalWrite(rearBrake, HIGH);
    delay(500);
    digitalWrite(rearBrake, LOW);
    digitalWrite(frontBrake, LOW);
    digitalWrite(front, HIGH);
    digitalWrite(rear, LOW);
    delay(1500);
    digitalWrite(rearBrake, HIGH);
    digitalWrite(front, LOW);
    digitalWrite(frontBrake, HIGH);
    delay(400);
    digitalWrite(rearBrake, LOW);
    digitalWrite(rear, HIGH);
  } */
  delay(1200);
}

unsigned int readDistance() {
  unsigned int duration, distance;
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/29.1) / 2;
  return distance;
}
