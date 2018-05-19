CREATE TABLE OPERADOR(
DOCUMENTO VARCHAR(30) NOT NULL,
LOGIN VARCHAR(30) NOT NULL,
CONTRASENHA VARCHAR(30) NOT NULL,
CORREO VARCHAR(30) NOT NULL,
TIPODOCUMENTO VARCHAR(30) NOT NULL,
NOMBRE VARCHAR(30) NOT NULL,
PRIMARY KEY (DOCUMENTO),
UNIQUE (LOGIN, CORREO));


CREATE TABLE PERSONANATURAL(
DOCUMENTO VARCHAR(30) NOT NULL,
TIPO VARCHAR(30) NOT NULL,
EDAD INTEGER NOT NULL,
GENERO CHAR NOT NULL,
APELLIDO VARCHAR(30),
PRIMARY KEY (DOCUMENTO)
);

ALTER TABLE PERSONANATURAL
ADD CONSTRAINT PERSONA_FK
FOREIGN KEY(DOCUMENTO)
REFERENCES OPERADOR(DOCUMENTO);





CREATE TABLE SEGURO (
ID INTEGER NOT NULL,
COSTO DECIMAL NOT NULL,
INCENDIO CHAR NOT NULL,
ROBO CHAR NOT NULL,
INUNDACIONES CHAR NOT NULL,
PRIMARY KEY (ID));






CREATE TABLE VIVIENDA
(
ID INTEGER NOT NULL, 
CAPACIDAD INTEGER NOT NULL,
TIPO VARCHAR(30) NOT NULL,
DIRECCION VARCHAR(30) NOT NULL,
NUMEROHABITANTES INTEGER NOT NULL, 
COSTO DECIMAL NOT NULL,
ID_PERSONA VARCHAR(30),
ID_SEGURO INTEGER,
PRIMARY KEY (ID));
------------------Constraint unique direccion 
ALTER TABLE Vivienda 
ADD CONSTRAINT UNIQUE_DIRECCION
UNIQUE(direccion);

ALTER TABLE VIVIENDA
ADD CONSTRAINT UNIQUE_PERSONA
UNIQUE(ID_PERSONA);

ALTER TABLE VIVIENDA 
ADD CONSTRAINT UNIQUE_SEGURO
UNIQUE(ID_SEGURO);

ALTER TABLE VIVIENDA
ADD CONSTRAINT FK_ID_PERSONA1
FOREIGN KEY(ID_PERSONA)
REFERENCES PERSONANATURAL(DOCUMENTO);

ALTER TABLE VIVIENDA
ADD CONSTRAINT FK_ID_SEGURO
FOREIGN KEY(ID_SEGURO)
REFERENCES SEGURO(ID);




---------------Crear tabla Empresa

CREATE TABLE EMPRESA(
ID VARCHAR(30) NOT NULL,
NUMREGISTROSUPERINTENDENCIA VARCHAR(30) NOT NULL,
NUMREGISTROCAMARADECOMERCIO VARCHAR(30) NOT NULL,
DIRECCION VARCHAR(30) NOT NULL,

PRIMARY KEY(ID)
);
---------------Restricciones de ND
ALTER TABLE Empresa
ADD CONSTRAINT UNIQUE_NUMREGSI
UNIQUE(NUMREGISTROSUPERINTENDENCIA );
------------------
ALTER TABLE Empresa
ADD CONSTRAINT UNIQUE_NUMREGCC
UNIQUE(NUMREGISTROCAMARADECOMERCIO);
-------------------
ALTER TABLE Empresa
ADD CONSTRAINT UNIQUE_UBICACION
UNIQUE(DIRECCION);

ALTER TABLE EMPRESA
ADD CONSTRAINT ID_FK_EMPRESA
FOREIGN KEY (ID)
REFERENCES OPERADOR(DOCUMENTO);





