  CREATE TABLE respuestas (
      id bigint not null auto_increment,
      mensaje varchar(255) not null,
      topico bigint not null,
      fecha_creacion datetime not null,
      autor bigint not null,
      solucion tinyint not null,

      primary key(id)
  );