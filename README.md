### Running everything separately

Run database:

`docker-compose up database`

Build the app:

`./gradlew clean build`

Run the app:

`./gradlew bootRun`

### Running everything through docker

_If running for the first time, if the previous image has been removed or there have been some changes to the docker file, the image needs to be rebuilt._

Build the image:

`docker-compose build`

Run the container:

`docker-compose up`

Or both at once:

`docker-compose up --build`

Stop the container:

`docker-compose down --volume`

### API documentation

http://localhost:8080/swagger-ui/index.html

### Future improvements

- Add tests
- Implement role based access
- Improve CORS settings
- Investigate and utilize actuator
- Investigate state management in Spring Security
- Write tests for configuration classes
- Switch to Flyway? Rely completely on ORM?
- Add email verification
- Add password reset option
- Add option to change email
- Add Google SSO
- Move old and new password matching to annotation
- Move new password validation to mapper
- Implement a complex account search (with parameter validation)
