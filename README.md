# Requirements
Docker must be installed. That's it. You do not need a Java JDK or Gradle installed.


# Usage and Demo
**Step 1:** Download repository, extract it. Create the Docker image according to [Dockerfile](Dockerfile).
This step uses Gradle to build, test, and package the [Java application](src/main/java/ru/borisov/giphycurrency/GiphyCurrencyApplication.java)
according to [build.gradle](build.gradle). The resulting image is 372MB in size.

```shell
# Open terminal inside extracted repository folder.
$ docker build -t giphy-currency .
```

**Step 2:** Start a container for the Docker image.

```shell
# It will start on port 8090 on your machine.
$ docker run -d -p 8090:8090 giphy-currency
```

**Step 3:** Open another terminal and access the example API endpoint.

```shell
$ curl http://localhost:8090/api/currencyGif
{
    "currencyStatus": "DOWN",
    "gifUrl": "https://media1.giphy.com/media/f7MO098FCipmq0eUpV/giphy.gif?cid=1eb9b1efj3pm3tg3chgbx94ttb5b5yguew85crv4faiovtqb&rid=giphy.gif&ct=g",
    "currency": "rub"
}
```

**Step 4:** Open the browser and try to get html page.

```shell
http://localhost:8090/currency/gif
```


# Notes

You can also build, test, package, and run the Java application locally (without Docker)
if you have JDK 11+ and Gradle installed.

```shell
# Build, test, package the application locally
$ gradle clean package

# Run the application locally
$ java -jar build/libs/giphy-currency-0.0.1-SNAPSHOT.jar
```
