create table super_renners
(
    team_naam       varchar,
    team_afkorting  varchar,
    team_landcode   varchar,
    team_status     varchar,
    renner_naam     varchar,
    renner_landcode varchar,
    geboortedatum   varchar,
    geboorteplaats  varchar,
    gewicht         varchar,
    lengte          varchar
);

create table super_uitslagen
(
    wedstrijd_naam     varchar,
    wedstrijd_landcode varchar,
    route_id           varchar,
    vertrek            varchar,
    aankomst           varchar,
    afstand            varchar,
    hoogtemeters       varchar,
    moeilijkheid       varchar,
    rit_id             varchar,
    datum              varchar,
    type               varchar,
    beschrijving       varchar,
    rit_nr             varchar,
    renner_naam        varchar,
    renner_landcode    varchar,
    geboortedatum      varchar,
    geboorteplaats     varchar,
    gewicht            varchar,
    lengte             varchar,
    team_naam          varchar,
    team_afkorting     varchar,
    team_landcode      varchar,
    team_status        varchar,
    positie            varchar,
    tijd               varchar,
    bonustijd          varchar,
    uitslag_status     varchar
);