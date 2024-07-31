# Eco Picker API

## Prerequisites

- JDK 17 or higher
- Gradle
- IDE (IntelliJ IDEA recommended)

B## Getting Started

### git clone

```shell
git clone https://github.com/Eco-Picker/eco-picker-api.git
cd eco-picker-api
```

### set an env file

```shell
cp .env.example .env
```

### build

```shell
./gradlew build 
```

## [Optional] Docker-compose for mysql

✅ check environment at docker-compose.yml
✅ check if your local port is occupied

```shell
cd docker
docker-compose up -d 
```

### bootRun

```shell
./gradlew bootRun
```

### Swagger

http://localhost:15000/swagger-ui/index.html

### Health Check 
http://localhost:15000/actuator/health

## License

This project is licensed under the MIT License - see the LICENSE file for details.

Contact
For any questions or feedback, please contact

- [jerry2219398\@gmail.com](mailto:jerry2219398@gmail.com?subject=ecopicker)
- [jsunwoo0977\@gmail.com](mailto:jsunwoo0977@gmail.com?subject=ecopicker)

### TODO

- [ ] encrypt/decrypt properties
- [ ] set up logging
- [ ] images - s3, local, etc
- [ ] locations - map
- [ ] version check 
