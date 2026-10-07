CREATE USER 'retail_app'@'localhost'
IDENTIFIED BY 'root@123';

GRANT SELECT, INSERT, UPDATE, DELETE
ON retail_shop.*
TO 'retail_app'@'localhost';