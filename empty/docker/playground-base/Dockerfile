FROM phusion/baseimage:0.9.18

RUN rm -f /etc/service/sshd/down

COPY id_rsa.pub /tmp/
RUN cat /tmp/id_rsa.pub >> /root/.ssh/authorized_keys && rm -f /tmp/id_rsa.pub

RUN apt-get update && \
  apt-get install -y python
