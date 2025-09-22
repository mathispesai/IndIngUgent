--
-- PostgreSQL database dump
--

-- Dumped from database version 13.0
-- Dumped by pg_dump version 13.0

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

