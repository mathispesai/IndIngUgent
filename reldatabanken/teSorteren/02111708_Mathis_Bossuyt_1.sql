SELECT
  id,
  familienaam,
  CASE
    WHEN marktwaarde IS NOT NULL THEN CONCAT(ROUND(marktwaarde / 1000000), ' miljoen')
    ELSE NULL
  END AS marktwaarde_miljoen,
  EXTRACT(year from AGE(CURRENT_DATE, geboortedatum)) AS leeftijd
FROM
  speler
WHERE
  geboortedatum IS NOT NULL
ORDER BY
  CASE
    WHEN marktwaarde IS NULL THEN 1
    ELSE 0
  END,
  marktwaarde DESC,
  leeftijd ASC,
  id ASC
  ;
  