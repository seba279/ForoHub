  CREATE TABLE cursos (
      id bigint not null auto_increment,
      nombre varchar(150) not null unique,
      categoria varchar(50) not null,

      primary key(id)
  );