#!/bin/bash
set -eu

script_dir="$(cd "$(dirname "$BASH_SOURCE")" && pwd)"

if [[ $# -lt 1 ]]; then
  echo "Usage: $0 DIR" >&2
  exit 1
fi

project_dir="$1"

if [[ ! -f "$project_dir/.project-name" ]]; then
  echo "The directory '$project_dir' does not look like a pipeline-template project. Not upgrading." >&2
  exit 1
fi

cp -r "$script_dir/common/"* "$script_dir/common/.util" "$project_dir"

echo "Project $(cat "$project_dir/.project-name") upgraded successfully!"
