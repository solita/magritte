#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"
source docker.sh

cp ~/.ssh/id_rsa.pub ../docker/ansible/
silent=1 ensure_image_created ansible ../docker/ansible
silent=1 ensure_container_created ansible -v "$(cd .. && pwd):/project" ansible
silent=1 ensure_container_running ansible

container_ip="$(docker inspect -f "{{ .NetworkSettings.IPAddress }}" ansible)"
ssh \
  -o UserKnownHostsFile=/dev/null \
  -o StrictHostKeyChecking=no \
  -o LogLevel=quiet \
  -t \
  -A \
  ansible@$container_ip \
  "cd /project && $@"
