#!/bin/sh

set -o allexport

export DATABASE_URI=$(echo "$DATABASE_URL" | awk -F'[:/@]' '{print "jdbc:postgresql://" $6 ":" $7 "/" $8 "?user=" $4 "&password=" $5}')
export DATABASE_USER=$(echo "$DATABASE_URL" | awk -F'[:/@]' '{print $4}')
export DATABASE_PASSWORD=$(echo "$DATABASE_URL" | awk -F'[:/@]' '{print $5}')

set +o allexport
