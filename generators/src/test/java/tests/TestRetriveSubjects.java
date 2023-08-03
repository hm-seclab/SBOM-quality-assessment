package tests;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHRelease;
import org.mariuxdeangelo.masterthesis.generators.RetrieveSubjects;
import tests.generator.TestAbstractGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestRetriveSubjects extends TestAbstractGenerator {

    @Test
    public void testRetrieveContainer() throws IOException {
        RetrieveSubjects.retrieveContainer(getTestContainerName());
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        List<Image> imageList = dockerClient.listImagesCmd().exec();
        List<Image> filteredImageList = imageList.stream().filter(
                image -> Arrays.asList(image.getRepoTags()).contains(getTestContainerName())).collect(Collectors.toList());
        assert(!filteredImageList.isEmpty());
        for (Image image : filteredImageList) {
            dockerClient.removeImageCmd(image.getId()).exec();
        }
        dockerClient.close();
    }

    @Test
    public void testRetrieveSources() throws IOException {
        Path projectPath = RetrieveSubjects.retrieveSources(getTestGitUrl(), getTestGitName());
        File[] fileList = projectPath.toFile().listFiles();

        assert(fileList.length > 0);
        FileUtils.deleteDirectory(projectPath.toFile());
    }

    @Test
    public void testRetrieverelease() throws IOException {
        GHRelease release = RetrieveSubjects.isReleaseAvailible(getTestGitUrl(), getTestGitName());
        Path releasePath = RetrieveSubjects.retrieveRelease(release, getTestGitName());
        File[] fileList = releasePath.toFile().listFiles();

        assert(fileList.length > 0);
        FileUtils.deleteDirectory(releasePath.toFile());
    }
}
