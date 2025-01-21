  CREATE TABLE usuarios (
      id bigint not null auto_increment,
      nombre varchar(100) not null unique,
      email varchar(100) not null unique,
      clave varchar(150) not null,
      perfiles bigint not null,

      primary key(id)
  );