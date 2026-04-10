Konfiguration für das [Javadoc Maven Plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/)

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-javadoc-plugin</artifactId>
            <version>3.12.0</version>
            <configuration>
                <!-- https://maven.apache.org/plugins/maven-javadoc-plugin/examples/stylesheet-configuration.html -->
                <stylesheetfile>javadoc-stylesheet.css</stylesheetfile>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>de.pirckheimer-gymnasium</groupId>
                    <artifactId>engine-pi-build-tools</artifactId>
                    <version>0.2.0</version>
                </dependency>
            </dependencies>
            <executions>
                <execution>
                    <id>attach-javadocs</id>
                    <goals>
                        <goal>jar</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

Konfiguration für das [Formatter Maven Plugin](https://code.revelc.net/formatter-maven-plugin)

```xml
<build>
    <plugins>
        <!--
            https://code.revelc.net/formatter-maven-plugin/
        https://code.revelc.net/formatter-maven-plugin/examples.html#basic-configuration-using-external-resource -->
        <plugin>
            <groupId>net.revelc.code.formatter</groupId>
            <artifactId>formatter-maven-plugin</artifactId>
            <version>2.29.0</version>
            <configuration>
                <configFile>eclipse-formatter.xml</configFile>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>format</goal>
                    </goals>
                </execution>
            </executions>
            <dependencies>
                <dependency>
                    <groupId>de.pirckheimer-gymnasium</groupId>
                    <artifactId>engine-pi-build-tools</artifactId>
                    <version>0.2.0</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```
