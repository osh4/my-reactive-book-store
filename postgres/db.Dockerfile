FROM postgres:16.8-bookworm
COPY schema.sql /docker-entrypoint-initdb.d/
