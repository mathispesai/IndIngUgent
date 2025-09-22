#include <Wire.h>
#include <ds3231.h>
#include <SPI.h> //for the SD card module
#include <SD.h> // for the SD card
#include <dht.h> // library for sensor

dht DHT;

#define DHT11_PIN 7

// Pin 53 Arduino Mega, SD-Card
const int chipSelect = 53;

File DataVertFarming;

struct ts t;
int minTimer;

int lamp1 = 10;
int lamp2 = 11;
int lamp3 = 12;
int pomp =  13;
/*
  lamp 1 = kiemlamp = warmtelamp
  lamp 2 = bloeilamp
  lamp 3 = groeilamp
*/

long previousMillis = 0;


long pompMillis = 0;

int lampKlok = 0;
int lamp2Start = LOW;

long pompTimer = 0;
int pompMode = LOW;
int pompKlok = 0;

void setup() {

  Serial.begin(9600);
  Wire.begin();
  DS3231_init(DS3231_CONTROL_INTCN);

  pinMode(lamp1, OUTPUT);
  pinMode(lamp2, OUTPUT);
  pinMode(lamp3, OUTPUT);
  pinMode(pomp, OUTPUT);

  /*
    // (set de timer) uncomment dit deel bij eerste upload en verander de waarden indien je de RTC een andere tijd wilt geven
    // comment dit deel weer als je de RTC reset hebt. (anders wordt bij iedere upload van dit programma de RTC weer op onderstaande datum gezet)
    t.hour = 11;
    t.min = 28;
    t.sec = 25;
    t.mday = 13;
    t.mon = 12;
    t.year = 2121; //(voor een of andere reden wordt er 100 jaar afgetrokken bij de rtc, daarom wordt er bij de initialisatie 100 jaar opgeteld : 2121 -> 2021)


    DS3231_set(t);
  */
  while (!Serial);

  Serial.print("Initializing SD card...");

  if (!SD.begin(53)) {
    Serial.println("initialization failed!");
    while (1);
  }

  Serial.println("initialization done.");
  if (SD.exists("dataVert.txt")) {

    Serial.println("dataVert.txt exists.");

  } else {

    Serial.println("datavert.txt doesn't exist.");

  }

  // open a new file and immediately close it:

  Serial.println("Creating dataVert.txt...");

  DataVertFarming = SD.open("dataVert.txt", FILE_WRITE);

  DataVertFarming.close();

  // Check to see if the file exists:

  if (SD.exists("dataVert.txt")) {

    Serial.println("dataVert.txt exists.");

  } else {

    Serial.println("dataVert.txt doesn't exist.");

  }
}


void lampSturing() {

  /*
    hier worden de lampen gestuurd op bepaalde uren.
    lamp2 wordt pas gestuurd vanaf 15 december.
    lamp 1 wordt : aangezet om 21 uur en uitgezet om 5 uur.
    lamp 2 wordt : aangezet om 8 uur en uit om 19 uur.
    lamp 3 wordt : aangezet om 5 of 14 uur en wordt uitgezet om 10 of 21 uur
  */
  //lampsturing 1 : de lamp wort aangestuurd tussen 21 en 5 uur, buiten deze periode wordt hui uitgestuurd.
  if (lampKlok >= 21 || lampKlok < 5)
  {
    digitalWrite(lamp1, HIGH);
  }
  else
  {
    digitalWrite(lamp1, LOW);
  }

  //lampsturing 2 : de lamp wordt aangestuurd tussen 8 en 19 uur, buiten deze periode wordt hij uitgestuurd.
  if (lamp2Start == HIGH)
  {
    if (lampKlok >= 8 && lampKlok < 19)
    {
      digitalWrite(lamp2, HIGH);
    }
    else
    {
      digitalWrite(lamp2, LOW);
    }
  }

  // lampsturing 3 : de lamp wordt aangestuurd tussen 5 en 10 of tussen 14 en 21 uur, buiten deze 2 periodes wordt hij uitgestuurd.
  if (lampKlok >= 5 && lampKlok < 10)
  {
    digitalWrite(lamp3, HIGH);
  }
  else
  { if (lampKlok >= 14 && lampKlok < 21)
    {
      digitalWrite(lamp3, HIGH);
    }
    else
    {
      digitalWrite(lamp3, LOW);
    }
  }
}

