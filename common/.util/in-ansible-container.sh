#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"

source docker.sh
source project.sh

../docker/ansible/start.sh

ssh \
  -o UserKnownHostsFile=/dev/null \
  -o StrictHostKeyChecking=no \
  -o LogLevel=ERROR \
  -t \
  -A \
  ansible@$(docker_host_ip) \
  -p 10022 \
  "cd /project && $@" \
  2>&1 | grep -vF "Connection to $(docker_host_ip) closed."
