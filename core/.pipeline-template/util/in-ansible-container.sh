#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"

source docker.sh

../../docker/PROJECT-ansible/start.sh

ssh \
  -o UserKnownHostsFile=/dev/null \
  -o StrictHostKeyChecking=no \
  -o LogLevel=ERROR \
  -t \
  -A \
  ansible@$(docker_host_ip) \
  -p $(cat ../ansible-ssh-port) \
  "cd /project && $@" \
  2>&1 | grep -vF "Connection to $(docker_host_ip) closed."
