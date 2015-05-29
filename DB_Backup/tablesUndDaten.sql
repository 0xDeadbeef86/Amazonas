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
    "RechnungArtikel";

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
    anschrift VARCHAR(500) NOT NULL
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

-- CREATE OR REPLACE VIEW v_BestellungenAnzeigen AS
-- SELECT "Rechnung"."id" AS "RechnungID", "Rechnung"."datum", "Adresse"."nachname", "Adresse"."vorname", "Adresse"."anschrift", "Artikel"."id" AS "ArtikelID", "RechnungArtikel"."anzahl"
-- FROM "Rechnung", "RechnungArtikel", "UserAdresse", "User", "Adresse", "Artikel"
-- WHERE "Rechnung"."fk_user_adresse" = "UserAdresse"."id" 
-- AND "User"."id" = "UserAdresse"."fk_user"
-- AND "Adresse"."id" = "UserAdresse"."fk_adresse"
-- AND "RechnungArtikel"."fk_rechnung" = "Rechnung"."id"
-- AND "RechnungArtikel"."fk_artikel" = "Artikel"."id";

--ToDo: LEFT JOIN auf User, sodass alle User ausgegeben werden und nicht nur die, die Bestellungen getätigt haben
CREATE OR REPLACE VIEW v_KundeUmsatzBestellungen AS
    SELECT 
    "User".username AS "Kunde", 
    SUM((("Artikel".nettopreis * (100 + "Mehrwertsteuer".mehrwertsteuersatz)) * "RechnungArtikel".anzahl)) AS "Gesamtbruttoumsatz", 
    SUM((("Artikel".nettopreis * "RechnungArtikel".anzahl))) AS "Gesamtnettoumsatz", 
    COUNT("Rechnung".id) AS Bestellungen
        FROM "Artikel", "Rechnung", "RechnungArtikel", "User", "UserAdresse", "Mehrwertsteuer"
            WHERE "Artikel".id = "RechnungArtikel".fk_artikel
            AND "RechnungArtikel".fk_rechnung = "Rechnung".id
            AND "UserAdresse".id = "Rechnung".fk_user_adresse
            AND "User".id = "UserAdresse".fk_user
            AND "RechnungArtikel".fk_artikel = "Artikel".id
            AND "Artikel".fk_mehrwertsteuer = "Mehrwertsteuer".id
                GROUP BY "User".username;

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
INSERT INTO "User" (username, passwort, "fk_berechtigung")
VALUES ('kunde', 'a', 1);
INSERT INTO "User" (username, passwort, "fk_berechtigung")
VALUES ('mitarbeiter', 'a', 2);
INSERT INTO "User" (username, passwort, "fk_berechtigung")
VALUES ('admin', 'a', 3);

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
INSERT INTO "Artikel" ("name", "beschreibung", "fk_mehrwertsteuer", "fk_kategorie", "nettopreis", "aktiv") 
VALUES ('Wurst', 'Datt ist ne Wuaaarst!!!', '2' , '1', '290', 'true');


INSERT INTO "Artikel" ("name", "beschreibung", "fk_mehrwertsteuer", "fk_kategorie", "nettopreis", "aktiv") 
VALUES ('Auto', 'Bemwe', '1' , '2', '3400000', 'true');

INSERT INTO "Artikel" ("name", "beschreibung", "fk_mehrwertsteuer", "fk_kategorie", "nettopreis", "aktiv") 
VALUES ('Pampers', 'Pampers für Kinder mit 4-6kg', '1' , '2', '999', 'true');


--Adresse
INSERT INTO "Adresse" ("vorname", "nachname", "anschrift")
VALUES ('Vorname', 'Nachname', 'Anschrift');
INSERT INTO "Adresse" ("vorname", "nachname", "anschrift")
VALUES ('Peter', 'Meier', 'Daheim');

--UserAdresse
INSERT INTO "UserAdresse" ("fk_user", "fk_adresse")
VALUES (3, 1);
INSERT INTO "UserAdresse" ("fk_user", "fk_adresse")
VALUES (1, 2);

--Rechnung
INSERT INTO "Rechnung" ("fk_user_adresse") 
VALUES (1);
INSERT INTO "Rechnung" ("fk_user_adresse") 
VALUES (2);
INSERT INTO "Rechnung" ("fk_user_adresse") 
VALUES (2);

--RechnungArtikel
INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (1, 1, 1);
INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (1, 2, 2);

INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (2, 3, 5);
INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (2, 1, 1);

INSERT INTO "RechnungArtikel" ("fk_rechnung", "fk_artikel", "anzahl")
VALUES (3, 3, 3);

