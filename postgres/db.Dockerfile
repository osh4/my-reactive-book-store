FROM postgres:16.10-bookworm
COPY schema.sql /docker-entrypoint-initdb.d/
