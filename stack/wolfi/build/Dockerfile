FROM cgr.dev/chainguard/wolfi-base:latest

ARG packages

# Install packages that we want to make available at build time
RUN apk add --update $packages && \
  rm -rf /var/cache/apk/*

COPY ./bin/yj-linux /usr/local/bin/yj
RUN chmod a+x /usr/local/bin/yj

ENTRYPOINT []