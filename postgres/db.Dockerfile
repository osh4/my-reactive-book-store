FROM postgres:16.11-bookworm
COPY schema.sql /docker-entrypoint-initdb.d/
