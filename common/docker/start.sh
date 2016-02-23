#!/bin/bash
set -eu
cd "$(dirname $BASH_SOURCE)"

find . -mindepth 2 -name 'start.sh' -exec '{}' \;
