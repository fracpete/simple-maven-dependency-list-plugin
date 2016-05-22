# maven-dependency-list-plugin

Simplified version of (maven-dependency-list)[https://maven.apache.org/plugins/maven-dependency-plugin/] 
plugin that just outputs a list of dependencies.

Based on version (2.10)[http://svn.apache.org/viewvc/maven/plugins/tags/maven-dependency-plugin-2.10/] 
of the plugin.

For your `pom.xml`:

```xml
  <build>
    <pluginManagement>
      <plugins>
        ...
        <plugin>
          <groupId>com.github.fracpete</groupId>
          <artifactId>maven-dependency-list-plugin</artifactId>
          <version>0.0.1</version>
          <executions>
            <execution>
              <id>list</id>
              <phase>package</phase>
              <goals>
                <goal>list</goal>
              </goals>
              <configuration>
                <includeGroupIds>nz.ac.waikato.cms.adams</includeGroupIds>  <!-- the accepted group IDs -->
                <outputFile>${project.build.directory}/${project.artifactId}-${project.version}.dep</outputFile>  <!-- where to output the list -->
                <sort>true</sort>                           <!-- sorted list -->
                <type>jar</type>
                <includeScope>compile</includeScope>        <!-- only artifacts used for compiling -->
                <outputGroupId>false</outputGroupId>        <!-- no group ID -->
                <outputClassifier>false</outputClassifier>  <!-- no classifier -->
                <outputVersion>false</outputVersion>        <!-- no version -->
                <outputScope>false</outputScope>            <!-- no scope -->
              </configuration>
            </execution>
          </executions>
        </plugin>
        ...
      </plugins>
    </pluginManagement>
    ...
    <plugins>
      <plugin>
        <groupId>com.github.fracpete</groupId>
        <artifactId>maven-dependency-list-plugin</artifactId>
      </plugin>
      ...
    <plugins>
  </build>

```
