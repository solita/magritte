#!/bin/bash
set -eu

script_dir="$(cd "$(dirname "$BASH_SOURCE")" && pwd)"

if [[ $# -lt 1 ]]; then
  echo "Usage: $0 DIR" >&2
  exit 1
fi

project_dir="$1"

if [[ ! -d "$project_dir/.pipeline-template" ]]; then
  echo "The directory '$project_dir' does not look like a pipeline-template project. Not upgrading." >&2
  exit 1
fi

project_name="$(cat "$project_dir/.pipeline-template/project-name")"

source "$script_dir/.project-util.sh"
copy_project_directory "$script_dir/core" "$project_dir"
copy_project_directory "$script_dir/core/.pipeline-template/util" "$project_dir/.pipeline-template/util"

echo "Project $project_name upgraded successfully!"
