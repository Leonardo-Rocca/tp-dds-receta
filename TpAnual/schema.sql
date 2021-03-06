
    create table Accion (
        tipoAccion varchar(31) not null,
        idAccion bigint generated by default as identity (start with 1),
        name varchar(255),
        usuario_idUsuario bigint,
        primary key (idAccion)
    )

    create table CondicionPreexistente (
        tipoCondicion varchar(31) not null,
        id bigint generated by default as identity (start with 1),
        primary key (id)
    )

    create table Condimento (
        idCondimento bigint generated by default as identity (start with 1),
        cantidad integer not null,
        nombre varchar(255),
        primary key (idCondimento)
    )

    create table EjecutadorDeAcciones (
        idEjecutadorDeAcciones bigint generated by default as identity (start with 1),
        primary key (idEjecutadorDeAcciones)
    )

    create table Grupo (
        idGrupo bigint generated by default as identity (start with 1),
        nombre varchar(255),
        preferenciasAlimenticias varbinary(255),
        primary key (idGrupo)
    )

    create table Ingrediente (
        idIngrediente bigint generated by default as identity (start with 1),
        cantidad integer not null,
        nombre varchar(255),
        primary key (idIngrediente)
    )

    create table ListadorDeRecetas (
        idListadorDeRecetas bigint generated by default as identity (start with 1),
        primary key (idListadorDeRecetas)
    )

    create table Receta (
        idReceta bigint generated by default as identity (start with 1),
        calorias integer not null,
        dificultad varchar(255),
        ingredientesCaros varbinary(255),
        nombre varchar(255),
        preparacion varchar(255),
        temporada varchar(255),
        autor_idUsuario bigint,
        primary key (idReceta)
    )

    create table RepoConsultas (
        idRepoConsultas bigint generated by default as identity (start with 1),
        listador_idListadorDeRecetas bigint,
        primary key (idRepoConsultas)
    )

    create table Usuario (
        idUsuario bigint generated by default as identity (start with 1),
        altura double not null,
        comidasQueLeDisgustan varbinary(255),
        fechaDeNacimiento varbinary(255),
        nombre varchar(255),
        peso integer not null,
        preferenciasAlimenticias varbinary(255),
        rutina integer,
        sexo varchar(255),
        ejecutador_idEjecutadorDeAcciones bigint,
        listador_idListadorDeRecetas bigint,
        repoConsultas_idRepoConsultas bigint,
        primary key (idUsuario)
    )

    create table Usuario_CondicionPreexistente (
        Usuario_idUsuario bigint not null,
        condicionesPreexistentes2_id bigint not null
    )

    alter table Accion 
        add constraint FK_8lcy4qxq8vigtmob7lx5udr94 
        foreign key (usuario_idUsuario) 
        references Usuario

    alter table Receta 
        add constraint FK_4fkawnpkslh7dis3749e2gfsk 
        foreign key (autor_idUsuario) 
        references Usuario

    alter table RepoConsultas 
        add constraint FK_qxawei45hvt1tp7eqyua70pnb 
        foreign key (listador_idListadorDeRecetas) 
        references ListadorDeRecetas

    alter table Usuario 
        add constraint FK_6xn0s68t61si9ew2pr623pxf2 
        foreign key (ejecutador_idEjecutadorDeAcciones) 
        references EjecutadorDeAcciones

    alter table Usuario 
        add constraint FK_ei05k05t6sv9ao67vjj1ry0i9 
        foreign key (listador_idListadorDeRecetas) 
        references ListadorDeRecetas

    alter table Usuario 
        add constraint FK_fb8y3a3g7ckdrrqignajahh2p 
        foreign key (repoConsultas_idRepoConsultas) 
        references RepoConsultas

    alter table Usuario_CondicionPreexistente 
        add constraint FK_exyj5kchsmymecxchmc59s1pp 
        foreign key (condicionesPreexistentes2_id) 
        references CondicionPreexistente

    alter table Usuario_CondicionPreexistente 
        add constraint FK_q358tv0bfpkrlv3owimcl0v8e 
        foreign key (Usuario_idUsuario) 
        references Usuario
