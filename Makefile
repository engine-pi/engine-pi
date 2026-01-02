PACKAGE_PATH = engine-pi/src/main/java/org
JBOX2D_PATH = $(PACKAGE_PATH)/jbox2d

all: package

deploy:
	mvn deploy

doc: install_build_tools
	mvn --file subprojects/engine clean
	mvn --file subprojects/engine javadoc:javadoc
	xdg-open subprojects/engine/target/reports/apidocs/index.html

doc_all: clean install
	mvn javadoc:javadoc
	xdg-open subprojects/engine/target/reports/apidocs/index.html
	xdg-open subprojects/demos/target/reports/apidocs/index.html

	mvn javadoc:aggregate
	xdg-open target/reports/apidocs/index.html

format:
	mvn process-sources

install_build_tools:
# Package build-tools is requried by javadoc
	mvn install --projects de.pirckheimer-gymnasium:engine-pi-build-tools

install: install_build_tools
	mvn install

package: install_build_tools
	# Package build-tools is requried by javadoc
	mvn install --projects de.pirckheimer-gymnasium:engine-pi-build-tools
	mvn package

clean:
	mvn clean

assets_init:
	git submodule init
	git submodule update

assets_sync_resources:
	rsync -av --delete assets/blockly-robot/resources/ subprojects/games/blockly-robot/src/main/resources/
	rsync -av --delete assets/demos/resources/ subprojects/demos/src/main/resources/
	rsync -av --delete assets/pacman/resources/ subprojects/games/pacman/src/main/resources/
	rsync -av --delete assets/tetris/resources/ subprojects/games/tetris/src/main/resources/

assets: assets_init assets_sync_resources
.PHONY: assets

mkdocs_deploy:
	mkdocs gh-deploy

mkdocs_serve:
	mkdocs serve --livereload
