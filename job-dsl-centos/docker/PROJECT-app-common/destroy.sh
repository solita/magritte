#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.pipeline-template/util/docker.sh

name="$(basename "$(pwd)")"

if image_exists "$name"; then
  echo "Removing image $name..."
  docker rmi "$name" >/dev/null
fi
