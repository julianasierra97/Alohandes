CREATE TABLE SERVICIO(
ID INTEGER NOT NULL,
COSTO DECIMAL NOT NULL,
NOMBRE VARCHAR(30) NOT NULL,
ID_EMPRESA VARCHAR(30),
ID_VIVIENDA INTEGER,
ID_HABITACION INTEGER,
PRIMARY KEY (ID)
);

ALTER TABLE SERVICIO
ADD CONSTRAINT FK_ID_EMPRESA1
FOREIGN KEY(ID_EMPRESA)
REFERENCES EMPRESA(ID);

ALTER TABLE SERVICIO
ADD CONSTRAINT FK_ID_VIVIENDA
FOREIGN KEY(ID_VIVIENDA)
REFERENCES VIVIENDA(ID);

ALTER TABLE SERVICIO
ADD CONSTRAINT FK_ID_HABITACION
FOREIGN KEY(ID_HABITACION)
REFERENCES HABITACION(ID);