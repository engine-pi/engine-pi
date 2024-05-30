deploy:
	mvn deploy

doc:
	rm -rf target/site/apidocs
	mvn javadoc:javadoc
	xdg-open target/site/apidocs/index.html

format:
	mvn formatter:format

install:
	mvn install

package:
	mvn package

run_jar: package
	java -jar target/engine-omega-0.9.0.jar

.PHONY: deploy doc format install package run_jar
