#include <Wire.h>
#include <ds3231.h>
#include <SPI.h> //for the SD card module
#include <SD.h> // for the SD card

struct ts t; 
int previousMillis;
int minTimer;

// Pin 53 Arduino Mega, SD-Card
const int chipSelect = 53; 

// Create a file to store the data
File DataVertFarming;

void setup() {
  
  //initializing Serial Monitor
  Serial.begin(9600);
  
  //RTC
  Wire.begin();
  DS3231_init(DS3231_CONTROL_INTCN);

  // setup for the SD card
  Serial.print("Initializing SD card");

  if(!SD.begin(chipSelect)) {
    Serial.println("initialization failed!");
    return;
  }
  Serial.println("initialization done.");
    
  //open file
  DataVertFarming=SD.open("DataVertFarming.txt", FILE_WRITE);

  // if the file opened ok, write to it:
  if (DataVertFarming) {
    Serial.println("File opened ok");
    // print the headings for our data
    
  }
  DataVertFarming.close();
  }  
 
void loggingdata() {
  Serial.begin(9600);
  DS3231_get(&t);
  
  DataVertFarming = SD.open("DataVertFarming.txt", FILE_WRITE);
  if (DataVertFarming) {
    // Printing Time
    DataVertFarming.print(t.mday);
    DataVertFarming.print('/');
    DataVertFarming.print(t.mon);
    DataVertFarming.print('/');
    DataVertFarming.print(t.year);
    DataVertFarming.print(',');
    DataVertFarming.print(t.hour);
    DataVertFarming.print(':');
    DataVertFarming.print(t.min);
    DataVertFarming.print(':');
    DataVertFarming.print(t.sec);
    DataVertFarming.print(",");

    // printing data from sensors
    DataVertFarming.print(); // voorbeeld voor data sensoren op te slaan
    DataVertFarming.print(","); //volgende kolom

    // visualisering van Tijd en data sensoren in Serial Plotter
    Serial.print("Date : ");
    Serial.print(t.mday);
    Serial.print("/");
    Serial.print(t.mon);
    Serial.print("/");
    Serial.print(t.year);
    Serial.print("\t Hour : ");
    Serial.print(t.hour);
    Serial.print(":");
    Serial.print(t.min);
    Serial.print(".");
    Serial.print(t.sec);
    
    Serial.print(",");
    Serial.print(); //voor data sensoren
    Serial.print(",");
    delay(1000);
  
    }
  DataVertFarming.close();
}

void loop() {
  loggingdata();
  delay(60000);
}
