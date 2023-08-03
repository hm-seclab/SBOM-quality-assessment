package org.mariuxdeangelo.masterthesis.generators;

import org.kohsuke.github.GHAsset;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class RetrieveSubjects {

    public static boolean retrieveContainer(String container) {
        if (container == null) return false;

        try {
            ProcessBuilder builder = new ProcessBuilder(
                "docker", "pull", container);
            Process process = builder.start();
            return process.waitFor(10, TimeUnit.MINUTES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path retrieveSources(String url, String project) {
        if (url == null) return null;

        Path projectPath = GeneratorAppConfig.getBaseDir().resolve("sources").resolve(project);
        projectPath.toFile().mkdirs();
        // if project already exists it will not be downloaded again.
        if (projectPath.toFile().listFiles().length != 0) return projectPath;

        // Repository already exists.
        if (projectPath.toFile().listFiles().length > 0) {
            return projectPath;
        }

        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "git", "clone", url + ".git", projectPath.toString());
            Process process = builder.start();
            process.waitFor(10, TimeUnit.MINUTES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return projectPath;
    }

    public static GHRelease isReleaseAvailible(String url, String name) {
        if (url == null) return null;
        Path outputPath = GeneratorAppConfig.getBaseDir().resolve("releases").resolve(name);
        outputPath.toFile().mkdirs();
        // if project already exists it will not be downloaded again.

        String[] split = url.split("/");
        String owner = split[split.length -2];
        String repo = split[split.length -1];

        try {
            GitHub gitHub;
            if (GeneratorAppConfig.getGitToken().isBlank()) {
                gitHub = GitHub.connectAnonymously();
            } else {
                gitHub = GitHub.connectUsingOAuth(GeneratorAppConfig.getGitToken());
            }
            GHRepository repository = gitHub.getRepository(owner + "/" + repo);
            return repository.getLatestRelease();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Path retrieveRelease(GHRelease release, String name) {
        Path outputPath = GeneratorAppConfig.getBaseDir().resolve("releases").resolve(name);
        if (release == null) return outputPath;
        if (outputPath.toFile().listFiles().length != 0) return outputPath;

        try {
            for (GHAsset asset : release.getAssets()) {
                    StaticHelper.downloadFile(asset.getBrowserDownloadUrl(), outputPath.resolve(asset.getName()).toFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputPath;
    }

}
