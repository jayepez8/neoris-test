FROM openjdk:11.0.15

LABEL authors="Jonathan Yépez"

WORKDIR /app

COPY /build/libs/transactions-*.jar /app/app.jar


EXPOSE 8082

CMD ["java", "-jar", "app.jar"]
