install:
	mvn install

package:
	mvn package

deploy:
	mvn deploy

.PHONY: install package
