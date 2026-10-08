FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y \
    libgtk-3-0 libgl1 libxtst6 libxrender1 libxi6 \
    libasound2t64 fontconfig \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/MallPlanner-1.0-SNAPSHOT.jar app.jar

ENV DISPLAY=host.docker.internal:0.0
ENV DB_HOST=host.docker.internal
ENV DB_PORT=3307
ENV DB_NAME=mallplanner
ENV DB_USER=root
ENV DB_PASS=root

CMD ["java", "-jar", "app.jar"]