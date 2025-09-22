create table super_clubs_en_spelers (
    clubnaam varchar,
    stadionnaam varchar,
    capaciteit varchar,
    spelerid varchar,
    voornaam varchar,
    familienaam varchar,
    geboorteplaats varchar,
    geboorteland varchar,
    geboortedatum varchar,
    veldpositie varchar,
    marktwaarde varchar
);

create table super_wedstrijdevents (
    datum varchar,
    thuisclub varchar,
    uitclub varchar,
    thuisdoelpunten varchar,
    uitdoelpunten varchar,
    toeschouwers varchar,
    eventnummer varchar,
    eventminuut varchar,
    doelpunt_spelerid varchar,
    doelpunt_beschrijving varchar,
    vervanging_uitgaande_speler_id varchar,
    vervanging_inkomende_speler_id varchar
);