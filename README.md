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
