FROM gitpod/workspace-full

USER gitpod

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 17.0.9-tem && \
    sdk default java 17.0.9-tem"
RUN cp /etc/hosts /tmp/hosts && echo "127.0.0.1 mariadb" >> /tmp/hosts
RUN sudo cp /tmp/hosts /etc/hosts
RUN docker compose up -d