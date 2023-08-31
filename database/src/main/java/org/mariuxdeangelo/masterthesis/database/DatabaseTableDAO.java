package org.mariuxdeangelo.masterthesis.database;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.ConsumerModel;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SubjectProjectModel;
import org.mariuxdeangelo.masterthesis.database.rowMapperDatabase.ConsumerDatabaseRowMapper;
import org.mariuxdeangelo.masterthesis.database.rowMapperDatabase.SbomFilesDatabaseRowMapper;
import org.mariuxdeangelo.masterthesis.database.rowMapperDatabase.SubjectProjectsDatabaseRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseTableDAO extends AbstractPostgresDAO{

    public List<SubjectProjectModel> retrieveListOfSubjectProjects() {
        String sql = "SELECT * FROM subject_projects";
        return jdbcTemplate.query(sql, new SubjectProjectsDatabaseRowMapper());
    }

    public List<SubjectProjectModel> retrieveListOfSubjectProjects(long id) {
        String sql = "SELECT * FROM subject_projects WHERE project_id = ?";
        return jdbcTemplate.query(sql, new SubjectProjectsDatabaseRowMapper(), id);
    }

    public Integer insertSubjectProject(SubjectProjectModel model) {
        String sql = "INSERT INTO subject_projects (name, container, git) VALUES (?, ?, ?) RETURNING project_id";
        return jdbcTemplate.queryForObject(sql, Integer.class, model.getName(), model.getContainer(), model.getGit());
    }

    public boolean deleteSubjectProject(long id) {
        String sql = "DELETE FROM subject_projects WHERE project_Id = ?";
        int update = jdbcTemplate.update(sql, id);
        return update > 0;
    }

    public List<SbomFilesModel> retrieveSbomFile() {
        String sql = "SELECT * FROM sbom_files";
        return jdbcTemplate.query(sql, new SbomFilesDatabaseRowMapper());
    }

    public List<SbomFilesModel> retrieveListOfSbomFilesLite() {
        String sql = "SELECT sbom_file_id, project_id, generator, mode, timestamp, spdx_orig, cdx_orig FROM sbom_files";
        return jdbcTemplate.query(sql, new SbomFilesDatabaseRowMapper());
    }

    public SbomFilesModel retrieveSbomFile(long id) {
        String sql = "SELECT * FROM sbom_files WHERE sbom_file_id = ?";
        return jdbcTemplate.queryForObject(sql, new SbomFilesDatabaseRowMapper(), id);
    }

    public Integer insertSbomFile(SbomFilesModel model) {
        String sql = "INSERT INTO sbom_files (project_id, generator, mode, timestamp, spdx, spdx_orig, cdx, cdx_orig, command, shell_out, execution_time) VALUES (?, ?, ?, ?, ?::json, ?, ?::json, ?, ?, ?, ?) " +
                     "ON CONFLICT ON CONSTRAINT unique_sbom_combination DO UPDATE " +
                     "SET timestamp = ?, spdx = ?::json, spdx_orig = ?, cdx = ?::json, cdx_orig = ?, command = ?, shell_out = ?, execution_time = ? " +
                     "RETURNING sbom_file_id";
        if (model.getSpdx() != null && model.getSpdx().isBlank()) model.setSpdx(null);
        if (model.getCdx() != null && model.getCdx().isBlank()) model.setCdx(null);
        return jdbcTemplate.queryForObject(sql, Integer.class, model.getProjectId(), model.getGenerator(), model.getMode(),
               model.getTimestamp(), model.getSpdx(), model.isOrig_spdx(), model.getCdx(), model.isOrig_cdx(), model.getCommand(), model.getShellOutput(), model.getExecutionTime(),
               model.getTimestamp(), model.getSpdx(), model.isOrig_spdx(), model.getCdx(), model.isOrig_cdx(), model.getCommand(), model.getShellOutput(), model.getExecutionTime());
    }

    public boolean deleteSbomFile(long id) {
        String sql = "DELETE FROM sbom_files WHERE sbom_file_id = ?";
        int update = jdbcTemplate.update(sql, id);
        return update > 0;
    }

    public List<ConsumerModel> retrieveConsumerList() {
        String sql = "SELECT consumer_id, sbom_file_id, consumer, type, timestamp FROM sbom_consumer";
        return jdbcTemplate.query(sql, new ConsumerDatabaseRowMapper());
    }

    public List<ConsumerModel> retrieveConsumerListLite() {
        String sql = "SELECT * FROM sbom_consumer";
        return jdbcTemplate.query(sql, new ConsumerDatabaseRowMapper());
    }

    public ConsumerModel retrieveConsumer(long id) {
        String sql = "SELECT * FROM sbom_consumer WHERE consumer_id = ?";
        return jdbcTemplate.queryForObject(sql, new ConsumerDatabaseRowMapper(), id);
    }

    public List<ConsumerModel> retrieveConsumerListBySbomId(long id) {
        String sql = "SELECT * FROM sbom_consumer WHERE sbom_file_id = ?";
        return jdbcTemplate.query(sql, new ConsumerDatabaseRowMapper(), id);
    }

    public Integer insertCunsumer(ConsumerModel model) {
        String sql = "INSERT INTO sbom_consumer (sbom_file_id, consumer, type, timestamp, report) " +
                     "VALUES (?, ?, ?, ?, ?::json) ON CONFLICT ON CONSTRAINT unique_consumer_combination DO UPDATE " +
                     "SET timestamp = ?, report = ?::json RETURNING consumer_id";
        return jdbcTemplate.queryForObject(sql, Integer.class, model.getSbomFileId(), model.getConsumer(),
                model.getType(), model.getTimestamp(), model.getReport(), model.getTimestamp(), model.getReport());
    }

    public boolean deleteConsumer(long id) {
        String sql = "DELETE FROM sbom_consumer WHERE consumer_id = ?";
        int update = jdbcTemplate.update(sql, id);
        return update > 0;
    }
}