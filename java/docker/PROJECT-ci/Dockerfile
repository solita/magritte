FROM PROJECT-common

RUN mkdir -p /etc/my_init.d
COPY start_jenkins.sh /etc/my_init.d/start_jenkins
RUN chmod +x /etc/my_init.d/start_jenkins

EXPOSE 8080
