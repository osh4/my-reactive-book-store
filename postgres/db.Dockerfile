FROM postgres:16.12-bookworm
COPY schema.sql /docker-entrypoint-initdb.d/
