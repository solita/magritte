#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"

for d in */; do
  if [[ -f "${d}start.sh" ]]; then
    "${d}start.sh"
  fi
done
