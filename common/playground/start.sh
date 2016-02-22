#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"
source ../.util/docker.sh

./create.sh
for h in $(ls ../docker/playground-hosts); do
  ensure_container_running $h
done
./update-inventory.sh
