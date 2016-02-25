#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.pipeline-template/util/docker.sh

name="$(basename "$(pwd)")"

if container_running "$name"; then
  echo "Stopping container $name..."
  docker stop "$name" >/dev/null
  ../../playground/update-inventory
fi
