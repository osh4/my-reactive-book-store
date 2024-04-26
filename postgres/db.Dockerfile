FROM postgres:16-bookworm
COPY schema.sql /docker-entrypoint-initdb.d/
