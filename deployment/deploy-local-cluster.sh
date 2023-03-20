#!/usr/bin/env bash
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

chmod +x "$SCRIPT_DIR/build-docker-image.sh"
bash "$SCRIPT_DIR/build-docker-image.sh"

docker compose -f "$SCRIPT_DIR/docker/docker-compose.yml" up -d
