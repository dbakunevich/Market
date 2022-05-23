FROM node:18-alpine

WORKDIR /usr/app
COPY ./ /usr/app

RUN npm install

EXPOSE 8182
CMD [ "npm", "start" ]
