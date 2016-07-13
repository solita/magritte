FROM solita/ansible-ssh:2.1.0.0

COPY id_rsa.pub /tmp/
RUN cat /tmp/id_rsa.pub >> /home/ansible/.ssh/authorized_keys && rm -f /tmp/id_rsa.pub

RUN mkdir -p /etc/ansible/roles
COPY requirements.yml /etc/ansible/
RUN chown -R ansible:ansible /etc/ansible
