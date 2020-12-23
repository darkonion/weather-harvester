FROM openjdk:11
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
RUN apt-get update -y
RUN apt-get install -y tzdata
ENV TZ Europe/Warsaw
ENTRYPOINT ["java","-cp","app:app/lib/*","pl.homeweather.weatherharvester.WeatherHarvesterApplication"]