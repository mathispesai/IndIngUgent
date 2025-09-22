SELECT id AS spelerid , 
CASE WHEN to_char(datum , 'DD/MM') LIKE to_char(speler.geboortedatum, 'DD/MM') THEN cast(count(nr) as integer)
ELSE 0
END AS verjaardag,  
CASE WHEN to_char(datum , 'DD/MM') NOT LIKE to_char(speler.geboortedatum, 'DD/MM') THEN cast(count(nr) as integer)
END AS overige
FROM speler JOIN doelpunt ON speler.id = doelpunt.spelerid WHERE geboortedatum IS NOT NULL GROUP BY doelpunt.datum, speler.id HAVING COUNT(nr)!=0;