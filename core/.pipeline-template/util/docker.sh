function image_exists {
  docker history $1 >/dev/null 2>&1
}

function container_exists {
  [[ -n "$(docker ps -aqf "name=$1" 2>/dev/null)" ]]
}

function container_running {
  [[ -n "$(docker ps -qf "name=$1" 2>/dev/null)" ]]
}

function docker_host_ip {
  if [[ -n "${DOCKER_MACHINE_NAME:-}" ]]; then
    docker-machine ip $DOCKER_MACHINE_NAME
  else
    echo 127.0.0.1
  fi
}
