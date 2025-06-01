INSERT INTO route(id, route_name, elevation_gain, estimated_average_speed, distance_in_meters, duration_in_minutes, estimated_start_time, estimated_end_time,
                  pause_in_minutes, start_time, average_speed, created_date, last_modified_date)
VALUES('e0483c47-0aa0-442d-808b-8897687f4af2', 'route-name 1', 1200, 24.4, 12000, 180,
       '2025-05-20 12:00:00', '2025-05-20 14:00:00',5, '2025-05-20 12:05:00', 35.1, '2025-05-19 12:00:00', '2025-05-19 13:02:00');

INSERT INTO route_point(id, route_id, latitude, longitude, altitude, distance_from_start_in_meter, created_date, last_modified_date) VALUES('59328972-c24c-4cce-a62b-e98120f7ae8d', 'e0483c47-0aa0-442d-808b-8897687f4af2', 34.4, 55.9, 90.5, 0, '2025-05-20', '2025-05-20');


INSERT INTO route(id, route_name, elevation_gain, estimated_average_speed, distance_in_meters, duration_in_minutes, estimated_start_time, estimated_end_time,
                  pause_in_minutes, created_date, last_modified_date)
VALUES('f654bfa7-1824-4123-be0b-a93993a21d6d', 'route-name 3', 1200, 24.4, 12000, 180,
       '2025-05-20 12:00:00', '2125-05-20 14:00:00',5, '2025-05-19 12:00:00', '2025-05-19 13:02:00');

INSERT INTO route(id, route_name, elevation_gain, estimated_average_speed, distance_in_meters, duration_in_minutes, estimated_start_time, estimated_end_time,
                  pause_in_minutes, created_date, last_modified_date)
VALUES('cc335f92-d2e8-483c-b0ae-aa7e75677353', 'route-name 4', 2000, 21.4, 1000, 120,
       '2025-05-20 12:00:00', '2025-05-20 14:00:00',50, '2025-05-19 12:00:00', '2025-05-19 12:00:00');
