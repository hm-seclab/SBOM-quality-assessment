import org.junit.jupiter.api.Test;
import org.mariuxdeangelo.masterthesis.database.DatabaseTableDAO;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.ConsumerModel;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SubjectProjectModel;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class TestDatabaseTableDAO {

    DatabaseTableDAO dao = new DatabaseTableDAO();

    @Test
    public void testSubjectProjects() {
        // Insert Project
        SubjectProjectModel project = new SubjectProjectModel();
        project.setContainer("TestContainer");
        project.setName("TestName");
        project.setGit("https://test.git/");

        Integer projectId = dao.insertSubjectProject(project);
        assert(projectId != null);

        List<SubjectProjectModel> subjectProjectModels = dao.retrieveListOfSubjectProjects();
        assert(!subjectProjectModels.isEmpty());

        // Insert SBOM
        SbomFilesModel sbomFile = new SbomFilesModel();
        sbomFile.setProjectId(projectId);
        sbomFile.setGenerator("test");
        sbomFile.setMode("test");
        sbomFile.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        sbomFile.setSpdx("{\"key1\": \"value3\", \"key2\": \"value4\"}");
        sbomFile.setCdx("{\"key1\": \"value3\", \"key2\": \"value4\"}");
        sbomFile.setOrig_spdx(true);
        sbomFile.setOrig_cdx(false);

        Integer sbomId = dao.insertSbomFile(sbomFile);
        assert(sbomId != null);

        List<SbomFilesModel> sbomFileList = dao.retrieveSbomFile();
        assert(!sbomFileList.isEmpty());

        // Insert Consumer
        ConsumerModel consumer = new ConsumerModel();
        consumer.setSbomFileId(sbomId);
        consumer.setConsumer("test");
        consumer.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        consumer.setReport("{\"key1\": \"value3\", \"key2\": \"value4\"}");

        Integer consumerId = dao.insertCunsumer(consumer);
        assert (consumerId != null);

        // Check if all exists
        assert(!dao.retrieveListOfSubjectProjects(projectId).isEmpty());
        assert(dao.retrieveSbomFile(sbomId) != null);
        assert(dao.retrieveConsumer(consumerId) != null);

        // Delete with cascade
        boolean delete = dao.deleteSubjectProject(projectId);
        assert(delete);

        // Check taht all got deleted
        assert(dao.retrieveListOfSubjectProjects(projectId).isEmpty());
        assert(dao.retrieveSbomFile(sbomId) != null);
        assert(dao.retrieveConsumer(consumerId) == null);
    }
}
