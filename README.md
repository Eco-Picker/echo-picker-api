# Eco Picker API

## Prerequisites

- JDK 17 or higher
- Gradle
- IDE (IntelliJ IDEA recommended)

## Getting Started

### git clone

```shell
git clone https://github.com/Eco-Picker/eco-picker-api.git
cd eco-picker-api
```

### set an env file

```shell
cp .env.example .env
```
After copying, you need to update the .env file with your specific credentials. Particularly, ensure that the following section is properly configured:

```shell
GEMINI_APP_KEY={GEMINI_APP_KEY}
```
For the GEMINI_APP_KEY, please contact us at jsunwoo0977@gmail.com to request the appropriate key. Alternatively, if you already possess a Gemini API key, you can input it directly in the .env file.

Make sure that your .env file is correctly updated before proceeding with the setup.

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

http://localhost:15000/api/swagger-ui/index.html
### Health Check 
http://localhost:15000/api/actuator/health

## License

This project is licensed under the MIT License - see the LICENSE file for details.

Contact
For any questions or feedback, please contact

- [jerry2219398\@gmail.com](mailto:jerry2219398@gmail.com?subject=ecopicker)
- [jsunwoo0977\@gmail.com](mailto:jsunwoo0977@gmail.com?subject=ecopicker)
