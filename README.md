# E-Store:  Rochester Munchies

An online E-store system built in Java 8=>11 and ___ _replace with other platform requirements_ ___
  
## Team

- Dara Prak
- Lucie Lim
- Adam Pang
- Robert Huang
- Jaden Seaton


## Prerequisites

- Java 8=>11
- Maven


## How to run it

1. Go to 'estore-api\src\main\java\com\estore\api\estoreapi\EstoreApiApplication.java' and run the main function.
2. Go to 'estore-ui\app' and run the command 'npm install'
3. In the same directory, run 'npm start'
4. Open your browser to http://localhost:4200

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
  
  
## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory


## License

MIT License

See LICENSE for details.
