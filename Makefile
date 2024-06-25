PACKAGE_PATH = engine-pi/src/main/java/org
JBOX2D_PATH = $(PACKAGE_PATH)/jbox2d

deploy:
	mvn deploy

doc: install
	rm -rf engine-pi/target/site/apidocs
	rm -rf engine-pi-demos/target/site/apidocs
	mvn javadoc:javadoc

	xdg-open engine-pi/target/site/apidocs/index.html
	xdg-open engine-pi-demos/target/site/apidocs/index.html

format:
	mvn formatter:format

install:
	mvn install

package:
	mvn package

run_jar:
	rm -rf engine-pi/target
	mvn package
	java -jar engine-pi/target/engine-pi-0.19.0.jar

clone_jbox2d:
	-rm -rf /tmp/jbox2d
	git clone https://github.com/jbox2d/jbox2d.git /tmp/jbox2d
	-rm -rf $(JBOX2D_PATH)
	mkdir -p $(PACKAGE_PATH)
	cp -r /tmp/jbox2d/jbox2d-library/$(JBOX2D_PATH) $(PACKAGE_PATH)
	rm -rf $(JBOX2D_PATH)/gwtemul
	rm -rf $(JBOX2D_PATH)/JBox2D.gwt.xml
	mvn formatter:format

.PHONY: deploy doc format install package run_jar
