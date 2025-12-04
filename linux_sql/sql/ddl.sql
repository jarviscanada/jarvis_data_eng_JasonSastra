\c host_agent;
CREATE TABLE IF NOT EXISTS public.host_info
(
    id               SERIAL PRIMARY KEY,
    hostname         VARCHAR NOT NULL UNIQUE,
    cpu_number       SMALLINT NOT NULL,
    cpu_architecture VARCHAR NOT NULL,
    cpu_model        VARCHAR NOT NULL,
    cpu_mhz          DOUBLE PRECISION NOT NULL,
    l2_cache         INT NOT NULL,
    "timestamp"      TIMESTAMP,
    total_mem        INT
);
CREATE TABLE IF NOT EXISTS public.host_usage
(
    "timestamp"      TIMESTAMP NOT NULL,
    host_id          INT NOT NULL REFERENCES public.host_info(id),
    memory_free      INT NOT NULL,
    cpu_idle         SMALLINT NOT NULL,
    cpu_kernel       SMALLINT NOT NULL,
    disk_io          INT NOT NULL,
    disk_available   INT NOT NULL
);