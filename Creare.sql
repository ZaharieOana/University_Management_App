drop database if exists Proiect_2max;
create database Proiect_2max;
use Proiect_2max;

CREATE TABLE Utilizator (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cnp INT,
    nume VARCHAR(45),
    prenume VARCHAR(45),
    adresa VARCHAR(200),
    telefon VARCHAR(10),
    email VARCHAR(45) UNIQUE,
    IBAN VARCHAR(45),
    nr_contract INT,
    parola VARCHAR(20)
);
  
CREATE TABLE Student (
    id INT PRIMARY KEY,
    an_studiu INT,
    nr_ore INT,
    FOREIGN KEY (id)
        REFERENCES Utilizator (id)
);

CREATE TABLE Profesor (
    id INT PRIMARY KEY,
    nr_cursuri INT,
    min_ore INT,
    max_ore INT,
    departament VARCHAR(45),
    FOREIGN KEY (id)
        REFERENCES Utilizator (id)
);

CREATE TABLE Administrator (
    id INT PRIMARY KEY,
    superadmin BOOLEAN,
    FOREIGN KEY (id)
        REFERENCES Utilizator (id)
);

CREATE TABLE FisaMaterie (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(35),
    descriere VARCHAR(200)
);

CREATE TABLE Materie (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_profesor INT,
    id_fisa INT,
    studenti INT,
    max_studenti INT,
    FOREIGN KEY (id_fisa)
        REFERENCES FisaMaterie (id),
    FOREIGN KEY (id_profesor)
        REFERENCES Profesor (id)
);

CREATE TABLE Contract (
    id_student INT,
    id_materie INT,
    PRIMARY KEY (id_student , id_materie),
    inscris_grup BOOLEAN,
    nota_curs DECIMAL(4 , 2 ),
    nota_seminar DECIMAL(4 , 2 ),
    nota_laborator DECIMAL(4 , 2 ),
    FOREIGN KEY (id_student)
        REFERENCES Student (id),
    FOREIGN KEY (id_materie)
        REFERENCES Materie (id)
);

CREATE TABLE Curs (
    id_materie INT PRIMARY KEY,
    data_inceput DATE,
    data_final DATE,
    procent INT,
    FOREIGN KEY (id_materie)
        REFERENCES Materie (id)
);

CREATE TABLE Laborator (
    id_materie INT PRIMARY KEY,
    data_inceput DATE,
    data_final DATE,
    procent INT,
    FOREIGN KEY (id_materie)
        REFERENCES Materie (id)
);

CREATE TABLE Seminar (
    id_materie INT PRIMARY KEY,
    data_inceput DATE,
    data_final DATE,
    procent INT,
    FOREIGN KEY (id_materie)
        REFERENCES Materie (id)
);

CREATE TABLE Grup (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_materie INT,
    FOREIGN KEY (id_materie)
        REFERENCES FisaMaterie (id)
);

CREATE TABLE Mesaj (
    id_student INT,
    id_grup INT,
    data_trimitere DATETIME,
    PRIMARY KEY (id_student , id_grup , data_trimitere),
    mesaj VARCHAR(280),
    FOREIGN KEY (id_student)
        REFERENCES Student (id),
    FOREIGN KEY (id_grup)
        REFERENCES Grup (id)
);

CREATE TABLE ActivitateGrup (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_grup INT,
    id_prof INT,
    nume VARCHAR(30),
    data_activitate DATETIME,
    data_expirare DATETIME,
    durata TIME,
    nr_min_stud INT,
    FOREIGN KEY (id_grup)
        REFERENCES Grup (id),
    FOREIGN KEY (id_prof)
        REFERENCES Profesor (id)
);

CREATE TABLE StudentActivitateGrup (
    id_student INT,
    id_activitate INT,
    PRIMARY KEY (id_student , id_activitate),
    FOREIGN KEY (id_student)
        REFERENCES Student (id),
    FOREIGN KEY (id_activitate)
        REFERENCES ActivitateGrup (id)
);

CREATE TABLE ActivitateMaterie (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_materie INT,
    tip VARCHAR(30),
    data_activitate DATETIME,
    durata TIME,
    nr_max_stud INT,
    FOREIGN KEY (id_materie)
        REFERENCES Materie (id)
);

CREATE TABLE StudentActivitateMaterie (
    id_student INT,
    id_activitate INT,
    PRIMARY KEY (id_student , id_activitate),
    FOREIGN KEY (id_student)
        REFERENCES Student (id),
    FOREIGN KEY (id_activitate)
        REFERENCES ActivitateMaterie (id)
);

CREATE TABLE Notificare (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_utilizator INT,
    data_primire DATETIME,
    mesaj VARCHAR(280),
    FOREIGN KEY (id_utilizator)
        REFERENCES Utilizator (id)
);
