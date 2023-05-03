FROM postgres:15-bullseye
COPY schema.sql /docker-entrypoint-initdb.d/
