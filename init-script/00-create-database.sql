create database entityone;
CREATE USER entityone IDENTIFIED BY 'entityone';
GRANT ALL PRIVILEGES ON entityone.* TO entityone;
FLUSH PRIVILEGES;

create database entitytwo;
CREATE USER entitytwo IDENTIFIED BY 'entitytwo';
GRANT ALL PRIVILEGES ON entitytwo.* TO entitytwo;
FLUSH PRIVILEGES;

create database entitythree;
CREATE USER entitythree IDENTIFIED BY 'entitythree';
GRANT ALL PRIVILEGES ON entitythree.* TO entitythree;
FLUSH PRIVILEGES;