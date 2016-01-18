bool isRecording = false;
const int recordButtonPin = 2, stopButtonPin = 3;
const int aKey = 6, bKey = 7, cKey = 8;
String recordedNotes

void setup() {
  pinMode(recordButtonPin, INPUT);
  pinMode(stopButtonPin, INPUT);
  pinMode(aKey, INPUT);
  pinMode(bKey, INPUT);
  pinMode(cKey, INPUT);
}

void loop() {
  int A = digitalRead(aKey);
  int B = digitalRead(bKey);
  int C = digitalRead(cKey);
  
  if(digitalRead(recordButtonPin) == HIGH){
     if(isRecording) isRecording = false;
     else isRecording = true;
     delay(300);
  }

  if(isRecording) {
    //ABC = W, AB = X, BC = Y, AC = Z;
    if(A == HIGH && B == HIGH && C == HIGH) recordedNotes = recordedNotes + "W";
    else if(A == HIGH && B == HIGH) recordedNotes = recordedNotes + "X";
    else if(B == HIGH && C == HIGH) recordedNotes = recordedNotes + "Y";
    else if(A == HIGH && C == HIGH) recordedNotes = recordedNotes + "Z";

    else if(A == HIGH) recordedNotes = recordedNotes + "A";
    else if(B == HIGH) recordedNotes = recordedNotes + "B";
    else if(C == HIGH) recordedNotes = recordedNotes + "C";
    delay(300);
  }

  if(digitalRead(stopButtonPin) == HIGH) {
    recordedNotes = ""; 
    delay(300);
  }

  if(recordedNotes.length() > 0){
    char note = recordedNotes.charAt(0);
    //--//
    //PLAY "note" on speaker
    //--//
    delay(250);
  }
}
