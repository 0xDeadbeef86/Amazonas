DROP VIEW IF EXISTS vwuseradresse;
DROP VIEW IF EXISTS vwuser;
DROP VIEW IF EXISTS vwartikel;
DROP VIEW IF EXISTS v_KundeUmsatzBestellungen;
DROP TABLE IF EXISTS 
    "Berechtigung",
    "User",
    "Mehrwertsteuer",
    "Kategorie",
    "Artikel",
    "Adresse",
    "UserAdresse",
    "Rechnung",
    "RechnungArtikel" Cascade;

CREATE TABLE "Berechtigung" (
    id SERIAL PRIMARY KEY,
    berechtigungsstufe INT NOT NULL,
    titel VARCHAR(50) NOT NULL
);

CREATE TABLE "User" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    passwort VARCHAR(50) NOT NULL,
    "fk_berechtigung" INT REFERENCES "Berechtigung" (id)
);

CREATE TABLE "Mehrwertsteuer" (
    id SERIAL PRIMARY KEY,
    mehrwertsteuersatz INT NOT NULL
);

CREATE TABLE "Kategorie" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE "Artikel" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    beschreibung VARCHAR(5000) NULL,
    nettopreis INT NOT NULL,
    "fk_mehrwertsteuer" INT REFERENCES "Mehrwertsteuer" (id),
    "fk_kategorie" INT REFERENCES "Kategorie" (id),
    aktiv BOOLEAN NOT NULL
);

CREATE TABLE "Adresse" (
    id SERIAL PRIMARY KEY,
    vorname VARCHAR(50) NOT NULL,
    nachname VARCHAR(50) NOT NULL,
    anschrift VARCHAR(500) NOT NULL,
    hausnummer INT,
    plz INT
);

CREATE TABLE "UserAdresse" (
    id SERIAL PRIMARY KEY,
    "fk_user" INT REFERENCES "User" (id),
    "fk_adresse" INT REFERENCES "Adresse" (id)
);

