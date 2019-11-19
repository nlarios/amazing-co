-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- grant all privileges on database amazing to amazing_user;

CREATE TABLE node (
  id SERIAL,
  name VARCHAR(15),
  parent_id INTEGER,
  root_id INTEGER NOT NULL,
  height INT NOT Null,
  PRIMARY KEY(id),
  CONSTRAINT fk_node_parent_id FOREIGN KEY (parent_id) REFERENCES node(id),
  CONSTRAINT fk_node_root_id FOREIGN KEY (parent_id) REFERENCES node(id)
);

CREATE TABLE node_hierarchy (
  ancestor_id INTEGER NOT NULL,
  descendant_id INTEGER NOT NULL,
  depth INT NOT NULL,
  PRIMARY KEY(ancestor_id, descendant_id),
  CONSTRAINT fk_hierarchy_node_ancestor_id FOREIGN KEY (ancestor_id) REFERENCES node(id),
  CONSTRAINT fk_hierarchy_node_descend_id FOREIGN KEY (descendant_id) REFERENCES node(id)
);

DO $$

DECLARE root INTEGER;
DECLARE first_a INTEGER;
DECLARE first_b INTEGER;
DECLARE second_a INTEGER;
DECLARE second_b INTEGER;
DECLARE third_a INTEGER;

BEGIN
-- add some node
-- root
INSERT INTO node VALUES (0, 'root', NULL,  0, 0) RETURNING id INTO root;
-- child node- height 1

INSERT INTO node (parent_id, name, root_id, height)
VALUES (root,'a', root, 1) RETURNING id INTO first_a;

INSERT INTO node (parent_id, name, root_id, height)
VALUES (root,'b', root, 1) RETURNING id INTO first_b;

INSERT INTO node (parent_id, name, root_id, height)
VALUES (first_a, 'c', root, 2) RETURNING id INTO second_a;

INSERT INTO node (parent_id, name, root_id, height)
VALUES (first_b, 'd', root, 2) RETURNING id INTO second_b;

INSERT INTO node (parent_id, name, root_id, height)
VALUES (second_a, 'e', root, 3) RETURNING id INTO third_a;

INSERT INTO node_hierarchy VALUES (root, root, 0);
INSERT INTO node_hierarchy VALUES (root, first_a, 1);
INSERT INTO node_hierarchy VALUES (root, first_b, 1);
INSERT INTO node_hierarchy VALUES (root, second_a, 2);
INSERT INTO node_hierarchy VALUES (root, second_b, 2);
INSERT INTO node_hierarchy VALUES (root, third_a, 3);

INSERT INTO node_hierarchy VALUES (first_a, first_a, 0);
INSERT INTO node_hierarchy VALUES (first_a, second_a, 1);
INSERT INTO node_hierarchy VALUES (first_a, third_a, 2);

INSERT INTO node_hierarchy VALUES (first_b, first_b, 0);
INSERT INTO node_hierarchy VALUES (first_b, second_b, 1);

INSERT INTO node_hierarchy VALUES (second_a, second_a, 0);
INSERT INTO node_hierarchy VALUES (second_a, third_a, 1);

INSERT INTO node_hierarchy VALUES (third_a, third_a, 0);


END $$