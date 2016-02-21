#!/bin/bash

cd "$(dirname "$BASH_SOURCE")"

# See: https://github.com/ansible/ansible/issues/6466#issuecomment-65454871
rm -rf /etc/ansible/roles/*