---------------Crea la tabla de usuario con sus restricciones
CREATE TABLE USUARIO (
    documento VARCHAR(20) NOT NULL,
    login   VARCHAR(20) NOT NULL,
    contrasenha VARCHAR(20) NOT NULL,
    correo VARCHAR(20) NOT NULL,
    tipoDocumento VARCHAR(20) NOT NULL,
    edad INTEGER NOT NULL, CHECK (EDAD BETWEEN 18 AND 100),
    genero CHAR NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    PRIMARY KEY (documento));
---------------Restrincciones de ND
-----------------------Constraint unique usuario 
ALTER TABLE Usuario
ADD CONSTRAINT UNIQUE_USUARIO
UNIQUE(login);
-----------------------Constraint unique correo 
ALTER TABLE Usuario
ADD CONSTRAINT UNIQUE_CORREO
UNIQUE(correo);






CREATE TABLE HABITACION(
ID INTEGER NOT NULL,
CAPACIDAD INTEGER NOT NULL,
TIPO VARCHAR(30) NOT NULL,
UBICACION VARCHAR(30) NOT NULL,
PRECIO DECIMAL NOT NULL,
ESCOMPARTIDA CHAR NOT NULL,
PRIMARY KEY (ID)
);

ALTER TABLE HABITACION
ADD CONSTRAINT UNIQUE_UBICACION_HABITACION
UNIQUE(UBICACION);







-----------------Crea la tabla de contrato
CREATE TABLE CONTRATO (
ID INTEGER NOT NULL,
FECHAINICIO DATE NOT NULL,
FECHAFIN DATE NOT NULL,  CHECK (FECHAFIN>FECHAINICIO),
TIPO VARCHAR(30) NOT NULL,
ESTADO VARCHAR(30) NOT NULL,
COSTO DECIMAL NOT NULL,
ID_CLIENTE VARCHAR(30) NOT NULL,
ID_VIVIENDA INTEGER,
ID_HABITACION INTEGER,
NUMEROPERSONAS INTEGER,
FECHACREACION VARCHAR(30) NOT NULL,
PRIMARY KEY (ID)
);

ALTER TABLE CONTRATO
ADD CONSTRAINT FK_ID_CLIENTE1
FOREIGN KEY(ID_CLIENTE)
REFERENCES USUARIO(DOCUMENTO);

ALTER TABLE CONTRATO
ADD CONSTRAINT FK_ID_VIVIENDA3
FOREIGN KEY(ID_VIVIENDA)
REFERENCES VIVIENDA(ID);

ALTER TABLE CONTRATO
ADD CONSTRAINT FK_ID_HABITACION3
FOREIGN KEY(ID_HABITACION)
REFERENCES HABITACION(ID);



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







---------------Crear tabla Empresa

CREATE TABLE HORARIOEMPRESA(
ID VARCHAR(30) NOT NULL,
HORARIOAPERTURA VARCHAR(30),
HORARIOCIERRE VARCHAR(30),
PRIMARY KEY(ID)
);
---------------Restricciones 
ALTER TABLE HORARIOEMPRESA
ADD CONSTRAINT ID_FK_HORARIOEMPRESA
FOREIGN KEY (ID)
REFERENCES OPERADOR(DOCUMENTO);






CREATE TABLE OPERADORHABITACION(
ID INTEGER NOT NULL,
ID_OPERADOR VARCHAR(50) NOT NULL,
PRIMARY KEY (ID, ID_OPERADOR)
);


ALTER TABLE OPERADORHABITACION
ADD CONSTRAINT FK_ID_EMPRESA1
FOREIGN KEY(ID_OPERADOR)
REFERENCES OPERADOR(DOCUMENTO);

ALTER TABLE OPERADORHABITACION
ADD CONSTRAINT FK_ID
FOREIGN KEY(ID)
REFERENCES HABITACION(ID);



CREATE TABLE TIPOHABITACION(
ID INTEGER NOT NULL,
TIPO VARCHAR(40) NOT NULL,
PRIMARY KEY (ID, TIPO)
);

ALTER TABLE TIPOHABITACION
ADD CONSTRAINT FK_ID_HABITACION
FOREIGN KEY(ID)
REFERENCES HABITACION(ID);





