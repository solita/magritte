#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.util/project.sh
source ../../.util/docker.sh

name="$project_name-$(basename "$(pwd)")"

if ! image_exists "$name"; then
  echo "Building image $name..."
  cp ~/.ssh/id_rsa.pub .
  docker build -t $name .
fi

if ! container_exists "$name"; then
  echo "Creating container $name..."
  docker create --name $name \
    -v "$(cd ../.. && pwd):/project" \
    $name \
    >/dev/null
fi
