CREATE TABLE app.user (
  id            SERIAL PRIMARY KEY,
  username      VARCHAR(150) NOT NULL UNIQUE,
  email         VARCHAR(100) NOT NULL UNIQUE,
  phone         VARCHAR(13),
  password      VARCHAR(100) NOT NULL,
  active        BOOLEAN DEFAULT FALSE,
  role          VARCHAR(100),
  register_date DATE,
  birth_date    DATE,
  name          VARCHAR(100),
  surname       VARCHAR(100)
);

CREATE TABLE app.tag (
  id    SERIAL UNIQUE,
  lang  VARCHAR(2),
  value VARCHAR(100) NOT NULL,
  PRIMARY KEY (id, lang)
);

CREATE TABLE app.category (
  id        SERIAL PRIMARY KEY,
  parent_id INTEGER DEFAULT 0,
  alias     VARCHAR(255),
  created   DATE,
  active    BOOLEAN DEFAULT FALSE
);

CREATE TABLE app.category_translate (
  category_id      INTEGER      NOT NULL REFERENCES app.category (id) ON DELETE CASCADE,
  lang             VARCHAR(2)   NOT NULL,
  name             VARCHAR(255) NOT NULL,
  intro_text       TEXT,
  meta_title       VARCHAR(255),
  meta_description VARCHAR(255),
  meta_keywords    VARCHAR(255),
  PRIMARY KEY (category_id, lang)
);

CREATE TABLE app.content_type (
  id                SERIAL PRIMARY KEY,
  name              VARCHAR(100),
  extra_field_types JSONB
);

CREATE TABLE app.content (
  id              SERIAL PRIMARY KEY,
  content_type_id INTEGER REFERENCES app.content_type (id) ON DELETE SET NULL,
  category_id     INTEGER REFERENCES app.category (id) ON DELETE SET NULL,
  alias           VARCHAR(255),
  created         DATE,
  created_by      INTEGER REFERENCES app.user (id) ON DELETE SET NULL,
  last_modified   DATE,
  publish_up      TIMESTAMP,
  publish_down    TIMESTAMP,
  hits            INTEGER,
  active          BOOLEAN DEFAULT FALSE,
  extra_fields    JSONB,
  image           VARCHAR(255)
);

CREATE TABLE app.content_translate (
  content_id       INTEGER      NOT NULL REFERENCES app.content (id) ON DELETE CASCADE,
  lang             VARCHAR(2)   NOT NULL,
  name             VARCHAR(255) NOT NULL,
  intro_text       TEXT,
  full_text        TEXT,
  meta_title       VARCHAR(255),
  meta_description VARCHAR(255),
  meta_keywords    VARCHAR(255),
  PRIMARY KEY (content_id, lang)
);

CREATE TABLE app.content_tag (
  content_id INTEGER NOT NULL REFERENCES app.content (id) ON DELETE CASCADE,
  tags_id    INTEGER NOT NULL REFERENCES app.tag (id) ON DELETE CASCADE,
  PRIMARY KEY (content_id, tags_id)
);

CREATE TABLE app.comment (
  id         SERIAL PRIMARY KEY,
  content_id INTEGER REFERENCES app.content (id) ON DELETE CASCADE,
  user_id    INTEGER REFERENCES app.user (id) ON DELETE SET NULL,
  created    TIMESTAMP NOT NULL,
  full_text  TEXT      NOT NULL,
  status     VARCHAR(100)
);

CREATE TABLE app.permissions (

)