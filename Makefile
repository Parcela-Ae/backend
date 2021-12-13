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
	mvn clean install compile
	
run:
	mvn spring-boot:run

deploy:
	mvn heroku:deploy -Dheroku.appName=parcela-ae-app