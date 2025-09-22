
-- Functions, constraints, triggers, ...

--DROP THE CONSTRAINTS-----------------------------------------------------

-- Remove check constraint for wielrenner.landcode
ALTER TABLE wielrenner
DROP CONSTRAINT IF EXISTS wielrenner_landcode_check;

-- Remove check constraint for wielerteam.afkorting
ALTER TABLE wielerteam
DROP CONSTRAINT IF EXISTS wielerteam_afkorting_check;

-- Remove check constraint for wielerteam.landcode
ALTER TABLE wielerteam
DROP CONSTRAINT IF EXISTS wielerteam_landcode_check;

-- Remove check constraint for wedstrijd.landcode
ALTER TABLE wedstrijd
DROP CONSTRAINT IF EXISTS wedstrijd_landcode_check;

-- Remove check constraint for wielrenner.gewicht
ALTER TABLE wielrenner
DROP CONSTRAINT IF EXISTS wielrenner_gewicht_check;

-- Remove check constraint for wielrenner.lengte
ALTER TABLE wielrenner
DROP CONSTRAINT IF EXISTS wielrenner_lengte_check;

-- Remove check constraint for wielerteam.status
ALTER TABLE wielerteam
DROP CONSTRAINT IF EXISTS wielerteam_status_check;

-- Remove check constraint for rit.type
ALTER TABLE rit
DROP CONSTRAINT IF EXISTS rit_type_check;

-- Remove check constraint for rittenkoers_rit.nr
ALTER TABLE rittenkoers_rit
DROP CONSTRAINT IF EXISTS rittenkoers_rit_nr_check;

-- Remove check constraint for route.afstand
ALTER TABLE route
DROP CONSTRAINT IF EXISTS route_afstand_check;

-- Remove check constraint for route.hoogtemeters
ALTER TABLE route
DROP CONSTRAINT IF EXISTS route_hoogtemeters_check;

-- Remove check constraint for route.moeilijkheid
ALTER TABLE route
DROP CONSTRAINT IF EXISTS route_moeilijkheid_check;

-- Remove check constraint for uitslag.positie
ALTER TABLE uitslag
DROP CONSTRAINT IF EXISTS uitslag_positie_check;

-- Remove check constraint for uitslag.tijd
ALTER TABLE uitslag
DROP CONSTRAINT IF EXISTS uitslag_tijd_check;

-- Remove check constraint for uitslag.bonustijd
ALTER TABLE uitslag
DROP CONSTRAINT IF EXISTS uitslag_bonustijd_check;

-- Remove check constraint for uitslag.status
ALTER TABLE uitslag
DROP CONSTRAINT IF EXISTS uitslag_status_check;

-- Remove check constraint for uitslag.status 'Did Finish'
ALTER TABLE uitslag
DROP CONSTRAINT IF EXISTS uitslag_did_finish_check;

-- Remove check constraint for uitslag.status not 'Did Finish' with NULL positie
ALTER TABLE uitslag
DROP CONSTRAINT IF EXISTS uitslag_not_did_finish_check;

-- Remove check constraint for uitslag.status 'Did Not Finish' or 'Did Not Start' with NULL tijd
ALTER TABLE uitslag
DROP CONSTRAINT IF EXISTS uitslag_dnf_dns_check

-- Remove check constraint for uitslag.status 'Did Not Start' with NULL bonustijd
ALTER TABLE uitslag
DROP CONSTRAINT IF EXISTS uitslag_dns_check




--ADD THE CONSTRAINTS----------------------------------------------------------


-- Add check constraint for wielrenner.landcode
ALTER TABLE wielrenner
ADD CONSTRAINT wielrenner_landcode_check
CHECK (LENGTH(landcode) = 2);

-- Add check constraint for wielerteam.afkorting
ALTER TABLE wielerteam
ADD CONSTRAINT wielerteam_afkorting_check
CHECK (LENGTH(afkorting) = 3);

-- Add check constraint for wielerteam.landcode
ALTER TABLE wielerteam
ADD CONSTRAINT wielerteam_landcode_check
CHECK (LENGTH(landcode) = 2);

