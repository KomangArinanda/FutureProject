--dijalanin di flyway di application properties--
CREATE TABLE IF NOT EXISTS role (
id CHAR (36) NOT NULL PRIMARY KEY,
nama_role VARCHAR (255) NOT NULL
);

CREATE TABLE IF NOT EXISTS pegawai (
id CHAR (36) NOT NULL PRIMARY KEY,
pegawai_id SERIAL NOT NULL,
password VARCHAR (255) NOT NULL,
nama_pegawai VARCHAR (255) NOT NULL,
email VARCHAR (255) NOT NULL,
role_id CHAR (36) NOT NULL,
CONSTRAINT UK_PEGAWAI_ID UNIQUE(pegawai_id),
CONSTRAINT UK_EMAIL UNIQUE(email),
CONSTRAINT FK_PEGAWAI_ROLE FOREIGN KEY(role_id) REFERENCES role(id)
);
