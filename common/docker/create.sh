#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"

for d in */; do
  if [[ -f "${d}create.sh" ]]; then
    "${d}create.sh"
  fi
done
