#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.util/project.sh
source ../../.util/docker.sh

name="$project_name-$(basename "$(pwd)")"

if image_exists "$name"; then
  echo "Removing image $name..."
  docker rmi "$name" >/dev/null
fi
