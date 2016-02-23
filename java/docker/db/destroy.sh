#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.util/project.sh
source ../../.util/docker.sh

name="$project_name-$(basename "$(pwd)")"

if container_exists "$name"; then
  ./stop.sh
  echo "Removing container $name..."
  docker rm "$name" >/dev/null
fi

if image_exists "$name"; then
  echo "Removing image $name..."
  docker rmi "$name" >/dev/null
fi
