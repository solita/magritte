FROM phusion/baseimage:0.9.18

RUN rm -f /etc/service/sshd/down

RUN useradd -m ansible
RUN echo ansible:ansible | chpasswd

COPY id_rsa.pub /tmp/
RUN chown ansible:ansible /tmp/id_rsa.pub
USER ansible
RUN mkdir /home/ansible/.ssh
RUN cat /tmp/id_rsa.pub >> /home/ansible/.ssh/authorized_keys && rm -f /tmp/id_rsa.pub
RUN chmod 700 /home/ansible/.ssh && chmod 600 /home/ansible/.ssh/authorized_keys
USER root

EXPOSE 22

ENV REFRESHED_AT 2016-03-15

RUN apt-get update && \
  apt-get install -y \
    python-setuptools \
    python-dev \
    git
RUN easy_install pip
RUN pip install ansible==2.0.0.2

COPY requirements.yml /tmp/
RUN ansible-galaxy install -r /tmp/requirements.yml && rm /tmp/requirements.yml
