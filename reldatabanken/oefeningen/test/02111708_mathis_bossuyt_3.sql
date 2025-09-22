SELECT cast(stadion.naam as varchar) AS stadionnaam, cast(to_char(datum , 'YYYY') as integer) as jaar,
CASE WHEN wedstrijd.toeschouwers IS NULL THEN cast(SUM(cast(floor(stadion.capaciteit*0.8) as integer)) as integer)
ELSE cast(SUM(wedstrijd.toeschouwers) as integer)
END AS toeschouwers
FROM stadion JOIN club ON stadion.naam = club.stadionnaam JOIN wedstrijd ON club.naam = wedstrijd.thuisclub
GROUP BY jaar, stadion.naam, wedstrijd.toeschouwers;
