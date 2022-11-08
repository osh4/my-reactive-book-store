FROM openjdk:17
RUN groupadd bookstoreuser && useradd -m -g bookstoreuser -d /home/bookstoreuser -s /bin/bash bookstoreuser
USER bookstoreuser:bookstoreuser
ENV BOOK_SERVICE_HOME=/usr/bookstore
WORKDIR $BOOK_SERVICE_HOME
COPY target/*.jar bookstore.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar ${BOOK_SERVICE_HOME}/bookstore.jar"]