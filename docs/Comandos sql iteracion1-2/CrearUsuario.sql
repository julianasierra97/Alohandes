
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

    
    
    