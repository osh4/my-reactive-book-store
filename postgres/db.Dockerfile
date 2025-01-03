FROM postgres:16.4-bookworm
COPY schema.sql /docker-entrypoint-initdb.d/
