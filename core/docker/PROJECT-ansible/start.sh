#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.magritte/util/docker.sh

name="$(basename "$(pwd)")"

if ! container_running "$name"; then
  ./create.sh
  echo "Starting container $name..."
  docker start "$name" >/dev/null

  # TODO: Replace with a proper polling loop
  echo 'Waiting for sshd to start...'
  sleep 5

  ../../imagination/update-inventories

  echo 'Installing Ansible roles with ansible-galaxy...'
   ../../.magritte/util/in-ansible-container ansible-galaxy install -v -r /etc/ansible/requirements.yml
fi
