CREATE FUNCTION check_doelpunten()
    RETURNS trigger AS
$BODY$
DECLARE
    score_thuis INTEGER;
    score_uit INTEGER;
    huidig_aantal_doelpunten INTEGER;
BEGIN

    -- vraag het aantal thuis- en uitdoelpunten op dat gelieerd is aan de wedstrijd
    -- waarvoor er een extra doelpunt zal worden toegevoegd
    SELECT thuisdoelpunten, uitdoelpunten INTO score_thuis, score_uit FROM wedstrijd WHERE thuisclub = NEW.thuisclub AND datum = NEW.datum;

    -- vraag het aantal doelpunten op die momenteel reeds zijn toegevoegd voor de wedstrijd
    -- waarvoor er een extra doelpunt zal worden toegeovegd
    SELECT COUNT(*) INTO huidig_aantal_doelpunten FROM doelpunt WHERE thuisclub = NEW.thuisclub AND datum = NEW.datum;

    -- controleer of er nog een extra doelpunt toegevoegd kan worden
    -- zoniet, gooi een foutmelding op en breek de toevoegoperatie af
    IF (huidig_aantal_doelpunten >= score_thuis + score_uit) THEN
        RAISE EXCEPTION 'Er kunnen geen extra doelpunten meer worden toegevoegd aan de wedstrijd met thuisclub % op datum %', NEW.thuisclub, NEW.datum;
    END IF;

    -- als er geen foutmelding opgegooid werd, geef dan de nieuwe rij terug (m.a.w. voeg de nieuwe rij toe)
    RETURN NEW;

END;
$BODY$
    LANGUAGE plpgsql;

CREATE TRIGGER check_doelpunten_trigger
    BEFORE INSERT ON doelpunt
    FOR EACH ROW EXECUTE PROCEDURE check_doelpunten();