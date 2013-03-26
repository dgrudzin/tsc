package org.tsc;

import com.sun.tools.javadoc.Main;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * @author Dmitry Grudzinskiy
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class TscMojo extends AbstractMojo {

    @Parameter(alias = "destDir", property = "tsc.dest.folder", defaultValue = "${project.build.directory}/${project.artifactId}-${project.version}/tsc")
    private String destDir;

    @Parameter(alias = "fileName", property = "tsc.file.name", defaultValue = "index.jsp")
    private String fileName;

    @Parameter(alias = "srcDir", property = "tsc.src.dir", defaultValue = "${project.build.sourceDirectory}")
    private String sourcePath;

    @Parameter(alias = "subpackages", property = "tsc.subpackages", defaultValue = "com")
    private String subpackages;

    @Parameter(defaultValue = "${project.compileClasspathElements}", readonly = true)
    private List<String> projectClassPath;

    @Parameter(alias = "css", property = "tsc.css", defaultValue = "")
    private String css;

    public void execute() throws MojoExecutionException {
        getLog().info("Started generating web tsc");

        getLog().info("Source files path - " + sourcePath);
        getLog().info("Destination directory - " + destDir);
        getLog().info("File name - " + fileName);

        // We need to use project classpath
        String classpath = "";
        for (String cle : projectClassPath) {
            classpath += cle + File.pathSeparator;
        }

        getLog().debug("ClassPath - " + classpath);

        // set the output property
        System.setProperty("destDir", destDir);
        System.setProperty("fileName", fileName);
        System.setProperty("css", css == null ? "" : css);

        String[] args = new String[] { "-sourcepath", sourcePath, "-classpath", classpath, "-subpackages", subpackages, "-quiet", "true"};

        // run javadoc tool
        Main.execute("tsc", ScalaSpringDoclet.class.getName(), args);

        getLog().info("Done generating web tsc");
    }
}
