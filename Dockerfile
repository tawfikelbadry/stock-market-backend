FROM openjdk:8
ADD target/stock-market-backend.jar stock-market-backend.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","stock-market-backend.jar"]