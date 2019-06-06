# Description
This app demonstrates bargaining on an auction. Two bidders take part in it, they make there beds simultaneously and after it detect a winner. 

## Build the app with tests
```bash
mvn package
```

## Build and run the app
```bash
mvn package; \
mvn exec:java -Dexec.mainClass="auction.App"
```

## Run tests only
```bash
mvn '-Dtest=auction.**.*' test
```