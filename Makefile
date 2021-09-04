clean:
	mvn clean

gen-sources:
	mvn clean generate-sources

unit-test:
	mvn clean test

mutation-test:
	mvn clean install && (mvn org.pitest:pitest-maven:mutationCoverage || true) && start target/pit-reports/*/index.html

test: unit-test mutation-test

build:
	mvn clean install
	
run:
	mvn spring-boot:run