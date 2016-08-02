#define CODIGO_SENSOR_0 0

void setup() {
  //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  // if analog input pin 0 is unconnected, random analog
  // noise will cause the call to randomSeed() to generate
  // different seed numbers each time the sketch runs.
  // randomSeed() will then shuffle the random function.
  randomSeed(analogRead(0));
}

float floatingPoint(int numDigits) {
  long limits = (long) pow(10, numDigits+1);
  return (float) random(-limits, limits) / (float) limits;
}

float randomFloat(long min, long max) {
  return random(min, max) + floatingPoint(2);
}

void loop() {
  float temperatura = randomFloat(-10,70);
  String str = String(CODIGO_SENSOR_0);
    for(int i = 0; i < str.length(); i++) {
    Serial.write(str[i]);
  }
  Serial.write('\n');
  str = String(temperatura);
  for(int i = 0; i < str.length(); i++) {
    Serial.write(str[i]);
  }
  Serial.write('\n');

  delay(1000);
}
