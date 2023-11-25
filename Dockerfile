FROM amazoncorretto:21
MAINTAINER vtesguarida.org
COPY boot/target/vtes-community-back.jar /vtes-community-back.jar
ARG VTES_MONGO_URL
ENV VTES_MONGO_URL VTES_MONGO_URL
ENTRYPOINT ["java", "-jar", "/vtes-community-back.jar"]