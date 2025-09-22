delete from vervanging;
delete from doelpunt;
delete from wedstrijdevent;
delete from wedstrijd;
delete from speler;
delete from club;
delete from stadion;

insert into stadion
select distinct stadionnaam, capaciteit::integer
from super_clubs_en_spelers;

insert into club
select distinct clubnaam, stadionnaam
from super_clubs_en_spelers;

insert into speler
select distinct spelerid::integer,
                voornaam,
                familienaam,
                geboorteplaats,
                geboorteland,
                geboortedatum::date,
                veldpositie,
                marktwaarde::integer,
                clubnaam
from super_clubs_en_spelers;

insert into wedstrijd
select distinct thuisclub,
                uitclub,
                datum::date,
                thuisdoelpunten::integer,
                uitdoelpunten::integer,
                toeschouwers::integer
from super_wedstrijdevents;

insert into wedstrijdevent
select distinct thuisclub, datum::date, eventnummer::integer, eventminuut::integer
from super_wedstrijdevents
where eventnummer is not null;

insert into doelpunt
select distinct thuisclub, datum::date, eventnummer::integer, doelpunt_beschrijving, doelpunt_spelerid::integer
from super_wedstrijdevents
where doelpunt_spelerid is not null;

insert into vervanging
select distinct thuisclub,
                datum::date,
                eventnummer::integer,
                vervanging_inkomende_speler_id::integer,
                vervanging_uitgaande_speler_id::integer
from super_wedstrijdevents
where vervanging_inkomende_speler_id is not null;