
# The default recipe runs 'git pull', syncs the assets, runs 'mvn package' and generate the mkdocs site
default: pull assets package mkdocs_build

# Run 'mvn deploy'
deploy:
	mvn deploy

# Generate the Javadoc API for the engine and MkDocs site.
doc: javadoc_engine mkdocs_build

# Generate the Javadoc API for the engine only and open the generated documentation in a browser.
javadoc_engine: install_build_tools
	mvn --file subprojects/engine clean
	mvn --file subprojects/engine javadoc:javadoc
	xdg-open subprojects/engine/target/reports/apidocs/index.html

# Generate the Javadoc API for all subprojects, then open the generated documentation for the engine, demos and aggregated APIs in a browser using xdg-open.
javadoc_all: clean install
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

# Run 'git pull'
pull:
	git pull

# Run 'mvn clean'
clean:
	mvn clean

assets_init:
	git submodule init
	git submodule update
	cd assets; git checkout main; git pull
	git pull

assets_sync_resources:
	rsync -av --delete assets/blockly-robot/resources/ subprojects/games/blockly-robot/src/main/resources/
	rsync -av --delete assets/demos/resources/ subprojects/demos/src/main/resources/
	rsync -av --delete assets/pacman/resources/ subprojects/games/pacman/src/main/resources/
	rsync -av --delete assets/tetris/resources/ subprojects/games/tetris/src/main/resources/

assets: assets_init assets_sync_resources

export NO_MKDOCS_2_WARNING := "true"

mkdocs_build:
	mkdocs build --strict
	xdg-open site/index.html

mkdocs_deploy:
	mkdocs gh-deploy

mkdocs_serve:
	mkdocs serve --livereload

# Execute the cli command
cli *args:
    mvn --projects de.pirckheimer-gymnasium:engine-pi-cli exec:java -Dexec.mainClass=cli.Client -Dexec.args="{{args}}"
    # java -jar subprojects/cli/target/engine-pi-cli-0.1.0-jar-with-dependencies.jar {{args}}
