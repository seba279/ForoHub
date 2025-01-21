  CREATE TABLE topicos (
      id bigint not null auto_increment,
      titulo varchar(100) not null unique,
      mensaje varchar(255) not null unique,
      fecha_creacion datetime not null,
      estado varchar(50) not null,
      autor bigint not null,
      curso bigint not null,

      primary key(id)
  );