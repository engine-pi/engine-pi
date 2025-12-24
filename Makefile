PACKAGE_PATH = engine-pi/src/main/java/org
JBOX2D_PATH = $(PACKAGE_PATH)/jbox2d

ASSETS = "$(HOME)/.cache/engine-pi-assets"

deploy:
	mvn deploy

doc: install_build_tools
	mvn --file modules/engine clean
	mvn --file modules/engine javadoc:javadoc
	xdg-open modules/engine/target/reports/apidocs/index.html

doc_all: clean install
	mvn javadoc:javadoc
	xdg-open modules/engine/target/reports/apidocs/index.html
	xdg-open modules/demos/target/reports/apidocs/index.html

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

pull_resources:
	 mkdir -p $(HOME)/.cache
	if [ -d $(ASSETS) ]; then \
		git -C $(ASSETS) pull; \
	else \
		git clone https://github.com/engine-pi/assets.git $(ASSETS); \
	fi
	rsync -av --delete $(ASSETS)/blockly-robot/resources modules/games/blockly-robot/src/main
	rsync -av --delete $(ASSETS)/pacman/resources modules/games/pacman/src/main
	rsync -av --delete $(ASSETS)/tetris/resources modules/games/tetris/src/main

mkdocs_deploy:
	mkdocs gh-deploy

mkdocs_serve:
	mkdocs serve
