FROM openjdk:17
EXPOSE 8080
ADD target/employe-hierarchy-images.jar employe-hierarchy-images.jar
ENTRYPOINT [ "java", "-jar", "/employe-hierarchy-imagesjar" ]