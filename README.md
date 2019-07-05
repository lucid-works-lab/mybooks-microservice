
### Travis Build Status
[![Build Status](https://travis-ci.org/lucid-works-lab/mybooks-microservice.svg?branch=master)](https://travis-ci.org/lucid-works-lab/mybooks-microservice.svg?branch=master)

# book-library

- add/load book item to catalogue
- use reactor for event processing
- use functions for REST

# Installation

## Local Jenkins
`>> cd Jenkins`

`>> docker-compose up`

note: Windows - shared drives in settings!, reset credentials!

## SAM local

[Installing the AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)

`>> sam local start-api`

(make sure Docker Shared Drives Setting properly done)

## Deploy to DigitalOcean
http://webexpressive.com/blog/superfast-CI-CD-using-docker-travisci-and-digitalocean/



## On DigitalOcean instance:

`>> docker run --rm -p 8080:8080 lucidworkslab/mybooks`

`>> ufw allow 8080`
`>> ufw allow 9090`

### add travis_rsa.pub

`>> nano ~/.ssh/authorized_keys`

https://monicalent.com/blog/2017/12/21/deploy-static-sites-digital-ocean-travis/

ssh -i ~/.ssh/lucid_works root@mybooks.digitalocean.lucid.works




