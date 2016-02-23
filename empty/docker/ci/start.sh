#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.util/project.sh
source ../../.util/docker.sh

name="$project_name-$(basename "$(pwd)")"

if ! container_running "$name"; then
  ./create.sh
  echo "Starting container $name..."
  docker start "$name" >/dev/null
  ../../playground/update-inventory.sh
fi
