CREATE TABLE IF NOT EXISTS subject_projects(
    project_id SERIAL PRIMARY Key,
    name       VARCHAR(255),
    container  VARCHAR(255),
    git        VARCHAR(1023)
);

CREATE TABLE IF NOT EXISTS sbom_files(
    sbom_file_id   SERIAL PRIMARY KEY,
    project_id     INT REFERENCES subject_projects(project_id) ON DELETE CASCADE,
    generator      VARCHAR(255),
    mode           VARCHAR(255),
    timestamp      TIMESTAMP,
    command        VARCHAR(1024),
    spdx           JSONB,
    spdx_orig      boolean,
    cdx            JSONB,
    cdx_orig       boolean,
    shell_out      BYTEA,
    execution_time BIGINT,
    CONSTRAINT unique_sbom_combination UNIQUE (generator, project_id, mode)
);

CREATE TABLE IF NOT EXISTS sbom_consumer(
    consumer_id    SERIAL PRIMARY KEY,
    sbom_file_id   INT REFERENCES sbom_files(sbom_file_id) ON DELETE CASCADE,
    consumer       VARCHAR(255),
    type           VARCHAR(255),
    timestamp      TIMESTAMP,
    command        VARCHAR(1024),
    report         JSONB,
    shell_out      BYTEA,
    execution_time BIGINT,
    CONSTRAINT unique_consumer_combination UNIQUE (sbom_file_id, type, consumer)
);

CREATE TABLE IF NOT EXISTS api_usage(
    timestamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    endpoint    VARCHAR(255)
)


