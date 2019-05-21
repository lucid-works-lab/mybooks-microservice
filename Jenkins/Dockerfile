FROM jenkins/jenkins:lts
USER root
RUN apt-get update
RUN apt-get install -y maven imagemagick
RUN curl -sSL https://get.docker.com/ | sh