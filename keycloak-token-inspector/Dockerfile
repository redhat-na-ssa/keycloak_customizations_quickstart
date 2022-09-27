# podman build --rm -f Dockerfile -t quay.io/jbride/keycloak-token-inspector:0.0.2 .

FROM docker.io/node@sha256:d48eee0bc34430178bc3bf10b767f698afbf26d57b59ec63fa5f6de9dd87ed87
COPY package.json .
RUN npm install
COPY app.js .
COPY index.html .
EXPOSE 8000
CMD [ "npm", "start" ]
