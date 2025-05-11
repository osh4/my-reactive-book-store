FROM postgres:16.9-bookworm
COPY schema.sql /docker-entrypoint-initdb.d/
