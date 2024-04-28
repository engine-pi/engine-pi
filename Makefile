deploy:
	mvn deploy

doc:
	mvn javadoc:javadoc
	xdg-open target/site/apidocs/index.html

install:
	mvn install

package:
	mvn package

.PHONY: deploy doc install package
