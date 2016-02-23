#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"

find . -mindepth 2 -name 'destroy.sh' -exec '{}' \;
