SELECT
  thuisclub,
  uitclub,
  datum,
  thuisdoelpunten,
  uitdoelpunten,
  (thuisdoelpunten + uitdoelpunten) % 2 = 0 AS gelijkspel
FROM
  wedstrijd
WHERE
  (uitdoelpunten % 2 = 0 OR thuisdoelpunten % 2 = 0) 
  AND toeschouwers IS NOT NULL
ORDER BY
  datum;
  
  