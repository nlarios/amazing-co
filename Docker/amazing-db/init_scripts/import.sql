CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- grant all privileges on database amazing to amazing_user;

CREATE TABLE node (
  id uuid DEFAULT uuid_generate_v4(),
  name VARCHAR(15),
  parent_id uuid,
  root_id uuid NOT NULL,
  height INT NOt Null,
  PRIMARY KEY(id),
  CONSTRAINT fk_node_parent_id FOREIGN KEY (parent_id) REFERENCES node(id),
  CONSTRAINT fk_node_root_id FOREIGN KEY (parent_id) REFERENCES node(id)
);

CREATE TABLE node_hierarchy (
  ancestor_id uuid NOT NULL,
  descendant_id uuid NOT NULL,
  depth INT NOT NULL,
  PRIMARY KEY(ancestor_id, descendant_id),
  CONSTRAINT fk_hierarchy_node_ancestor_id FOREIGN KEY (ancestor_id) REFERENCES node(id),
  CONSTRAINT fk_hierarchy_node_descend_id FOREIGN KEY (descendant_id) REFERENCES node(id)
);

DO $$

DECLARE root uuid;
DECLARE first_a uuid;
DECLARE first_b uuid;
DECLARE second_a uuid;
DECLARE second_b uuid;
DECLARE third_a uuid;

BEGIN
-- add some node
-- root
INSERT INTO node VALUES ('32c28dac-47ff-47cc-a8e2-b26d89333985', 'root',NULL,  '32c28dac-47ff-47cc-a8e2-b26d89333985', 0) RETURNING id INTO root;
-- child node- height 1

INSERT INTO node (parent_id, name, root_id, height)
VALUES (root,'a', root, 1) RETURNING id INTO first_a;

INSERT INTO node (parent_id, name, root_id, height)
VALUES (first_a,'b', root, 1) RETURNING id INTO first_b;

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