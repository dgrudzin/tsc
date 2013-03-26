# Spring MVC test console

Maven plugin that generates test console for web projects that use spring mvc [@RequestMapping](http://static.springsource.org/spring/docs/2.5.x/api/org/springframework/web/bind/annotation/RequestMapping.html).
The output is an html file that can be further deployed with the application inside a war file.
The resulting console is a list of html forms for all the methods marked with the specified annotation.
Every form can be asynchronously submitted and the response will appear on the page.
<p>

Usage: <p>

As a minimum add the following lines to your project's pom file:

<pre>
&lt;build&gt;
      &lt;plugins&gt;
      ...
        &lt;plugin&gt;
            &lt;groupId&gt;org.tsc&lt;/groupId&gt;
            &lt;artifactId&gt;tsc&lt;/artifactId&gt;
            &lt;version&gt;1.0-SNAPSHOT&lt;/version&gt;
            &lt;executions&gt;
                &lt;execution&gt;
                    &lt;goals&gt;
                        &lt;goal&gt;generate&lt;/goal&gt;
                    &lt;/goals&gt;
                &lt;/execution&gt;
            &lt;/executions&gt;
        &lt;/plugin&gt;
      ...            
      &lt;/plugins&gt;
&lt;/build&gt;
</pre>
<p>
and the console will be created and placed under you target folder.

<p>
Available configuration parameters:

<p>
<b>destDir</b> - the directory to place the console file under, defaults to "${project.build.directory}/${project.artifactId}-${project.version}/tsc"
<p>
<b>fileName</b> - console file name, defaults to "index.jsp"
<p>
<b>srcDir</b> - directory to look for source files, defaults to "${project.build.sourceDirectory}"
<p>
<b>subpackages</b> - pacakages to pars, defaults to "com"
<p>
<b>css</b> - path to css file to use will use default project css otherwise









