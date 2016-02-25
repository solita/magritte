function replace_project {
  sed -e "s/PROJECT/$project_name/g"
}

function copy_project_directory {
  local source="$1"
  local destination=$(echo "$2"| replace_project)
  mkdir -p "$destination"
  local destination="$(cd "$destination" && pwd)"
  (
    shopt -s nullglob
    cd $source
    for x in *; do
      if [[ -d "$x" ]]; then
        copy_project_directory "$source/$x" "$destination/$x"
      else
        local source_file="$x"
        local destination_file="$(echo "$destination/$x" | replace_project)"
        cat "$source_file" | replace_project >"$destination_file"
        chmod --reference "$source_file" "$destination_file"
      fi
    done
  )
}
