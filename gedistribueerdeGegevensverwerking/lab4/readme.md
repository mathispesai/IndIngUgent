1. Importing the data CSV file into QuestDB using the web console and rename it to `data-temp.csv`.
2. Creating a new table `data` with the appropriate schema to store the data and adding deduplication based on timestamp and locations. 

```sql
CREATE TABLE data (
    eventId LONG,
    visible BOOLEAN,
    ts TIMESTAMP,
    sensorType SYMBOL,
    individualLocalIdentifier SYMBOL,
    location GEOHASH(40b)
) timestamp(ts)
PARTITION BY hour
DEDUP UPSERT KEYS(ts, location);
```
3. Converting the data from the CSV file into the `data` table. and inserting the data into the table. 

```sql
INSERT INTO data
SELECT 
    eventId,
    visible,
    CAST(timestamp AS TIMESTAMP) AS ts,  -- Convert the timestamp from VARCHAR to TIMESTAMP
    sensorType,
    individualLocalIdentifier,
    make_geohash(locationLong, locationLat,40) AS location  -- Combine latitude and longitude to GEOHASH
FROM 
    'data-temp.csv';
```
3. Creating a view with the latest hourly points for each individual local identifier. This view will be used to get the last known location of each pet on the hour.
```sql
CREATE MATERIALIZED VIEW 'latest_hourly_location'
with base 'data' refresh incremental
as (
    select
    ts as 'timestamp', 
    individualLocalIdentifier as 'identifier',
    last(location) as 'geohash'
    from data
    sample by 1h
    ) partition by day;
```
4. Kafka Sink
Make sure you have the files [questdb.json](questdb.json) and [producer.py](producer.py) now open a terminal and run the following command to start the Kafka Connect

```bash
Invoke-RestMethod -Uri "http://localhost:8083/connectors" `
  -Method Post `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body (Get-Content -Raw -Path "questdb.json")
  ```
  5. Kafka Producer
  now run the [producer.py](producer.py) file to start the producer and send the data to the Kafka topic. 
  
