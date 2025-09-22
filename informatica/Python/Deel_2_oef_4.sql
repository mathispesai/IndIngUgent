-- https://dodona.ugent.be/nl/courses/1032/series/11421/activities/890684045
SELECT ZIP
FROM CUSTOMER
GROUP BY ZIP
HAVING COUNT(*) > 1

