CREATE TABLE FAMILIAUSUARIO(
ID_USUARIO VARCHAR(30) NOT NULL,
ID_FAMILIA VARCHAR(30) NOT NULL,
PRIMARY KEY (ID_USUARIO, ID_FAMILIA)
);

ALTER TABLE FAMILIAUSUARIO
ADD CONSTRAINT FK_ID_USUARIO
FOREIGN KEY(ID_USUARIO)
REFERENCES USUARIO(DOCUMENTO);

ALTER TABLE FAMILIAUSUARIO
ADD CONSTRAINT FK_ID_FAMILIA
FOREIGN KEY(ID_FAMILIA)
REFERENCES USUARIO(DOCUMENTO);