CREATE FUNCTION check_toeschouwers()
    RETURNS trigger AS
$BODY$
DECLARE
    capaciteit_stadion INTEGER;
BEGIN

    SELECT s.capaciteit INTO capaciteit_stadion FROM club c INNER JOIN stadion s ON c.stadionnaam = s.naam WHERE c.naam = NEW.thuisclub;

    IF (NEW.toeschouwers > capaciteit_stadion) THEN
        RAISE EXCEPTION 'Het aantal toeschouwers % die de wedstrijd met thuisclub % op datum % bijwoont overschrijdt de maximale capaciteit % van het stadion.', NEW.toeschouwers, NEW.thuisclub, NEW.datum, capaciteit_stadion;
    END IF;

    RETURN NEW;

END;
$BODY$
    LANGUAGE plpgsql;

CREATE TRIGGER check_toeschouwers
    BEFORE INSERT ON wedstrijd
    FOR EACH ROW EXECUTE PROCEDURE check_toeschouwers();