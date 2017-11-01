DROP DATABASE IF EXISTS labtest;

CREATE DATABASE labtest;

CREATE TABLE IF NOT EXISTS laborder (Insertts TIMESTAMP, ordernr STRING, Patientnr STRING, visitnr STRING) ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "\""
) ;
LOAD DATA INPATH '${INPUT}/laborder.csv' OVERWRITE INTO TABLE laborder;

CREATE TABLE IF NOT EXISTS labanalyse (resultaat STRING, deleted BOOLEAN,  analysecodePk INT, Inserttimestamp TIMESTAMP,
specimennr STRING, uitvoeringsts TIMESTAMP
) ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "\""
) ;
LOAD DATA INPATH '${INPUT}/labresult.csv' OVERWRITE INTO TABLE laborder;

CREATE TABLE IF NOT EXISTS labspecimen (specimennr STRING, ordernr STRING) ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "\""
) ;
LOAD DATA INPATH '${INPUT}/labspecimen.csv' OVERWRITE INTO TABLE labspecimen;


CREATE TABLE IF NOT EXISTS labanalysecode (pk INT, analysecode STRING, analysecodeversion STRING, view INT, referencevalue STRING, unit STRING) ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "\""
) ;
LOAD DATA INPATH '${INPUT}/labordercodes.csv' OVERWRITE INTO TABLE labanalysecode;


SELECT * FROM laborder 
INNER JOIN labspecimen ON labspecimen.ordernr = laborder.ordernr
INNER JOIN labanalyse ON labanalyse.specimennr = labspecimen.specimennr
INNER JOIN labanalysecode ON labanalysecode.pk = labanalyse.analysecodepk;