CREATE TABLE "Rechnung" (
    id SERIAL PRIMARY KEY,
    fk_user_adresse INT REFERENCES "UserAdresse" (id),
    datum TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "RechnungArtikel" (
    id SERIAL PRIMARY KEY,
    "fk_rechnung" INT REFERENCES "Rechnung" (id),
    "fk_artikel" INT REFERENCES "Artikel" (id),
    anzahl INT CHECK (anzahl > 0)
);

--Views

CREATE OR REPLACE VIEW v_KundeUmsatzBestellungen AS
    SELECT
    "User".username AS "Kunde", 
    SUM((("Artikel".nettopreis * (100 + "Mehrwertsteuer".mehrwertsteuersatz)) * "RechnungArtikel".anzahl) / 100) AS "Gesamtbruttoumsatz", 
    SUM("Artikel".nettopreis * "RechnungArtikel".anzahl) AS "Gesamtnettoumsatz", 
    COUNT("Rechnung".id) AS "Bestellungen"
        FROM "UserAdresse"
             INNER JOIN "Rechnung" ON "UserAdresse".id = "Rechnung".fk_user_adresse
             INNER JOIN "RechnungArtikel" ON "RechnungArtikel".fk_rechnung = "Rechnung".id
             INNER JOIN "Artikel" ON "Artikel".id = "RechnungArtikel".fk_artikel
             INNER JOIN "Mehrwertsteuer" ON "Artikel".fk_mehrwertsteuer = "Mehrwertsteuer".id
             RIGHT JOIN "User" ON "User".id = "UserAdresse".fk_user
                 GROUP BY "User".username;



-- Artikel
CREATE OR REPLACE VIEW vwartikel AS 
    SELECT 
    "Artikel".id,
    "Artikel".name, 
    "Artikel".beschreibung,
    "Mehrwertsteuer".mehrwertsteuersatz as mehrwertsteuer, 
    "Kategorie".name AS kategorie, 
    "Artikel".nettopreis,
    "Artikel".aktiv 
        FROM "Artikel"
            JOIN "Mehrwertsteuer" ON "Mehrwertsteuer".id = "Artikel".fk_mehrwertsteuer -- "Mehrwertsteuer".mehrwertsteuersatz
            JOIN "Kategorie" ON "Kategorie".id = "Artikel".fk_kategorie -- "Kategorie".name    
                ORDER BY "Artikel".id;

CREATE OR REPLACE RULE rule_vwartikel_insert 
AS ON INSERT TO vwartikel
DO INSTEAD (
    INSERT INTO "Artikel" (name, beschreibung, fk_mehrwertsteuer, fk_kategorie, nettopreis, aktiv) VALUES (
        NEW.name, 
        NEW.beschreibung,         
        (SELECT id from "Mehrwertsteuer" where "Mehrwertsteuer".mehrwertsteuersatz=NEW.mehrwertsteuer),
        (SELECT id from "Kategorie" where "Kategorie".name=NEW.kategorie),
        NEW.nettopreis,
        NEW.aktiv
    )        
);

CREATE OR REPLACE RULE rule_vwartikel_update
AS ON UPDATE TO vwartikel
DO INSTEAD (
    UPDATE "Artikel" SET
        name = NEW.name, 
        beschreibung = NEW.beschreibung, 
        fk_mehrwertsteuer = (SELECT id from "Mehrwertsteuer" where "Mehrwertsteuer".mehrwertsteuersatz=NEW.mehrwertsteuer), 
        fk_kategorie = (SELECT id from "Kategorie" where "Kategorie".name=NEW.kategorie),
        nettopreis = NEW.nettopreis,
        aktiv = NEW.aktiv
    WHERE id = OLD.id
);

-- User
CREATE OR REPLACE VIEW vwuser AS 
    SELECT 
    "User".id,
    "User".username, 
    "User".passwort,
    "Berechtigung".titel as berechtigung,
    "Berechtigung".berechtigungsstufe
        FROM "User"
            JOIN "Berechtigung" ON "Berechtigung".id = "User".fk_berechtigung            
                ORDER BY "User".id;

CREATE OR REPLACE RULE rule_vwuser_insert 
AS ON INSERT TO vwuser
DO INSTEAD (
    INSERT INTO "User" (username, passwort, fk_berechtigung) VALUES (
        NEW.username, 
        NEW.passwort,         
        (SELECT id from "Berechtigung" where "Berechtigung".titel = NEW.berechtigung)        
    )        
);

CREATE OR REPLACE RULE rule_vwuser_update
AS ON UPDATE TO vwuser
DO INSTEAD (
    UPDATE "User" SET
        username = NEW.username, 
        passwort = NEW.passwort, 
        fk_berechtigung = (SELECT id from "Berechtigung" where "Berechtigung".titel=NEW.berechtigung)    
    WHERE id = OLD.id
);

-- UserAdresse
CREATE OR REPLACE VIEW vwuseradresse AS 
    SELECT 
    "UserAdresse".id,
    "User".id as UserID,
    "Adresse".id as AdresseID,      
    "User".username as User,   
    "Adresse".vorname as Vorname,  
    "Adresse".nachname as Nachname,  
    "Adresse".anschrift as Adresse,
    "Adresse".hausnummer as Hausnummer,
    "Adresse".plz as PLZ
        FROM "Adresse"
            JOIN "UserAdresse" ON "Adresse".id = "UserAdresse".fk_adresse
            JOIN "User" ON "UserAdresse".fk_user = "User".id          
                ORDER BY "UserAdresse".id;

-- WARUM GEHT DAS NICHT???
--CREATE OR REPLACE RULE rule_vwuseradresse_delete AS ON DELETE TO vwuseradresse DO INSTEAD (
--    DELETE FROM "UserAdresse" WHERE fk_adresse = OLD.adresseid;
--    DELETE FROM "Rechnung" WHERE fk_user_adresse = OLD.adresseid
--);


--Berechtigungen
INSERT INTO "Berechtigung" (berechtigungsstufe, titel) 
VALUES (0, 'kein Zugriff');
INSERT INTO "Berechtigung" (berechtigungsstufe, titel) 
VALUES (1, 'Kunde');
INSERT INTO "Berechtigung" (berechtigungsstufe, titel) 
VALUES (2, 'Mitarbeiter');
INSERT INTO "Berechtigung" (berechtigungsstufe, titel) 
VALUES (3, 'Adminstrator');

--User
INSERT INTO "vwuser" (username, passwort, berechtigung)
VALUES ('geloeschte Benutzer', 'a', 'Kunde');
INSERT INTO "vwuser" (username, passwort, berechtigung)
VALUES ('kunde', 'a', 'Kunde');
INSERT INTO "vwuser" (username, passwort, berechtigung)
VALUES ('mitarbeiter', 'a', 'Mitarbeiter');
INSERT INTO "vwuser" (username, passwort, berechtigung)
VALUES ('admin', 'a', 'Adminstrator');
--INSERT INTO "vwuser" (username, passwort, berechtigung)
--VALUES ('Grupfel', 'a', 'Kunde');

--Mehrwersteuersätze
INSERT INTO "Mehrwertsteuer" (mehrwertsteuersatz)
VALUES (19);
INSERT INTO "Mehrwertsteuer" (mehrwertsteuersatz)
VALUES (7);

--Kategorien
INSERT INTO "Kategorie" (name)
VALUES ('Nahrungsmittel');
INSERT INTO "Kategorie" (name)
VALUES ('Sonstiges');


--Artikel
INSERT INTO "vwartikel" ("name", "beschreibung", "mehrwertsteuer", "kategorie", "nettopreis", "aktiv")
VALUES ('Wurst', 'Datt ist ne Wuaaarst!!!', '7' , 'Nahrungsmittel', '290', 'true');

INSERT INTO "vwartikel" ("name", "beschreibung", "mehrwertsteuer", "kategorie", "nettopreis", "aktiv") 
VALUES ('Auto', 'Bemwe', '19' , 'Sonstiges', '3400000', 'true');

INSERT INTO "vwartikel" ("name", "beschreibung", "mehrwertsteuer", "kategorie", "nettopreis", "aktiv") 
VALUES ('Pampers', 'Pampers für Kinder mit 4-6kg', '19' , 'Sonstiges', '999', 'true');


--Adresse
INSERT INTO "Adresse" ("vorname", "nachname", "anschrift", "hausnummer", "plz")
VALUES ('Dummy', 'Fucker', 'Dum-Fuck-Street', 0, 666);
INSERT INTO "Adresse" ("vorname", "nachname", "anschrift", "hausnummer", "plz")
VALUES ('Bibo', 'Vogel', 'Anschrift', 1, 1111);
INSERT INTO "Adresse" ("vorname", "nachname", "anschrift", "hausnummer", "plz")
VALUES ('Peter', 'Meier', 'Daheim', 2, 2222);
INSERT INTO "Adresse" ("vorname", "nachname", "anschrift", "hausnummer", "plz")
VALUES ('Andreas', 'Schulz', 'Im Gems 5', 3, 3333);

--UserAdresse
INSERT INTO "UserAdresse" ("fk_user", "fk_adresse")
VALUES (1, 1);
INSERT INTO "UserAdresse" ("fk_user", "fk_adresse")
VALUES (4, 2);
INSERT INTO "UserAdresse" ("fk_user", "fk_adresse")
VALUES (2, 3);
INSERT INTO "UserAdresse" ("fk_user", "fk_adresse")
VALUES (3, 4);

--Rechnung
INSERT INTO "Rechnung" ("fk_user_adresse") 
VALUES (2);
INSERT INTO "Rechnung" ("fk_user_adresse") 
VALUES (3);
INSERT INTO "Rechnung" ("fk_user_adresse") 
VALUES (4);

--RechnungArtikel
INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (1, 1, 1);
INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (1, 2, 1);

INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (2, 3, 1);
INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (2, 1, 1);

INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (3, 3, 1);
