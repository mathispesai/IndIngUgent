--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

--
-- Name: eendagswedstrijd; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.eendagswedstrijd (
    naam character varying NOT NULL
);


--
-- Name: eendagswedstrijd_rit; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.eendagswedstrijd_rit (
    id integer NOT NULL,
    naam character varying NOT NULL
);

--
-- Name: rit; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.rit (
    id integer NOT NULL,
    datum date NOT NULL,
    type character varying NOT NULL,
    beschrijving character varying NOT NULL,
    route_id integer NOT NULL
);

--
-- Name: rittenkoers; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.rittenkoers (
    naam character varying NOT NULL
);

--
-- Name: rittenkoers_rit; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.rittenkoers_rit (
    id integer NOT NULL,
    naam character varying NOT NULL,
    nr integer NOT NULL
);

--
-- Name: route; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.route (
    id integer NOT NULL,
    vertrek character varying NOT NULL,
    aankomst character varying NOT NULL,
    afstand double precision NOT NULL,
    hoogtemeters integer NOT NULL,
    moeilijkheid integer NOT NULL
);

--
-- Name: uitslag; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.uitslag (
    rennernaam character varying NOT NULL,
    rit_id integer NOT NULL,
    positie integer,
    tijd interval,
    bonustijd interval,
    status character varying NOT NULL
);

--
-- Name: wedstrijd; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.wedstrijd (
    naam character varying NOT NULL,
    landcode character(2) NOT NULL
);

--
-- Name: wielerteam; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.wielerteam (
    naam character varying NOT NULL,
    afkorting character(3) NOT NULL,
    landcode character(2) NOT NULL,
    status character varying NOT NULL
);

--
-- Name: wielrenner; Type: TABLE; Schema: basic; 
--

CREATE TABLE public.wielrenner (
    naam character varying NOT NULL,
    landcode character(2) NOT NULL,
    geboortedatum date NOT NULL,
    geboorteplaats character varying,
    gewicht double precision,
    lengte double precision,
    teamnaam character varying NOT NULL
);

--
-- Name: eendagswedstrijd eendagswedstrijd_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.eendagswedstrijd
    ADD CONSTRAINT eendagswedstrijd_pkey PRIMARY KEY (naam);


--
-- Name: eendagswedstrijd_rit eendagswedstrijd_rit_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.eendagswedstrijd_rit
    ADD CONSTRAINT eendagswedstrijd_rit_pkey PRIMARY KEY (id);


--
-- Name: eendagswedstrijd_rit eendagswedstrijd_rit_uc; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.eendagswedstrijd_rit
    ADD CONSTRAINT eendagswedstrijd_rit_uc UNIQUE (naam);


--
-- Name: rit rit_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.rit
    ADD CONSTRAINT rit_pkey PRIMARY KEY (id);


--
-- Name: rittenkoers rittenkoers_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.rittenkoers
    ADD CONSTRAINT rittenkoers_pkey PRIMARY KEY (naam);


--
-- Name: rittenkoers_rit rittenkoers_rit_naam_nr_key; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.rittenkoers_rit
    ADD CONSTRAINT rittenkoers_rit_naam_nr_key UNIQUE (naam, nr);


--
-- Name: rittenkoers_rit rittenkoers_rit_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.rittenkoers_rit
    ADD CONSTRAINT rittenkoers_rit_pkey PRIMARY KEY (id);


--
-- Name: route route_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.route
    ADD CONSTRAINT route_pkey PRIMARY KEY (id);


--
-- Name: uitslag uitslag_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.uitslag
    ADD CONSTRAINT uitslag_pkey PRIMARY KEY (rennernaam, rit_id);


--
-- Name: wedstrijd wedstrijd_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.wedstrijd
    ADD CONSTRAINT wedstrijd_pkey PRIMARY KEY (naam);


--
-- Name: wielerteam wielerteam_afkorting_key; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.wielerteam
    ADD CONSTRAINT wielerteam_afkorting_key UNIQUE (afkorting);


--
-- Name: wielerteam wielerteam_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.wielerteam
    ADD CONSTRAINT wielerteam_pkey PRIMARY KEY (naam);


--
-- Name: wielrenner wielrenner_pkey; Type: CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.wielrenner
    ADD CONSTRAINT wielrenner_pkey PRIMARY KEY (naam);


--
-- Name: eendagswedstrijd eendagswedstrijd_naam_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.eendagswedstrijd
    ADD CONSTRAINT eendagswedstrijd_naam_fkey FOREIGN KEY (naam) REFERENCES public.wedstrijd(naam);


--
-- Name: eendagswedstrijd_rit eendagswedstrijd_rit_id_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.eendagswedstrijd_rit
    ADD CONSTRAINT eendagswedstrijd_rit_id_fkey FOREIGN KEY (id) REFERENCES public.rit(id);


--
-- Name: eendagswedstrijd_rit eendagswedstrijd_rit_naam_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.eendagswedstrijd_rit
    ADD CONSTRAINT eendagswedstrijd_rit_naam_fkey FOREIGN KEY (naam) REFERENCES public.eendagswedstrijd(naam);


--
-- Name: rit rit_route_id_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.rit
    ADD CONSTRAINT rit_route_id_fkey FOREIGN KEY (route_id) REFERENCES public.route(id);


--
-- Name: rittenkoers rittenkoers_naam_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.rittenkoers
    ADD CONSTRAINT rittenkoers_naam_fkey FOREIGN KEY (naam) REFERENCES public.wedstrijd(naam);


--
-- Name: rittenkoers_rit rittenkoers_rit_id_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.rittenkoers_rit
    ADD CONSTRAINT rittenkoers_rit_id_fkey FOREIGN KEY (id) REFERENCES public.rit(id);


--
-- Name: rittenkoers_rit rittenkoers_rit_naam_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.rittenkoers_rit
    ADD CONSTRAINT rittenkoers_rit_naam_fkey FOREIGN KEY (naam) REFERENCES public.rittenkoers(naam);


--
-- Name: uitslag uitslag_rennernaam_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.uitslag
    ADD CONSTRAINT uitslag_rennernaam_fkey FOREIGN KEY (rennernaam) REFERENCES public.wielrenner(naam);


--
-- Name: uitslag uitslag_rit_id_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.uitslag
    ADD CONSTRAINT uitslag_rit_id_fkey FOREIGN KEY (rit_id) REFERENCES public.rit(id);


--
-- Name: wielrenner wielrenner_teamnaam_fkey; Type: FK CONSTRAINT; Schema: basic; 
--

ALTER TABLE ONLY public.wielrenner
    ADD CONSTRAINT wielrenner_teamnaam_fkey FOREIGN KEY (teamnaam) REFERENCES public.wielerteam(naam);


--
-- PostgreSQL database dump complete
--

