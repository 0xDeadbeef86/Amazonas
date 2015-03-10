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

INSERT INTO "Berechtigung" (berechtigungsstufe, titel) 
VALUES (0, 'kein Zugriff');
INSERT INTO "Berechtigung" (berechtigungsstufe, titel) 
VALUES (1, 'Kunde');
INSERT INTO "Berechtigung" (berechtigungsstufe, titel) 
VALUES (2, 'Mitarbeiter');
INSERT INTO "Berechtigung" (berechtigungsstufe, titel) 
VALUES (3, 'Adminstrator');


INSERT INTO "User" (username, passwort, "fk_berechtigung")
VALUES ('kunde', 'a', 1);
INSERT INTO "User" (username, passwort, "fk_berechtigung")
VALUES ('mitarbeiter', 'a', 2);
INSERT INTO "User" (username, passwort, "fk_berechtigung")
VALUES ('admin', 'a', 3);

