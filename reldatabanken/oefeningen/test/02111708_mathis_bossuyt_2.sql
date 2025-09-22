SELECT DISTINCT cast(stadion.naam as varchar) AS stadionnaam FROM stadion 
JOIN club ON stadion.naam = club.stadionnaam 
JOIN wedstrijd ON club.naam = wedstrijd.thuisclub 
JOIN wedstrijdevent ON (wedstrijd.thuisclub=wedstrijdevent.thuisclub AND wedstrijd.datum = wedstrijdevent.datum)
JOIN doelpunt ON (wedstrijdevent.datum = doelpunt.datum AND wedstrijdevent.thuisclub = doelpunt.thuisclub AND wedstrijdevent.nr = doelpunt.nr)
GROUP BY stadion.naam HAVING MIN(wedstrijdevent.minuut) <= ALL(SELECT wedstrijdevent.minuut FROM wedstrijdevent JOIN doelpunt ON (wedstrijdevent.datum = doelpunt.datum AND wedstrijdevent.thuisclub = doelpunt.thuisclub AND wedstrijdevent.nr = doelpunt.nr));  
