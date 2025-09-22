-- https://dodona.ugent.be/nl/courses/1032/series/11417/activities/907504431
SELECT *
FROM PRODUCT
WHERE (DESCRIPTION LIKE '%Monitor%' or DESCRIPTION LIKE '%Speaker%') and QUANTITY_ON_HAND < 400
ORDER BY DESCRIPTION

