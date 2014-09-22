
--Elimina Sequencias
DROP SEQUENCE USER_ID_SEQ;
DROP SEQUENCE MESSAGE_ID_SEQ;
DROP SEQUENCE DIA_ID_SEQ;
DROP SEQUENCE FERIAS_ID_SEQ;
DROP SEQUENCE RECIBO_ID_SEQ;
DROP SEQUENCE REQUISICAO_ID_SEQ;

-- Eliminar Constraints --
alter table ADMINISTRADOR drop constraint FK_ADMINIST_INCLUI2_UTILIZAD;
alter table DIA_TRABALHO drop constraint FK_DIA_TRAB_DE_RECIBO;
alter table DIA_TRABALHO drop constraint FK_DIA_TRAB_TRABALHA_FUNCIONA;
alter table FERIAS drop constraint FK_FERIAS_MARCA_UTILIZAD;
alter table FUNCIONARIO drop constraint FK_FUNCIONA_INCLUI_UTILIZAD;
alter table LE drop constraint FK_LE_LE_UTILIZAD;
alter table LE drop constraint FK_LE_LE2_MENSAGEM;
alter table MENSAGEM drop constraint FK_MENSAGEM_ENVIA_UTILIZAD;
alter table RECIBO drop constraint FK_RECIBO_EMITE_ADMINIST;
alter table RECIBO drop constraint FK_RECIBO_RECEBE_FUNCIONA;
alter table REQUISICAO drop constraint FK_REQUISIC_REQUISITA_UTILIZAD;
alter table UTILIZADOR drop constraint FK_UTILIZAD_RELATIONS_FUNCIONA;

-- Eliminar Tabelas
drop table ADMINISTRADOR cascade constraints;

drop index DE_FK;
drop index TRABALHA_FK;
drop table DIA_TRABALHO cascade constraints;

drop index MARCA_FK;
drop table FERIAS cascade constraints;

drop table FUNCIONARIO cascade constraints;

drop index LE2_FK;
drop index LE_FK;
drop table LE cascade constraints;

drop index ENVIA_FK;
drop table MENSAGEM cascade constraints;

drop index EMITE_FK;
drop index RECEBE_FK;
drop table RECIBO cascade constraints;

drop index REQUISITA_FK;
drop table REQUISICAO cascade constraints;

drop index RELATIONSHIP_1_FK;
drop table UTILIZADOR cascade constraints;

--Cria tabela UTILIZADOR
create table UTILIZADOR 
(
   ID_USER              INTEGER              not null,
   NOME                 VARCHAR2(30)         not null,
   USERNAME             VARCHAR2(14)         not null,
   PASSWORD             VARCHAR2(16)         not null,
   RUA                  VARCHAR2(30),
   CODIGO_POSTAL        CHAR(7),
   TELEMOVEL            CHAR(9),
   DATA_NASCIMENTO      DATE,
   NACIONALIDADE        VARCHAR2(15),
   constraint PK_UTILIZADOR primary key (ID_USER)
);
create index RELATIONSHIP_1_FK on UTILIZADOR (
   ID_USER ASC
);


--Cria tabela ADMINISTRADOR
create table ADMINISTRADOR 
(
   ID_USER              INTEGER              not null,
   CARGO                CHAR(20),
   ESPECIALIZACAO       CHAR(20),
   constraint PK_ADMINISTRADOR primary key (ID_USER),
   constraint FK_ADMINIST_INCLUI2_UTILIZAD foreign key (ID_USER) references UTILIZADOR (ID_USER)
);
--Cria tabela FUNCIONARIO
create table FUNCIONARIO 
(
   ID_USER              INTEGER              not null,
   HORAS_TRAB           INTEGER              not null,
   SALARIO_BASE         FLOAT(8),
   constraint PK_FUNCIONARIO primary key (ID_USER),
   constraint FK_FUNCIONA_INCLUI_UTILIZAD foreign key (ID_USER) references UTILIZADOR (ID_USER)
);

