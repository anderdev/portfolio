
    alter table cartao 
        drop 
        foreign key FK_CA_USUARIO;

    alter table cartao 
        drop 
        foreign key FK_CA_MASTER_USUARIO;

    alter table cidade 
        drop 
        foreign key FK_EST_CIDADE;

    alter table credito 
        drop 
        foreign key FK_CRE_USUARIO;

    alter table debito 
        drop 
        foreign key FK_DE_CARTAO;

    alter table debito 
        drop 
        foreign key FK_DE_USUARIO;

    alter table descricao 
        drop 
        foreign key FK_TP_USUARIO;

    alter table descricao 
        drop 
        foreign key FK_TP_CONTA;

    alter table estado 
        drop 
        foreign key FK_PAIS_ESTADO;

    alter table fechamento 
        drop 
        foreign key FK_FE_USUARIO;

    alter table parametros 
        drop 
        foreign key FK_P_MOEDA;

    alter table parametros 
        drop 
        foreign key FK_P_FECHAMENTO;

    alter table parametros 
        drop 
        foreign key FK_P_USUARIO;

    alter table parcela 
        drop 
        foreign key FK_PA_USUARIO;

    alter table registroAcesso 
        drop 
        foreign key FK_RA_USUARIO;

    alter table usuario 
        drop 
        foreign key FK_CI_USUARIO;

    alter table usuario 
        drop 
        foreign key FK_US_PARAMETRO;

    drop table if exists cartao;

    drop table if exists cidade;

    drop table if exists conta;

    drop table if exists credito;

    drop table if exists debito;

    drop table if exists descricao;

    drop table if exists estado;

    drop table if exists fechamento;

    drop table if exists moeda;

    drop table if exists pais;

    drop table if exists parametros;

    drop table if exists parcela;

    drop table if exists propaganda;

    drop table if exists registroAcesso;

    drop table if exists tipofechamento;

    drop table if exists usuario;

    create table cartao (
        codigo integer not null auto_increment,
        descricao varchar(255) not null,
        compra integer not null,
        vencimento integer not null,
        expira datetime not null,
        master_codigo integer,
        usuario_codigo integer,
        primary key (codigo)
    );

    create table cidade (
        codigo integer not null auto_increment,
        cidade varchar(255) not null,
        estado_codigo integer,
        primary key (codigo)
    );

    create table conta (
        codigo integer not null auto_increment,
        descricao varchar(255) not null,
        locale varchar(255),
        primary key (codigo)
    );

    create table credito (
        codigo integer not null auto_increment,
        aux integer,
        contabilizado bit,
        data datetime not null,
        descricao varchar(255) not null,
        grupo varchar(255),
        moeda varchar(255) not null,
        superGrupo varchar(255) not null,
        valor double precision not null,
        usuario_codigo integer,
        primary key (codigo)
    );

    create table debito (
        codigo integer not null auto_increment,
        contabilizado bit,
        data datetime not null,
        descricao varchar(255) not null,
        grupo varchar(255) not null,
        moeda varchar(255) not null,
        parcela_codigo integer,
        superGrupo varchar(255),
        tipo varchar(255),
        valor double precision not null,
        usuario_codigo integer,
        cartao_codigo integer,
        primary key (codigo)
    );

    create table descricao (
        codigo integer not null auto_increment,
        descricao varchar(255) not null,
        tipo_conta integer,
        usuario_codigo integer,
        primary key (codigo)
    );

    create table estado (
        codigo integer not null auto_increment,
        estado varchar(255) not null,
        pais_codigo integer,
        primary key (codigo)
    );

    create table fechamento (
        codigo integer not null auto_increment,
        data datetime not null,
        dataFinal datetime,
        dataInicial datetime,
        moeda varchar(255) not null,
        tipo varchar(255) not null,
        totalCredito double precision not null,
        totalDebito double precision not null,
        totalGeral double precision not null,
        usuario_codigo integer,
        primary key (codigo)
    );

    create table moeda (
        codigo integer not null auto_increment,
        moeda varchar(255) not null,
        sigla varchar(255) not null,
        primary key (codigo)
    );

    create table pais (
        codigo integer not null auto_increment,
        locale varchar(255),
        pais varchar(255) not null,
        primary key (codigo)
    );

    create table parametros (
        codigo integer not null auto_increment,
        tipofechamento_codigo integer,
        usuario_codigo integer,
        moeda_codigo integer,
        primary key (codigo)
    );

    create table parcela (
        codigo integer not null auto_increment,
        parcelas integer not null,
        usuario_codigo integer,
        primary key (codigo)
    );

    create table propaganda (
        codigo integer not null auto_increment,
        ativa bit not null,
        descricao varchar(255) not null,
        idioma varchar(255) not null,
        idioma_usuario varchar(255) not null,
        imagem blob,
        texto text not null,
        tipo varchar(255) not null,
        primary key (codigo)
    );

    create table registroAcesso (
        codigo integer not null auto_increment,
        dataLogin datetime,
        dataLogout datetime,
        ipUsuario varchar(255),
        localName varchar(255),
        locale varchar(255),
        usuario_codigo integer,
        primary key (codigo)
    );

    create table tipofechamento (
        codigo integer not null auto_increment,
        idioma varchar(255) not null,
        periodo integer not null,
        tipoFechamento varchar(255) not null,
        primary key (codigo)
    );

    create table usuario (
        codigo integer not null auto_increment,
        administrador bit,
        data_cadastro datetime,
        data_nascimento varchar(255) not null,
        email varchar(255) not null,
        excluido bit,
        idioma varchar(255),
        master_codigo integer,
        nome varchar(255) not null,
        palavra_secreta varchar(255),
        senha varchar(255) not null,
        usuario varchar(255) not null,
        parametro_codigo integer,
        cidade_codigo integer,
        primary key (codigo)
    );

    alter table cartao 
        add index FK_CA_USUARIO (usuario_codigo), 
        add constraint FK_CA_USUARIO 
        foreign key (usuario_codigo) 
        references usuario (codigo);

    alter table cartao 
        add index FK_CA_MASTER_USUARIO (master_codigo), 
        add constraint FK_CA_MASTER_USUARIO 
        foreign key (master_codigo) 
        references usuario (codigo);

    alter table cidade 
        add index FK_EST_CIDADE (estado_codigo), 
        add constraint FK_EST_CIDADE 
        foreign key (estado_codigo) 
        references estado (codigo);

    alter table credito 
        add index FK_CRE_USUARIO (usuario_codigo), 
        add constraint FK_CRE_USUARIO 
        foreign key (usuario_codigo) 
        references usuario (codigo);

    alter table debito 
        add index FK_DE_CARTAO (cartao_codigo), 
        add constraint FK_DE_CARTAO 
        foreign key (cartao_codigo) 
        references cartao (codigo);

    alter table debito 
        add index FK_DE_USUARIO (usuario_codigo), 
        add constraint FK_DE_USUARIO 
        foreign key (usuario_codigo) 
        references usuario (codigo);

    alter table descricao 
        add index FK_TP_USUARIO (usuario_codigo), 
        add constraint FK_TP_USUARIO 
        foreign key (usuario_codigo) 
        references usuario (codigo);

    alter table descricao 
        add index FK_TP_CONTA (tipo_conta), 
        add constraint FK_TP_CONTA 
        foreign key (tipo_conta) 
        references conta (codigo);

    alter table estado 
        add index FK_PAIS_ESTADO (pais_codigo), 
        add constraint FK_PAIS_ESTADO 
        foreign key (pais_codigo) 
        references pais (codigo);

    alter table fechamento 
        add index FK_FE_USUARIO (usuario_codigo), 
        add constraint FK_FE_USUARIO 
        foreign key (usuario_codigo) 
        references usuario (codigo);

    alter table parametros 
        add index FK_P_MOEDA (moeda_codigo), 
        add constraint FK_P_MOEDA 
        foreign key (moeda_codigo) 
        references moeda (codigo);

    alter table parametros 
        add index FK_P_FECHAMENTO (tipofechamento_codigo), 
        add constraint FK_P_FECHAMENTO 
        foreign key (tipofechamento_codigo) 
        references tipofechamento (codigo);

    alter table parametros 
        add index FK_P_USUARIO (usuario_codigo), 
        add constraint FK_P_USUARIO 
        foreign key (usuario_codigo) 
        references usuario (codigo);

    alter table parcela 
        add index FK_PA_USUARIO (usuario_codigo), 
        add constraint FK_PA_USUARIO 
        foreign key (usuario_codigo) 
        references usuario (codigo);

    alter table registroAcesso 
        add index FK_RA_USUARIO (usuario_codigo), 
        add constraint FK_RA_USUARIO 
        foreign key (usuario_codigo) 
        references usuario (codigo);

    alter table usuario 
        add index FK_CI_USUARIO (cidade_codigo), 
        add constraint FK_CI_USUARIO 
        foreign key (cidade_codigo) 
        references cidade (codigo);

    alter table usuario 
        add index FK_US_PARAMETRO (parametro_codigo), 
        add constraint FK_US_PARAMETRO 
        foreign key (parametro_codigo) 
        references parametros (codigo);
