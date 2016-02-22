#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"
source ../.util/docker.sh

./stop.sh
for h in $(ls ../docker/playground-hosts); do
  ensure_container_destroyed $h
done
for h in $(ls ../docker/playground-hosts); do
  ensure_image_destroyed $h
done
ensure_image_destroyed playground-base
