-- Create the device_location table
CREATE TABLE device_location (
                                 id UUID PRIMARY KEY NOT NULL,
                                 device_id VARCHAR(20) NOT NULL,
                                 location_timestamp BIGINT NOT NULL,
                                 latitude DOUBLE PRECISION NOT NULL,
                                 longitude DOUBLE PRECISION NOT NULL,
                                 speed DOUBLE PRECISION NOT NULL,
                                 bearing DOUBLE PRECISION NOT NULL,
                                 altitude DOUBLE PRECISION NOT NULL,
                                 accuracy DOUBLE PRECISION NOT NULL,
                                 battery DOUBLE PRECISION NOT NULL,
                                 created_date TIMESTAMP NOT NULL,
                                 last_modified_date TIMESTAMP NOT NULL
);

-- Create index on device_id and location_timestamp
CREATE INDEX idx_device_id_timestamp
    ON device_location (device_id, location_timestamp);

-- Insert sample data
INSERT INTO device_location (
    id, device_id, location_timestamp, latitude, longitude, speed, bearing, altitude,
    accuracy, battery, created_date, last_modified_date
) VALUES (
             '11111111-1111-1111-1111-111111111111', 'DEVICE001', 1717459200000, 37.7749, -122.4194, 15.0, 180.0, 10.0,
             5.0, 75.0, '2025-06-01 10:00:00', '2025-06-01 10:05:00'
         );

INSERT INTO device_location (
    id, device_id, location_timestamp, latitude, longitude, speed, bearing, altitude,
    accuracy, battery, created_date, last_modified_date
) VALUES (
             '22222222-2222-2222-2222-222222222222', 'DEVICE002', 1717462800000, 40.7128, -74.0060, 20.0, 90.0, 12.0,
             4.0, 60.0, '2025-06-01 11:00:00', '2025-06-01 11:05:00'
         );


-- Create the participant table
CREATE TABLE participant (
                             id UUID PRIMARY KEY NOT NULL,
                             device_id VARCHAR(20) NOT NULL,
                             name VARCHAR(20) NOT NULL,
                             vehicle VARCHAR(4) NOT NULL,
                             role VARCHAR(5) NOT NULL,
                             created_date TIMESTAMP NOT NULL,
                             last_modified_date TIMESTAMP NOT NULL
);

-- Create index on device_id
CREATE INDEX idx_participant_device_id
    ON participant (device_id);

-- Insert sample data
INSERT INTO participant (
    id, device_id, name, vehicle, role, created_date, last_modified_date
) VALUES (
             '33333333-3333-3333-3333-333333333333', 'DEVICE001', 'Alice', 'CAR', 'DRVR',
             '2025-06-01 09:00:00', '2025-06-01 09:15:00'
         );

INSERT INTO participant (
    id, device_id, name, vehicle, role, created_date, last_modified_date
) VALUES (
             '44444444-4444-4444-4444-444444444444', 'DEVICE002', 'Bob', 'BIKE', 'PASS',
             '2025-06-01 09:30:00', '2025-06-01 09:45:00'
         );
