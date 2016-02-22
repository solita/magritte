function image_exists {
  docker history $1 >/dev/null 2>&1
}

function ensure_image_created {
  if image_exists $1; then
    if [[ ${silent:-0} -eq 0 ]]; then
      echo "Image $1 already created."
    fi
  else
    if [[ ${silent:-0} -eq 0 ]]; then
      echo "Creating image $1..."
    fi
    docker build --tag $1 $2
  fi
}

function ensure_image_destroyed {
  if ! image_exists $1; then
    echo "Image $1 already destroyed."
  else
    echo "Destroying image $1..."
    docker rmi $1 >/dev/null
  fi
}

function container_exists {
  [[ -n "$(docker ps -aqf "name=$1")" ]]
}

function container_running {
  [[ -n "$(docker ps -qf "name=$1")" ]]
}

function ensure_container_created {
  if container_exists $1; then
    if [[ ${silent:-0} -eq 0 ]]; then
      echo "Container $1 already created."
    fi
  else
    if [[ ${silent:-0} -eq 0 ]]; then
      echo "Creating container $1..."
    fi
    docker create --name $@ >/dev/null
  fi
}

function ensure_container_running {
  if container_running $1; then
    if [[ ${silent:-0} -eq 0 ]]; then
      echo "Container $1 already running."
    fi
  else
    if [[ ${silent:-0} -eq 0 ]]; then
      echo "Starting container $1..."
    fi
    docker start $1 >/dev/null
  fi
}

function ensure_container_stopped {
  if ! container_running $1; then
    echo "Container $1 already stopped."
  else
    echo "Stopping container $1..."
    docker stop $1 >/dev/null
  fi
}

function ensure_container_destroyed {
  if ! container_exists $1; then
    echo "Container $1 already destroyed."
  else
    echo "Destroying container $1..."
    docker rm $1 >/dev/null
  fi
}
