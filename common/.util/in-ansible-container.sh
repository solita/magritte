#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"

source project.sh

../docker/ansible/start.sh

container_ip="$(docker inspect -f "{{ .NetworkSettings.IPAddress }}" $project_name-ansible)"
ssh \
  -o UserKnownHostsFile=/dev/null \
  -o StrictHostKeyChecking=no \
  -o LogLevel=quiet \
  -t \
  -A \
  ansible@$container_ip \
  "cd /project && $@"
