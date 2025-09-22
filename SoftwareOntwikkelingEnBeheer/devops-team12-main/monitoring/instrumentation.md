# Grafana Dashboard

## Link

- [First Dashboard](http://devops-proxy.atlantis.ugent.be/grafana/devops-team12/d/ce5zw11lz9ywwe/first-dashboard?orgId=1&from=2024-12-14T08:32:32.116Z&to=2024-12-14T14:32:32.116Z&timezone=browser)

## Metrics

### 1. Unit behavior count/seconds

**Description**: Dispalys the requests per unit behavior per second, meaning how many units request a move from a certain behaviour every second.

**Y-axis**: `count/second`

**Labels**: Different faction behaviours 
- CAPTURE_BASE
- CONVERT_OPPONENTS
- EXPAND_TERRITORY
- FARM_GOLD
- NEUTRALIZE_TERRITORY
- PROTECT_BASE
- RIG_BASE



### 2. Unit requests per seconds

**Description**: Displays the requests per unit for each second.

**Y-axis**: `time`

**Labels**: Unit types
- CLERIC
- FIGHTER
- PIONEER
- SAPPER
- WORKER

### 3. Total execution time/seconds

**Description**: Displays the total execution time per second.

**Y-axis**: `time`

**Labels**: Execution time
- Value

### 4. Unit move execution time/seconds

**Description**: Displays the average execution time per unit

**Y-axis**: `time`

**Labels**: Unit types
- CLERIC
- FIGHTER
- PIONEER
- SAPPER
- WORKER

### 5. Execution time to count ratio

**Description**: Displays the execution time divided by count for each unit per second.

**Y-axis**: `time`

**Labels**: Unit types
- CLERIC
- FIGHTER
- PIONEER
- SAPPER
- WORKER

### 6. Memory usage (bytes)

**Description**: Displays total memory usage per deployment

**Y-axis**: `bytes`

**Labels**: Deployments

### 7. CPU usage

**Description**: Displays CPU usage per deployment

**Y-axis**: `time`

**Labels**: Deployments