--Cria tabela FERIAS
create table FERIAS 
(
   ID_FERIAS            INTEGER              not null,
   ID_USER              INTEGER              not null,
   DATA_INICIO          DATE                 not null,
   DATA_FIM             DATE                 not null,
   CONFIRMADO			VARCHAR2(10)				 not null,
   constraint PK_FERIAS primary key (ID_FERIAS),
   constraint FK_FERIAS_MARCA_UTILIZAD foreign key (ID_USER) references UTILIZADOR (ID_USER)
);
create index MARCA_FK on FERIAS (
   ID_USER ASC
);

--Cria tabela REQUISICAO
create table REQUISICAO 
(
   ID_REQUISICAO        INTEGER              not null,
   ID_USER              INTEGER              not null,
   NOME_SALA            CHAR(16)             not null,
   HORA_INICIO          DATE                 not null,
   HORA_FIM             DATE                 not null,
   constraint PK_REQUISICAO primary key (ID_REQUISICAO),
   constraint FK_REQUISIC_REQUISITA_UTILIZAD foreign key (ID_USER) references UTILIZADOR (ID_USER)
);
create index REQUISITA_FK on REQUISICAO (
   ID_USER ASC
);
--Cria Tabela MENSAGEM
create table MENSAGEM 
(
   ID_MENSAGEM          INTEGER              not null,
   ID_USER              INTEGER              not null,
   TEXTO                CLOB                 not null,
   ASSUNTO              VARCHAR2(30)         not null,
   TIPO_MENSAGEM        SMALLINT             ,
   constraint PK_MENSAGEM primary key (ID_MENSAGEM),
   constraint FK_MENSAGEM_ENVIA_UTILIZAD foreign key (ID_USER) references UTILIZADOR (ID_USER)
);
create index ENVIA_FK on MENSAGEM (
   ID_USER ASC
);
--Cria tabela LE
create table LE 
(
   ID_USER              INTEGER              not null,
   ID_MENSAGEM          INTEGER              not null,
   constraint PK_LE primary key (ID_USER, ID_MENSAGEM),
   constraint FK_LE_LE_UTILIZAD foreign key (ID_USER) references UTILIZADOR (ID_USER),
   constraint FK_LE_LE2_MENSAGEM foreign key (ID_MENSAGEM) references MENSAGEM (ID_MENSAGEM)
);
create index LE_FK on LE (
   ID_USER ASC
);
create index LE2_FK on LE (
   ID_MENSAGEM ASC
);

--Cria tabela RECIBO
create table RECIBO 
(
   ID_RECIBO            INTEGER              not null,
   ID_USER              INTEGER              not null,
   DATA_RECIBO          DATE,
   QUANTIA              FLOAT(8),
   HORAS_TRAB			INTEGER,
   constraint PK_RECIBO primary key (ID_RECIBO),
   constraint FK_RECIBO_EMITE_ADMINIST foreign key (ID_USER) references FUNCIONARIO (ID_USER)
);

create index EMITE_FK on RECIBO (
   ID_USER ASC
);

--Cria tabela DIA_TRABALHO
create table DIA_TRABALHO 
(
   ID_DIA               INTEGER              not null,
   ID_RECIBO            INTEGER              ,
   ID_USER              INTEGER              not null,
   HORA_ENTRADA         TIMESTAMP            not null,
   HORA_SAIDA           TIMESTAMP            ,
   DATA_DIA     	    DATE,
   constraint PK_DIA_TRABALHO primary key (ID_DIA),
   constraint FK_DIA_TRAB_DE_RECIBO foreign key (ID_RECIBO) references RECIBO (ID_RECIBO),
   constraint FK_DIA_TRAB_TRABALHA_FUNCIONA foreign key (ID_USER) references FUNCIONARIO (ID_USER)
);
create index TRABALHA_FK on DIA_TRABALHO (
   ID_USER ASC
);
create index DE_FK on DIA_TRABALHO (
   ID_RECIBO ASC
);

--Cria Sequencia
CREATE SEQUENCE USER_ID_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE MESSAGE_ID_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE DIA_ID_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE FERIAS_ID_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE RECIBO_ID_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE REQUISICAO_ID_SEQ START WITH 1000 INCREMENT BY 1;






