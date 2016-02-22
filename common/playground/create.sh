#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"
source ../.util/docker.sh

cp ~/.ssh/id_rsa.pub ../docker/playground-base/
ensure_image_created playground-base ../docker/playground-base
for h in $(ls ../docker/playground-hosts); do
  ensure_image_created $h ../docker/playground-hosts/$h
done
for h in $(ls ../docker/playground-hosts); do
  ensure_container_created $h $h
done
