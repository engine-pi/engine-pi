PACKAGE_PATH = engine-pi/src/main/java/org
JBOX2D_PATH = $(PACKAGE_PATH)/jbox2d

ASSETS = "$(HOME)/.cache/engine-pi-assets"

deploy:
	mvn deploy

doc: clean install
	mvn javadoc:javadoc

	xdg-open modules/engine/target/reports/apidocs/index.html
	xdg-open modules/demos/target/reports/apidocs/index.html

format:
	mvn process-sources

install:
	mvn install

package:
	mvn package

clean:
	mvn clean

sync_resources:
	if [ -d $(ASSETS) ]; then \
		git -C $(ASSETS) pull; \
	else \
		git clone https://github.com/engine-pi/assets.git $(ASSETS); \
	fi
	rsync -av --delete $(ASSETS)/tetris/resources modules/games/tetris/src/main
	rsync -av --delete $(ASSETS)/pacman/resources modules/games/pacman/src/main

.PHONY: deploy doc format install package run_jar clone_jbox2d
