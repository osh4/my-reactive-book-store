FROM postgres:16.3-bookworm
COPY schema.sql /docker-entrypoint-initdb.d/
