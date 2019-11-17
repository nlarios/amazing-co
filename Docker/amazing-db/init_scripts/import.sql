CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE Node (
    id UUID NOT NULL,
    reference_id UUID NOT NULL,
    code VARCHAR(255) NOT NULL,
    CONSTRAINT tenant_pkey PRIMARY KEY (id)
);

CREATE TABLE site (
    id uuid NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    location public.geometry(Polygon,4326),
    site uuid,
    tenant uuid NOT NULL,
    CONSTRAINT site_pkey PRIMARY KEY (id),
    CONSTRAINT fk_site_site_site_id FOREIGN KEY (site) REFERENCES site(id),
    CONSTRAINT fk_site_tenant_tenant_id FOREIGN KEY (tenant) REFERENCES tenant(id)
);




-- Insert test data
INSERT INTO tenant VALUES ('c7c27dfa-9342-11e9-bc42-526af7764f64', 'a80ad614-e798-4e2c-a72f-5aaed6ef828f', 'kapae');

INSERT INTO site VALUES ('d57df58e-b79b-408e-a728-cc2f37918abf', 'Site 1', 'Site 1 description', ST_GeometryFromText('POLYGON((23.621978759765625 37.97451499202461,23.75518798828125 38.072960376834345,23.95156860351563 38.078365629967124,24.002380371093754 37.84341033205655,23.82797241210938 37.83364941345965,23.621978759765625 37.97451499202461))', 4326), null, 'c7c27dfa-9342-11e9-bc42-526af7764f64');

INSERT INTO sensor VALUES ('523bdc6c-d7d7-4ed4-97ef-6652fbfc47d9', 'drone', null, 'Drone', 'Drone description', ST_GeometryFromText('POINT(23.742249011993408 38.020136801261756)', 4326), null, 'd57df58e-b79b-408e-a728-cc2f37918abf', 'c7c27dfa-9342-11e9-bc42-526af7764f64');
INSERT INTO sensor VALUES ('04960342-ae81-43d1-81c3-3e61d938a288', 'drone-ir', null, 'IR Radiation', 'IR Radiation description', ST_GeometryFromText('POINT(23.742249011993408 38.020136801261756)', 4326), '523bdc6c-d7d7-4ed4-97ef-6652fbfc47d9', 'd57df58e-b79b-408e-a728-cc2f37918abf', 'c7c27dfa-9342-11e9-bc42-526af7764f64');
INSERT INTO sensor VALUES ('dd2ef677-22c6-45c9-a393-8221bc5c2e6f', 'drone-te', null, 'Relative Humidity', 'Relative Humidity description', ST_GeometryFromText('POINT(23.742249011993408 38.020136801261756)', 4326), '523bdc6c-d7d7-4ed4-97ef-6652fbfc47d9', 'd57df58e-b79b-408e-a728-cc2f37918abf', 'c7c27dfa-9342-11e9-bc42-526af7764f64');
INSERT INTO sensor VALUES ('d9a4955a-c5fd-47d0-a693-28ec07b59f7d', 'drone-rh', null, 'Temperature External', 'Temperature External description', ST_GeometryFromText('POINT(23.742249011993408 38.020136801261756)', 4326), '523bdc6c-d7d7-4ed4-97ef-6652fbfc47d9', 'd57df58e-b79b-408e-a728-cc2f37918abf', 'c7c27dfa-9342-11e9-bc42-526af7764f64');

INSERT INTO sensor VALUES ('f329c000-c5ca-4759-8872-fdac6d7cf54c', 'station', '34404', 'Station', 'Station sensor', ST_GeometryFromText('POINT(23.742249011993408 38.020136801261756)', 4326), null, 'd57df58e-b79b-408e-a728-cc2f37918abf', 'c7c27dfa-9342-11e9-bc42-526af7764f64');