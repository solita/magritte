#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.pipeline-template/util/docker.sh

name="$(basename "$(pwd)")"

if ! image_exists "$name"; then
  echo "Building image $name..."
  cp ~/.ssh/id_rsa.pub .
  docker build -t $name .
fi

if ! container_exists "$name"; then
  echo "Creating container $name..."
  docker create --name $name \
    -v "$(cd ../.. && pwd):/project" \
    -P \
    $name \
    >/dev/null
fi
