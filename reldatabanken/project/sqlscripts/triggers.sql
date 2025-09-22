-- NUMMER 1

CREATE OR REPLACE FUNCTION CHECK_SAME_DATE_RACE() RETURNS 
TRIGGER AS $$ 
	$$ BEGIN IF EXISTS (
	    SELECT *
	    FROM rit r
	        JOIN uitslag u ON u.rit_id = r.id
	    WHERE
	        NEW.rennernaam = u.rennernaam
	        AND NEW.rit_id <> u.rit_id
	        and r.datum = (
	            select datum
	            from rit
	            where
	                rit.id = new.rit_id
	        )
	) THEN RAISE EXCEPTION 'Rider participated in another race on the same date.';
	END IF;
	RETURN NEW;
	END;
	$$ LANGUAGE plpgsql;


CREATE TRIGGER TRIGGER_SAME_DATE_RACE 
	trigger_same_date_race BEFORE
	INSERT ON uitslag FOR EACH ROW
	EXECUTE
	    FUNCTION check_same_date_race();


-- NUMMER 2

CREATE OR REPLACE FUNCTION CHECK_RACE_RESULT() RETURNS 
TRIGGER AS $$ 
	$$ BEGIN -- Check if the status is 'Did Finish'
	IF NEW.status <> 'Did Finish' THEN -- Check if there is a race in the same rittenkoers with a higher volgnummer
	IF EXISTS (
	    SELECT 1
	    FROM uitslag u
	        JOIN rittenkoers_rit as r ON u.rit_id = r.id
	    WHERE r.naam = (
	            select naam
	            from rittenkoers_rit
	            where
	                rittenkoers_rit.id = new.rit_id
	        )
	        and u.rennernaam = new.rennernaam
	        and NEW.rit_id <> u.rit_id
	        and r.nr > (
	            select rr.nr
	            from rittenkoers_rit as rr
	            where
	                new.rit_id = rr.id
	        )
	) THEN RAISE EXCEPTION 'Invalid race result. Another race with higher volgnummer exists.';
	END IF;
	END IF;
	-- Return the NEW row for the trigger to complete successfully
	RETURN NEW;
	END;
	$$ LANGUAGE plpgsql;


CREATE TRIGGER UITSLAG_CHECK_TRIGGER 
	uitslag_check_trigger BEFORE
	INSERT ON uitslag FOR EACH ROW
	EXECUTE
	    FUNCTION check_race_result();


--- NUMMER 3

CREATE OR REPLACE FUNCTION CHECK_LOWER_SEQUENCE_RACES() 
RETURNS TRIGGER AS 
$$ 
BEGIN -- Check if all races in the same rittenkoers have a lower volgnummer
	IF EXISTS (
	    SELECT 1
	    FROM uitslag u
	        JOIN rittenkoers_rit as r ON u.rit_id = r.id
	    WHERE r.naam = (
	            select naam
	            from rittenkoers_rit
	            where
	                rittenkoers_rit.id = new.rit_id
	        )
	        AND u.rennernaam = new.rennernaam
	        AND NEW.rit_id <> u.rit_id
	        AND r.nr < (
	            SELECT rr.nr
	            from rittenkoers_rit as rr
	            where
	                new.rit_id = rr.id
	        )
            and u.status <> 'Did Finish'
	) THEN RAISE EXCEPTION 'Rider did not finish all races with a lower sequence number in the same race tour.';
	END IF;
	-- Return the NEW row for the trigger to complete successfully
	RETURN NEW;
	END;
	$$ LANGUAGE plpgsql;


CREATE TRIGGER TRIGGER_LOWER_SEQUENCE_RACES 
	trigger_lower_sequence_races BEFORE
	INSERT ON uitslag FOR EACH ROW
	EXECUTE
	    FUNCTION check_lower_sequence_races();
