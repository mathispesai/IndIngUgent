CREATE FUNCTION controleer_wedstrijd_datum() RETURNS trigger AS
$$
BEGIN
    IF (EXISTS(SELECT 1 FROM wedstrijd WHERE datum = NEW.datum AND thuisclub = NEW.uitclub)) THEN
        RAISE EXCEPTION 'De uitclub % heeft al een wedstrijd op % en kan dus geen andere wedstrijd spelen op die dag.', NEW.uitclub, NEW.datum;
    ELSEIF (EXISTS(SELECT 1 FROM wedstrijd WHERE datum = NEW.datum and uitclub = NEW.thuisclub)) THEN
        RAISE EXCEPTION 'De thuisclub % heeft al een wedstrijg op % en kan dus geen andere wedstrijd spelen op die dag.', NEW.thuisclub, NEW.datum;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_wedstrijd_datum
    BEFORE INSERT
    ON wedstrijd
    FOR EACH ROW
EXECUTE procedure controleer_wedstrijd_datum();