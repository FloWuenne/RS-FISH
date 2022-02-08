FROM maven:3.8.4-jdk-8-slim
RUN apt-get -y update
RUN apt-get -y install git
RUN git clone --branch docker_jvm_update https://github.com/FloWuenne/RS-FISH.git
WORKDIR /RS-FISH
ENV JAVA_OPTS=""
RUN ./install
RUN sed -i "s|Xmx0g|Xmx8g|g" /RS-FISH/rs-fish
