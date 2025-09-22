
#include <SPI.h> //for the SD card module
#include <SD.h> // for the SD card
#include <ds3231.h>
#include <Wire.h>


//
struct ts t; 
int previousMillis;
int minTimer;

// Pin 53 Arduin Mega
const int chipSelect = 53; 

// Create a file to store the data
File myFile;

void setup() {

  //initializing Serial monitor
  Serial.begin(9600);

  //RTC
  Wire.begin();
  DS3231_init(DS3231_CONTROL_INTCN);

    
  // setup for the SD card
  Serial.print("Initializing SD card...");

  if(!SD.begin(chipSelect)) {
    Serial.println("initialization failed!");
    return;
  }
  Serial.println("initialization done.");
    
  //open file
  myFile=SD.open("DATA.txt", FILE_WRITE);

  // if the file opened ok, write to it:
  if (myFile) {
    Serial.println("File opened ok");
    // print the headings for our data
    myFile.println("Date,Time,Temperature ºC");
  }
  myFile.close();
}

void loggingTime() {
  Serial.begin(9600);
  DS3231_get(&t);
  
  myFile = SD.open("DATA.txt", FILE_WRITE);
  if (myFile) {
    myFile.print(t.mday(), DEC);
    myFile.print('/');
    myFile.print(t.month(), DEC);
    myFile.print('/');
    myFile.print(t.day(), DEC);
    myFile.print(',');
    //myFile.print(t.hour(), DEC);
    myFile.print(':');
    myFile.print(t.minute(), DEC);
    myFile.print(':');
    myFile.print(t.second(), DEC);
    myFile.print(",");
  }
  //Serial.print(t.year(), DEC);
  Serial.print('/');
  Serial.print(t.month(), DEC);
  Serial.print('/');
  Serial.println(t.day(), DEC);
  //Serial.print(t.hour(), DEC);
  Serial.print(':');
  Serial.print(t.minute(), DEC);
  Serial.print(':');
  Serial.println(t.second(), DEC);
  myFile.close();
  delay(1000);  
}
   
  myFile = SD.open("DATA.txt", FILE_WRITE);
  if (myFile) {
    Serial.println("open with success");
    myFile.print(t);
    myFile.println(",");
  }
  myFile.close();
}

void loop() {
  loggingTime();
  delay(5000);
}
