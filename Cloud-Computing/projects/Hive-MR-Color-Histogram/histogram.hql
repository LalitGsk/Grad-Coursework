drop table pixels;
drop table rgb;

create table pixels (
  RedPixel int,
  GreenPixel int,
  BluePixel int)
row format delimited fields terminated by ',' stored as textfile;

load data local inpath 'pixels-large.txt' overwrite into table pixels;

CREATE TABLE rgb(colorType int, colorIntensity int, intensityCount int);

INSERT INTO TABLE rgb 
SELECT 1, RedPixel, COUNT(1) FROM pixels GROUP BY RedPixel
UNION ALL
SELECT 2, GreenPixel, COUNT(1) FROM pixels GROUP BY GreenPixel
UNION ALL
SELECT 3, BluePixel, COUNT(1) FROM pixels GROUP BY BluePixel;

SELECT * FROM rgb ORDER BY colorType, colorIntensity;
