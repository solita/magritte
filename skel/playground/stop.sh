#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"
source ../.util/docker.sh

for h in $(ls ../docker/playground-hosts); do
  ensure_container_stopped $h
done
