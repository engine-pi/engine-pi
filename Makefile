PACKAGE_PATH = engine-pi/src/main/java/org
JBOX2D_PATH = $(PACKAGE_PATH)/jbox2d

ASSETS = "$(HOME)/.cache/engine-pi-assets"

deploy:
	mvn deploy

doc: clean install
	mvn javadoc:javadoc
	xdg-open modules/engine/target/reports/apidocs/index.html
	xdg-open modules/demos/target/reports/apidocs/index.html

	mvn javadoc:aggregate
	xdg-open target/reports/apidocs/index.html

format:
	mvn process-sources

install:
	# Package build-tools is requried by javadoc
	mvn install --projects de.pirckheimer-gymnasium:engine-pi-build-tools
	mvn install

package:
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