/*
  zolang mompMode aan staat zal deze functie werken
  de pomp wordt aangezet iedere keer dat de functie wordt opgeroepen
  dit tot de timer van 20 seconden voor bij is, dan wordt de pomp uit gezet wen wordt pompMode ook uitgezet tot het weer 00 of 12 uur is.
*/
void pompSturing() {

  if (pompMillis - pompTimer >= 20000)
  {
    digitalWrite(pomp, LOW);
    pompMode = LOW;
  }
  else
  {
    digitalWrite(pomp, HIGH);
  }

}

void sdcardTimer ()
{


  /*
    de timer voor de sdcardwriter.
    iedere keer dat de tijd 00,15,30 of 45 minuten is en de seconden gelijk is aan nul
    zal de functie loggingdata beginnen, wordt dit mededeeld op serieel monitor en
    wordt de previousMillis timer gereset zodat de functie niet meerdere keren opgeroepen wordt gedurende de hele seconde.
  */



  if (t.sec == 0) {
    minTimer = t.min % 15;
    if (millis() - previousMillis >= 1001) {
      if (minTimer == 0) {
        loggingdata();
        Serial.println("-----------timer-------------");
        previousMillis = millis();
        int chk = DHT.read11(DHT11_PIN);

      }
    }
  }
}

void loggingdata() {

  DataVertFarming = SD.open("dataVert.txt", FILE_WRITE); // de tekstfile wordt geopend.
  //if the file is available, write to it
  if (DataVertFarming) {
    //start a new line
    DataVertFarming.print('\n');
    // de tijd wordt geprint in het tekst bestand.
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
    DataVertFarming.print("Temperature = ");
    DataVertFarming.print(DHT.temperature);
    DataVertFarming.print(",Humidity = ");
    DataVertFarming.print(DHT.humidity);

    // printing data van sensoren. (momenteel nog geen sensoren)
    /*
      hier voorbeeld van lezen digitale pin 2
      DataVertFarming.print(digitalRead(2)); // voorbeeld voor data sensoren op te slaan
      DataVertFarming.print(","); //volgende kolom
    */
    // close file
    DataVertFarming.close();
  }
  else {
    Serial.println("opening file failed");
  }
}

void loop() {

  DS3231_get(&t); // leest de waarde af van de RTC af

  /*schrijft bovenstaande waarde op in seriele monitor zodat we de tijd kunnen zien.*/
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

  sdcardTimer ();  // begint de functie voor de sdcard Timer

  /*
    hieronder word er gekeken of pompmode aan of uit staat.
    als hij uit staat kijk het programma naar de tijd, is het 0 of 12 uur precies, dan zal pompmode aan gezet worden.
    als hij aan staat zal hij bij iedere loop de functie pompsturing () laten lopen tot pompmode terug uit is.
    pompMillis wordt elke loop gelijkgesteld tot millis().
    pomptimer wordt gelijkgesteld aan millis () waneer pompmode aan gezet wordt. (1 keer om de 12 uur)
  */
  pompMillis = millis();

  if (pompMode == LOW)
  {
    pompKlok = t.hour % 12;
    if (pompKlok == 0)
    {
      if (t.min == 0)
      {
        if (t.sec == 0)
        {
          pompTimer = millis();
          pompMode = HIGH;
        }
      }
    }
  }

  if (pompMode == HIGH)
  {
    pompSturing();
  }

  /*
    stelt de lampklok gelijk aan het uur gegeven door de RTC.
    kijkt of de datum 15 december is zodat lamp2Start aan wordt gezet.
    dit betekend dat lamp2 vanaf 15 dec maar mag beginnen met aan en uit gaan.
    dan wordt de functie lampsturing opgeroepen.
  */

  lampKlok = t.hour;
  if (t.mon >= 12) // deze functie is voor 2021.
  { if (t.mday >= 15) 
    {
      lamp2Start = HIGH;
    }
  }
  if (t.year >= 2122) // vanaf 2022 wordt de lamp2start direct aangestuurd aangezien het voorbij 15/12/2021 is. 
  {                   
    lamp2Start = HIGH;
  }
  
  lampSturing();

}
