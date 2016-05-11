#!/bin/bash
set -eu

cd "$(dirname "$BASH_SOURCE")"
source ../../.pipeline-template/util/docker.sh

name="$(basename "$(pwd)")"

if ! image_exists "$name"; then
  echo "Building image $name..."
  ../../.pipeline-template/util/ensure-ssh-keys-generated
  cp ~/.ssh/id_rsa.pub .
  docker build -t $name .
fi
