#!/bin/bash
set -eu

script_dir="$(cd "$(dirname "$BASH_SOURCE")" && pwd)"
samples="$(find "$script_dir" -mindepth 1 -maxdepth 1 -type d -not \( -name common -or -name .git \) -printf '%P\n')"

function in_list {
  for x in $1; do
    [[ "$x" == "$2" ]] && return 0
  done
  return 1
}

function list_available_samples {
  echo "Available samples:" >&2
  (
    cd "$script_dir"
    for s in *; do
      if [[ -d "$s" && "$s" != 'core' ]]; then
        echo "  - $s: $(cat "$script_dir/$s/.description")"
      fi
    done
  )
}

if [[ $# -lt 2 ]]; then
  echo "Usage: $0 NAME DIR [SAMPLE]" >&2
  echo >&2
  echo "SAMPLE defaults to 'empty'." >&2
  echo >&2
  list_available_samples
  exit 1
fi

if [[ $# -gt 2 ]]; then
  sample="$3"
  if ! in_list "$samples" "$sample"; then
    echo "Invalid sample: $sample" >&2
    echo >&2
    list_available_samples
    exit 1
  fi
else
  sample='empty'
fi

project_name="$1"
project_dir="$2"
parent_dir="$(dirname "$project_dir")"

for dir in "$project_dir" "$parent_dir"; do
  if [[ -d "$dir" ]]; then
    dir_full="$(cd "$dir" && pwd)"
    if [[ "$dir_full" == "$script_dir" || "$dir_full" == "$script_dir/"* ]]; then
      echo "The directory '$script_dir' is for samples only. Can't create a new project here." >&2
      exit 1
    fi
  fi
done

if [[ ! -d "$parent_dir" ]]; then
  echo "Can't create a new project in '$project_dir' because the directory '$parent_dir' does not exist." >&2
  exit 1
fi

if in_list "$samples" "$project_dir"; then
  read -p "Are you sure you want to create a project from sample '$sample' in the directory '$project_dir'? [yN] " -n 1 -r >&2
  echo >&2
  if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    exit 1
  fi
fi

if [[ -d "$project_dir" ]]; then
  read -p "The directory '$project_dir' already exists! Are you sure you want to create a new pipeline project there? [yN] " -n 1 -r >&2
  echo >&2
  if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    exit 1
  fi
fi

source "$script_dir/.project-util.sh"
copy_project_directory "$script_dir/core" "$project_dir"
copy_project_directory "$script_dir/core/.pipeline-template" "$project_dir/.pipeline-template"
copy_project_directory "$script_dir/$sample" "$project_dir"

echo "Project $project_name created successfully!"
echo
echo 'Next steps:'
echo
echo ' 1. Change to the project directory:'
echo
echo "        cd $project_dir"
echo
echo ' 2. Start the playground containers:'
echo
echo '        docker/start.sh'
echo
echo " 3. Configure the playground:"
echo
echo '        ./ansible-playbook site.yml'
echo
