--------------------------------------------------------
-- Archivo creado  - viernes-abril-20-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table TIPOEMPRESA
--------------------------------------------------------

  CREATE TABLE "ISIS2304A631810"."TIPOEMPRESA" 
   (	"ID" VARCHAR2(30 BYTE), 
	"TIPO" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS NOLOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TBSPROD" ;
REM INSERTING into ISIS2304A631810.TIPOEMPRESA
SET DEFINE OFF;
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('9932118103411345','Hostal');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('6047794489456565','Hostal');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('1984560871242574','Hostal');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('1269592162498916','Hotel');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('3332963235175607','Hotel');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('2719314594724096','Hotel');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('8678964081420045','Hotel');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('9613642528505241','Vivienda Universitaria');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('9648767625211439','Vivienda Universitaria');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('6317380200814872','Vivienda Universitaria');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('233u29372','Hotel');
Insert into ISIS2304A631810.TIPOEMPRESA (ID,TIPO) values ('233u243539372','Hotel');
--------------------------------------------------------
--  Constraints for Table TIPOEMPRESA
--------------------------------------------------------

  ALTER TABLE "ISIS2304A631810"."TIPOEMPRESA" MODIFY ("TIPO" NOT NULL ENABLE);
  ALTER TABLE "ISIS2304A631810"."TIPOEMPRESA" MODIFY ("ID" NOT NULL ENABLE);
