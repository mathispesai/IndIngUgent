import geohash_tools as geohash

import csv
import json

from kafka import KafkaProducer # kafka-python package

producer = KafkaProducer(bootstrap_servers='localhost:9094', value_serializer=lambda v: json.dumps(v).encode('utf-8'))

with open('data.csv', mode ='r') as file:    
       csvFile = csv.DictReader(file)

       for line in csvFile:
            print(line)
            lineMod = {
                'eventId': int(line['event-id']),
                'visible': line['visible'].lower() == 'true',
                'ts': line['timestamp'],
                'sensorType': line['sensor-type'],
                'individualLocalIdentifier': line['individual-local-identifier'],
                'location': geohash.encode(float(line['location-lat']), float(line['location-long']), precision=7)
            }
            print(json.dumps(lineMod))
            print("Sending line..")
            producer.send(topic="timeseries-topic", value=lineMod)
            producer.flush()
           