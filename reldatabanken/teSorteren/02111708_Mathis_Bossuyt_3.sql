SELECT
	naam,
  COUNT(DISTINCT UNNEST(STRING_TO_ARRAY(LOWER(naam), '')) AS klinker) AS aantal
FROM stadion
WHERE
  klinker IN ('a', 'e', 'i', 'o', 'u');