#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.pipeline-template/util/docker.sh

name="$(basename "$(pwd)")"

if ! image_exists "$name"; then
  ../PROJECT-app-common/create.sh
  echo "Building image $name..."
  docker build -t $name .
fi

if ! container_exists "$name"; then
  echo "Creating container $name..."
  docker create -h $name --name $name \
    -P \
    $name \
    >/dev/null
fi
