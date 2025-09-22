#include <Wire.h>
#include <ds3231.h>
 
struct ts t; 
int previousMillis;
int minTimer;
 
void setup() {
  Serial.begin(9600);
  Wire.begin();
  DS3231_init(DS3231_CONTROL_INTCN);
  /*----------------------------------------------------------------------------
  In order to synchronise your clock module, insert timetable values below !
  ----------------------------------------------------------------------------*/
  *
  // (set de timer) uncomment dit deel bij eerste upload en verander de waarden indien je de RTC een andere tijd wilt geven
  // comment dit deel weer als je de RTC reset hebt. (anders wordt bij iedere upload van dit programma de RTC weer gerest)
  t.hour=10; 
  t.min=44;
  t.sec=30;
  t.mday=10;
  t.mon=11;
  t.year=2121;
 
  DS3231_set(t); 
  */
}
 
void loop() {
  DS3231_get(&t);
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
  Serial.println(t.sec);
  sdcardTimer ();
  delay(1000);
}

void sdcardTimer ()
{
if(t.sec == 0){
  minTimer = t.min%15;
  if(millis()- previousMillis >= 1001){
    if(minTimer == 0){
      // WriteSDcard (); // <- voeg hier functie voor het schrijven op sdkaart
      Serial.println("-----------timer-------------");
      previousMillis = millis();
      
      }
    }  
  }  
}
