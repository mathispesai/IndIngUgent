--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: check_doelpunten(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_doelpunten() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.check_doelpunten() OWNER TO postgres;

--
-- Name: check_toeschouwers(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_toeschouwers() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    capaciteit_stadion INTEGER;
BEGIN

    SELECT s.capaciteit INTO capaciteit_stadion FROM club c INNER JOIN stadion s ON c.stadionnaam = s.naam WHERE c.naam = NEW.thuisclub;

    IF (NEW.toeschouwers > capaciteit_stadion) THEN
        RAISE EXCEPTION 'Het aantal toeschouwers % die de wedstrijd met thuisclub % op datum % bijwoont overschrijdt de maximale capaciteit % van het stadion.', NEW.toeschouwers, NEW.thuisclub, NEW.datum, capaciteit_stadion;
    END IF;

    RETURN NEW;

END;
$$;


ALTER FUNCTION public.check_toeschouwers() OWNER TO postgres;

--
-- Name: controleer_wedstrijd_datum(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.controleer_wedstrijd_datum() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF (EXISTS(SELECT 1 FROM wedstrijd WHERE datum = NEW.datum AND thuisclub = NEW.uitclub)) THEN
        RAISE EXCEPTION 'De uitclub % heeft al een wedstrijd op % en kan dus geen andere wedstrijd spelen op die dag.', NEW.uitclub, NEW.datum;
    ELSEIF (EXISTS(SELECT 1 FROM wedstrijd WHERE datum = NEW.datum and uitclub = NEW.thuisclub)) THEN
        RAISE EXCEPTION 'De thuisclub % heeft al een wedstrijg op % en kan dus geen andere wedstrijd spelen op die dag.', NEW.thuisclub, NEW.datum;
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.controleer_wedstrijd_datum() OWNER TO postgres;

--
-- Name: increment(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.increment(value integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
    DECLARE
        result integer;
        incrementer CONSTANT integer := 1;
    BEGIN
        result := value + incrementer;
        RAISE INFO 'the result of this call is %', result;
        RETURN result;
    END;
    $$;


ALTER FUNCTION public.increment(value integer) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: club; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.club (
    naam character varying NOT NULL,
    stadionnaam character varying NOT NULL
);


ALTER TABLE public.club OWNER TO postgres;

--
-- Name: doelpunt; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doelpunt (
    thuisclub character varying NOT NULL,
    datum date NOT NULL,
    nr integer NOT NULL,
    beschrijving character varying NOT NULL,
    spelerid integer NOT NULL
);


ALTER TABLE public.doelpunt OWNER TO postgres;

--
-- Name: speler; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.speler (
    id integer NOT NULL,
    voornaam character varying,
    familienaam character varying NOT NULL,
    geboorteplaats character varying,
    geboorteland character varying,
    geboortedatum date,
    veldpositie character varying,
    marktwaarde integer,
    clubnaam character varying NOT NULL,
    CONSTRAINT speler_marktwaarde_check CHECK ((marktwaarde >= 0)),
    CONSTRAINT speler_veldpositie_check CHECK (((veldpositie)::text = ANY (ARRAY[('Goalkeeper'::character varying)::text, ('Attack'::character varying)::text, ('Defender'::character varying)::text, ('Midfield'::character varying)::text])))
);


ALTER TABLE public.speler OWNER TO postgres;

--
-- Name: stadion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stadion (
    naam character varying NOT NULL,
    capaciteit integer NOT NULL,
    CONSTRAINT stadion_capaciteit_check CHECK ((capaciteit > 0))
);


ALTER TABLE public.stadion OWNER TO postgres;

--
-- Name: vervanging; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vervanging (
    thuisclub character varying NOT NULL,
    datum date NOT NULL,
    nr integer NOT NULL,
    spelerid_in integer NOT NULL,
    spelerid_uit integer NOT NULL,
    CONSTRAINT vervanging_check CHECK ((spelerid_in <> spelerid_uit))
);


ALTER TABLE public.vervanging OWNER TO postgres;

--
-- Name: wedstrijd; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wedstrijd (
    thuisclub character varying NOT NULL,
    uitclub character varying NOT NULL,
    datum date NOT NULL,
    thuisdoelpunten integer NOT NULL,
    uitdoelpunten integer NOT NULL,
    toeschouwers integer,
    CONSTRAINT wedstrijd_check CHECK (((thuisclub)::text <> (uitclub)::text)),
    CONSTRAINT wedstrijd_thuisdoelpunten_check CHECK ((thuisdoelpunten >= 0)),
    CONSTRAINT wedstrijd_toeschouwers_check CHECK ((toeschouwers >= 0)),
    CONSTRAINT wedstrijd_uitdoelpunten_check CHECK ((uitdoelpunten >= 0))
);


ALTER TABLE public.wedstrijd OWNER TO postgres;

--
-- Name: wedstrijdevent; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wedstrijdevent (
    thuisclub character varying NOT NULL,
    datum date NOT NULL,
    nr integer NOT NULL,
    minuut integer NOT NULL,
    CONSTRAINT wedstrijdevent_minuut_check CHECK (((minuut >= 0) AND (minuut <= 120))),
    CONSTRAINT wedstrijdevent_nr_check CHECK ((nr >= 1))
);


ALTER TABLE public.wedstrijdevent OWNER TO postgres;

--
-- Name: winnaar_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.winnaar_view AS
 SELECT thuisclub,
    uitclub,
    datum,
        CASE
            WHEN (thuisdoelpunten > uitdoelpunten) THEN thuisclub
            WHEN (thuisdoelpunten < uitdoelpunten) THEN uitclub
            ELSE 'Gelijkspel'::character varying
        END AS winnaar,
    abs((thuisdoelpunten - uitdoelpunten)) AS doelsaldo
   FROM public.wedstrijd;


ALTER VIEW public.winnaar_view OWNER TO postgres;

--
-- Name: club club_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.club
    ADD CONSTRAINT club_pkey PRIMARY KEY (naam);


--
-- Name: doelpunt doelpunt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doelpunt
    ADD CONSTRAINT doelpunt_pkey PRIMARY KEY (thuisclub, datum, nr);


--
-- Name: speler speler_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.speler
    ADD CONSTRAINT speler_pkey PRIMARY KEY (id);


--
-- Name: stadion stadion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stadion
    ADD CONSTRAINT stadion_pkey PRIMARY KEY (naam);


--
-- Name: vervanging vervanging_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vervanging
    ADD CONSTRAINT vervanging_pkey PRIMARY KEY (thuisclub, datum, nr);


--
-- Name: wedstrijd wedstrijd_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wedstrijd
    ADD CONSTRAINT wedstrijd_pkey PRIMARY KEY (thuisclub, datum);


--
-- Name: wedstrijdevent wedstrijdevent_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wedstrijdevent
    ADD CONSTRAINT wedstrijdevent_pkey PRIMARY KEY (thuisclub, datum, nr);


--
-- Name: doelpunt check_doelpunten_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_doelpunten_trigger BEFORE INSERT ON public.doelpunt FOR EACH ROW EXECUTE FUNCTION public.check_doelpunten();


--
-- Name: wedstrijd check_toeschouwers; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_toeschouwers BEFORE INSERT ON public.wedstrijd FOR EACH ROW EXECUTE FUNCTION public.check_toeschouwers();


--
-- Name: wedstrijd trigger_wedstrijd_datum; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trigger_wedstrijd_datum BEFORE INSERT ON public.wedstrijd FOR EACH ROW EXECUTE FUNCTION public.controleer_wedstrijd_datum();


--
-- Name: club club_stadionnaam_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.club
    ADD CONSTRAINT club_stadionnaam_fkey FOREIGN KEY (stadionnaam) REFERENCES public.stadion(naam);


--
-- Name: doelpunt doelpunt_spelerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doelpunt
    ADD CONSTRAINT doelpunt_spelerid_fkey FOREIGN KEY (spelerid) REFERENCES public.speler(id);


--
-- Name: doelpunt doelpunt_thuisclub_datum_nr_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doelpunt
    ADD CONSTRAINT doelpunt_thuisclub_datum_nr_fkey FOREIGN KEY (thuisclub, datum, nr) REFERENCES public.wedstrijdevent(thuisclub, datum, nr);


--
-- Name: speler speler_clubnaam_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.speler
    ADD CONSTRAINT speler_clubnaam_fkey FOREIGN KEY (clubnaam) REFERENCES public.club(naam);


--
-- Name: vervanging vervanging_spelerid_in_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vervanging
    ADD CONSTRAINT vervanging_spelerid_in_fkey FOREIGN KEY (spelerid_in) REFERENCES public.speler(id);


--
-- Name: vervanging vervanging_spelerid_uit_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vervanging
    ADD CONSTRAINT vervanging_spelerid_uit_fkey FOREIGN KEY (spelerid_uit) REFERENCES public.speler(id);


--
-- Name: vervanging vervanging_thuisclub_datum_nr_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vervanging
    ADD CONSTRAINT vervanging_thuisclub_datum_nr_fkey FOREIGN KEY (thuisclub, datum, nr) REFERENCES public.wedstrijdevent(thuisclub, datum, nr);


--
-- Name: wedstrijd wedstrijd_thuisclub_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wedstrijd
    ADD CONSTRAINT wedstrijd_thuisclub_fkey FOREIGN KEY (thuisclub) REFERENCES public.club(naam);


--
-- Name: wedstrijd wedstrijd_uitclub_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wedstrijd
    ADD CONSTRAINT wedstrijd_uitclub_fkey FOREIGN KEY (uitclub) REFERENCES public.club(naam);


--
-- Name: wedstrijdevent wedstrijdevent_thuisclub_datum_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wedstrijdevent
    ADD CONSTRAINT wedstrijdevent_thuisclub_datum_fkey FOREIGN KEY (thuisclub, datum) REFERENCES public.wedstrijd(thuisclub, datum);


--
-- PostgreSQL database dump complete
--