-- Add check constraint for wedstrijd.landcode
ALTER TABLE wedstrijd
ADD CONSTRAINT wedstrijd_landcode_check
CHECK (LENGTH(landcode) = 2);

-- Optional is already done

-- Add wielrenner.gewicht > 0
ALTER TABLE wielrenner
ADD CONSTRAINT wielrenner_gewicht_check
CHECK (gewicht > 0);

-- Add wielrenner.lengte > 0
ALTER TABLE wielrenner
ADD CONSTRAINT wielrenner_lengte_check
CHECK (lengte > 0);

-- Add wielerteam.status {‘World Tour’, ‘Pro Tour’}
ALTER TABLE wielerteam
ADD CONSTRAINT wielerteam_status_check
CHECK (status IN ('World Tour', 'Pro Tour'));

-- Add rit.type {‘Road Race’, ‘Individual Time Trial’, ‘Team Time Trial’}
ALTER TABLE rit
ADD CONSTRAINT rit_type_check
CHECK (type IN ('Road Race', 'Individual Time Trial', 'Team Time Trial'));

-- Add rittenkoers_rit.nr > 0
ALTER TABLE rittenkoers_rit
ADD CONSTRAINT rittenkoers_rit_nr_check
CHECK (nr > 0);

-- Add route.afstand > 0
ALTER TABLE route
ADD CONSTRAINT route_afstand_check
CHECK (afstand > 0);

-- Add route.hoogtemeters > 0
ALTER TABLE route
ADD CONSTRAINT route_hoogtemeters_check
CHECK(hoogtemeters > 0);

-- Add check constraint for route.moeilijkheid
ALTER TABLE route
ADD CONSTRAINT route_moeilijkheid_check
CHECK (moeilijkheid >= 1 AND moeilijkheid <= 5);

-- Add uitslag.positie > 0
ALTER TABLE uitslag
ADD CONSTRAINT uitslag_positie_check
CHECK(positie > 0);

-- Add check constraint for uitslag.tijd as a positive interval
ALTER TABLE uitslag
ADD CONSTRAINT uitslag_tijd_check
CHECK (tijd > interval '0' second);

-- Add uitslag.bonustijd > 0
ALTER TABLE uitslag
ADD CONSTRAINT uitslag_bonustijd_check
CHECK (bonustijd > interval '0' second);

-- Add uitslag.status {‘Did Finish’, ‘Did Not Finish’, ‘Did Not Start’, ‘Over Time Limit’, ‘Disqualified’}
ALTER TABLE uitslag
ADD CONSTRAINT uitslag_status_check
CHECK (status IN ('Did Finish', 'Did Not Finish', 'Did Not Start', 'Over Time Limit', 'Disqualified'));

-- Add check constraint for uitslag.status 'Did Finish'
ALTER TABLE uitslag
ADD CONSTRAINT uitslag_did_finish_check
CHECK (
    (status = 'Did Finish' AND positie IS NOT NULL AND tijd IS NOT NULL AND bonustijd IS NOT NULL) OR
    (status <> 'Did Finish')
);

-- Add check constraint for uitslag.status not 'Did Finish' with NULL positie
ALTER TABLE uitslag
ADD CONSTRAINT uitslag_not_did_finish_check
CHECK (
    (status <> 'Did Finish' AND positie IS NULL) OR
    (status = 'Did Finish')
);

-- Add check constraint for uitslag.status 'Did Not Finish' or 'Did Not Start' with NULL tijd
ALTER TABLE uitslag
ADD CONSTRAINT uitslag_dnf_dns_check
CHECK (
    ((status = 'Did Not Finish' OR status = 'Did Not Start') AND tijd IS NULL) OR
    (status NOT IN ('Did Not Finish', 'Did Not Start'))
);

-- Add check constraint for uitslag.status 'Did Not Start' with NULL bonustijd
ALTER TABLE uitslag
ADD CONSTRAINT uitslag_dns_check
CHECK (
    (status = 'Did Not Start' AND bonustijd IS NULL) OR
    (status <> 'Did Not Start')
);