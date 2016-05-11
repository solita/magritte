#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.pipeline-template/util/docker.sh

name="$(basename "$(pwd)")"

if ! image_exists "$name"; then
  ../PROJECT-common/create.sh
  echo "Building image $name..."
  docker build -t $name .
fi
