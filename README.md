## dev
mvn clean package -Dmaven.test.skip=true -Denv=dev

## test
mvn clean package -Dmaven.test.skip=true -Denv=test

## prod
mvn clean package -Dmaven.test.skip=true -Denv=prod