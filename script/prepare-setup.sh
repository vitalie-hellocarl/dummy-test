#!/usr/bin/env sh
PROJECT_ROOT_DIR="$(pwd -P)"

# Parameters order:
# 1. ENV_NAME -> development,production or qa
# 2. ENV_PROP_FILE -> base64 encoded properties file representing the EVN to build
# 3. ENV_SIGNING_PROP_FILE -> base64 encoded properties file with signature information for ENV
# 4. ENV_SIGNING_KEYSTORE_FILE -> base64 encoded keystore file for ENV
ENV_NAME=$1
ENV_PROP_FILE=$2
ENV_SIGNING_PROP_FILE=$3
ENV_SIGNING_KEYSTORE_FILE=$4

#environment
echo "Generating environment config"
ENV_DIR="$PROJECT_ROOT_DIR/environment/$ENV_NAME"
echo "Creating path: $ENV_DIR"
mkdir -p "$ENV_DIR"
echo "Writing env properties-----"
echo "$ENV_PROP_FILE" | base64 -d > "$ENV_DIR/env-$ENV_NAME.properties"
echo "Finished generating environment config"

#signing
echo "Generating signing config"
SIGNING_DIR="$PROJECT_ROOT_DIR/signing"
SIGNING_DIR_ENV_NAME="$SIGNING_DIR/$ENV_NAME"
echo "Creating path: $SIGNING_DIR"
mkdir -p "$SIGNING_DIR/development"
mkdir -p "$SIGNING_DIR/production"
mkdir -p "$SIGNING_DIR/qa"
echo "empty" > "$SIGNING_DIR/development/carl-development-keystore.properties"
echo "empty" > "$SIGNING_DIR/production/carl-development-keystore.properties"
echo "empty" > "$SIGNING_DIR/qa/carl-qa-keystore.properties"
rm -f "$SIGNING_DIR_ENV_NAME/carl-$ENV_NAME-keystore.properties"
echo "Writing signing files"
echo "$ENV_SIGNING_PROP_FILE" | base64 -d > "$SIGNING_DIR_ENV_NAME/carl-$ENV_NAME-keystore.properties"
echo "$ENV_SIGNING_KEYSTORE_FILE" | base64 -d > "$SIGNING_DIR_ENV_NAME/carl-$ENV_NAME-key.keystore"
echo "Finished writing singing"