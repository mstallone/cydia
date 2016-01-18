#include <Servo.h> 

Servo servo;
String message;
bool isModeOne = 1, dirIsPos = 1; 
int pos = 0;

void setup(){
  Serial.begin(9600);
  pinMode(13, OUTPUT);
  servo.attach(11);
  servo.write(110);
}

void loop(){
  while(Serial.available()){
    delay(10);
    char c = Serial.read();
    message += c;
  } 
  
  if(message.length() > 0){
    if(message == "LED"){
      if(digitalRead(13) == HIGH) digitalWrite(13, LOW);
      else digitalWrite(13, HIGH);
    }else if(message == "resetServo") {
      servo.write(110);
      isModeOne = 0;
    }else if(message == "startServo") {
      isModeOne = 1;
    }
    
    message = "";
  }

  if(isModeOne) {
    if(dirIsPos){
      pos++;
      if(pos > 180) dirIsPos = 0;
    }else {
      pos--;
      if(pos < 50) {
        dirIsPos = 1;
        isModeOne = 0;
        message = "resetServo";
      }
    }
    servo.write(pos);
    
  }
  Serial.println(String(analogRead(5)) + "#");
}
