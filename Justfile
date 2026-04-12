
# The default recipe runs 'git pull', syncs the assets, runs 'mvn package' and generate the mkdocs site
default: pull assets install_cli check_file_links package mkdocs_build

# Run 'mvn deploy'
deploy:
	mvn deploy

# Run 'mvn compile'
compile:
	mvn compile

#  Run 'mvn compile'
alias build := compile

# Run 'mvn test' for the specified subproject, or for all subprojects if no subproject is specified.
test subproject='':
	if [ -n "{{subproject}}" ]; then \
		mvn --projects de.pirckheimer-gymnasium:{{subproject}} test; \
	else \
		mvn test; \
	fi

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

# Generate the aggregated Javadoc API for all subprojects, then open the generated documentation in a browser using xdg-open.
javadoc_aggregate: clean install
	mvn javadoc:aggregate
	xdg-open target/reports/apidocs/index.html

# Run 'mvn process-sources' to format the source code using the configured formatter.
format:
	mvn process-sources

install_build_tools:
	# Package build-tools is requried by javadoc
	mvn install --projects de.pirckheimer-gymnasium:engine-pi-build-tools

# Install the CLI subproject, which is required for the 'check_file_links' target.
install_cli:
	mvn install --projects de.pirckheimer-gymnasium:engine-pi-cli

# Install all subprojects, which is required for the 'package' target.
install: install_build_tools
	mvn install

# Run 'mvn package' to build the project and create the JAR files for all subprojects.
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

# Initialize and update the git submodule for assets, then pull the latest changes from the main branch of the assets repository and the main repository.
assets_init:
	git submodule init
	git submodule update
	cd assets; git checkout main; git pull
	git pull

# Synchronize resource files using rsync from the assets directory to the subprojects.
assets_sync_resources:
	rsync -a --delete assets/blockly-robot/resources/ subprojects/games/blockly-robot/src/main/resources/
	rsync -a --delete assets/demos/resources/ subprojects/demos/src/main/resources/
	rsync -a --delete assets/pacman/resources/ subprojects/games/pacman/src/main/resources/
	rsync -a --delete assets/tetris/resources/ subprojects/games/tetris/src/main/resources/
	rsync -a --delete assets/demos/resources/ subprojects/engine/src/test/resources/demos/
	rsync -a --delete assets/tetris/resources/images/image-text/ subprojects/engine/src/test/resources/image-text/
	rsync -a --delete assets/tetris/resources/ subprojects/games/tetris/src/main/resources/


	rsync -a --delete assets/tetris/resources/images/image-text/ subprojects/demos/src/main/resources/main-classes/actor/image-text/tetris/
	rsync -a --delete assets/tetris/resources/images/image-text/ subprojects/demos/src/main/resources/main-classes/actor/image-text/tetris/
	rsync -a --delete assets/pacman/resources/images/image-text/ subprojects/demos/src/main/resources/main-classes/actor/image-text/pacman/
	rsync -a --delete assets/space-invaders/resources/images/image-text/ subprojects/demos/src/main/resources/main-classes/actor/image-text/space-invaders/

# The 'assets' target depends on 'assets_init' and 'assets_sync_resources', which means that running 'just assets' will first initialize and update the git submodule for assets, then synchronize the resource files using rsync.
assets: assets_init assets_sync_resources

export NO_MKDOCS_2_WARNING := "true"

# Build the MkDocs site and open the generated index.html in a browser using xdg-open.
mkdocs_build:
	mkdocs build --config-file docs/mkdocs.yml --strict

# Open the generated index.html of the MkDocs site in a browser using xdg-open.
mkdocs_open:
	xdg-open site/index.html

# Deploy the MkDocs site to GitHub Pages using the 'gh-deploy' command.
mkdocs_deploy:
	mkdocs gh-deploy --config-file docs/mkdocs.yml

# Serve the MkDocs site locally with livereload enabled using the 'serve' command.
mkdocs_serve:
	mkdocs serve --config-file docs/mkdocs.yml --livereload

# Execute the cli command (subprojects/cli) with the provided arguments.
cli *args:
	mvn --projects de.pirckheimer-gymnasium:engine-pi-cli exec:java -Dexec.mainClass=cli.Client -Dexec.args="{{args}}"
	# java -jar subprojects/cli/target/engine-pi-cli-0.1.0-jar-with-dependencies.jar {{args}}

# Execute the cli command (subprojects/cli) with the main class 'cli.checklinks.FileLinkChecker' to check file links in the documentation.
check_file_links: (cli "check-file-links")
	mvn --projects de.pirckheimer-gymnasium:engine-pi-cli exec:java -Dexec.mainClass=cli.checklinks.FileLinkChecker
