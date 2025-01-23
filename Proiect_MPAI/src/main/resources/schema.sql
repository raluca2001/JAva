CREATE TABLE Studenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(255),
    media DOUBLE,
    participant_competitii BOOLEAN,
    venit_familie DOUBLE,
    membri_familie INT,
    zona_defavorizata BOOLEAN,
    probleme_medicale BOOLEAN,
    deces_in_familie BOOLEAN,
    situatie_exceptionala BOOLEAN,
    anul_studiu INT
);

CREATE TABLE Burse (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tip VARCHAR(255),
    suma_alocata DOUBLE,
    student_id INT,
    state VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES Studenti(id)
);
