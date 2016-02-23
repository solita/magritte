#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.util/project.sh
source ../../.util/docker.sh

name="$project_name-$(basename "$(pwd)")"

if ! image_exists "$name"; then
  ../common/create.sh
  echo "Building image $name..."
  docker build -t $name .
fi

if ! container_exists "$name"; then
  echo "Creating container $name..."
  docker create --name $name \
    $name \
    >/dev/null
fi