CREATE TABLE SERVICIO(
ID INTEGER NOT NULL,
NOMBRE VARCHAR(30) NOT NULL,
PRIMARY KEY (ID)
);






CREATE TABLE SERVICIOEMPRESA(
IDSERVICIO INTEGER NOT NULL,
ID_EMPRESA VARCHAR2(30),
COSTO DECIMAL,
PRIMARY KEY (IDSERVICIO, id_empresa)
);


ALTER TABLE SERVICIOEMPRESA
ADD CONSTRAINT FK_ID_SERVICIOEMPRESA
FOREIGN KEY(IDSERVICIO)
REFERENCES SERVICIO(ID);


ALTER TABLE SERVICIOEMPRESA
ADD CONSTRAINT FK_ID_EMPRESAYSERVICIO
FOREIGN KEY(ID_EMPRESA)
REFERENCES EMPRESA(ID);







CREATE TABLE TIPOEMPRESA(
ID VARCHAR(30) NOT NULL,
TIPO VARCHAR(30) NOT NULL);
---------------Restricciones 
ALTER TABLE TIPOEMPRESA
ADD CONSTRAINT ID_FK_TIPOEMPRESA
FOREIGN KEY (ID)
REFERENCES OPERADOR(DOCUMENTO);




CREATE TABLE SERVICIOHABITACION(
IDSERVICIO INTEGER NOT NULL,
ID_HABITACION INTEGER,
COSTO DECIMAL,
PRIMARY KEY (IDSERVICIO, id_habitacion)
);


ALTER TABLE SERVICIOHABITACION
ADD CONSTRAINT FK_ID_SERVICIO
FOREIGN KEY(IDSERVICIO)
REFERENCES SERVICIO(ID);


ALTER TABLE SERVICIOHABITACION
ADD CONSTRAINT FK_ID_HABITACIONSERVICIO
FOREIGN KEY(ID_HABITACION)
REFERENCES HABITACION(ID);







CREATE TABLE SERVICIOVIVIENDA(
IDSERVICIO INTEGER NOT NULL,
ID_VIVIENDA INTEGER NOT NULL,
COSTO DECIMAL,
PRIMARY KEY (IDSERVICIO, id_vivienda)
);

ALTER TABLE SERVICIOVIVIENDA
ADD CONSTRAINT FK_ID_SERVICIOVIVIENDA
FOREIGN KEY(IDSERVICIO)
REFERENCES SERVICIO(ID);


ALTER TABLE SERVICIOVIVIENDA
ADD CONSTRAINT FK_ID_VIVIENDAYSERVICIO
FOREIGN KEY(ID_VIVIENDA)
REFERENCES VIVIENDA(ID);







create table contratoHabitacion(
id_contrato INTEGER NOT NULL, 
id_habitacion INTEGER NOT NULL,
primary key (id_contrato, id_habitacion));

ALTER TABLE contratoHabitacion
ADD CONSTRAINT FK_ID_CONTRATO
FOREIGN KEY(ID_CONTRATO)
REFERENCES CONTRATO(ID);

ALTER TABLE contratoHabitacion
ADD CONSTRAINT FK_ID_HABITACION_CONTRATO
FOREIGN KEY(ID_HABITACION)
REFERENCES HABITACION(ID);




create table contratoVivienda(
id_contrato INTEGER NOT NULL, 
id_vivienda INTEGER NOT NULL,
primary key (id_contrato, id_vivienda)
);

ALTER TABLE CONTRATOVIVIENDA
ADD CONSTRAINT FK_ID_CONTRATO_VIVIENDA
FOREIGN KEY(ID_CONTRATO)
REFERENCES CONTRATO(ID);

ALTER TABLE CONTRATOVIVIENDA
ADD CONSTRAINT FK_ID_VIVIENDA_CONTRATO
FOREIGN KEY(ID_VIVIENDA)
REFERENCES VIVIENDA(ID);

