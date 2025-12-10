#!/bin/bash

# Check if a commit message was provided
if [ -z "$1" ]; then
    echo "❌ Error: You must provide a commit message."
    echo "Usage: ./git-auto.sh \"your message\""
    exit 1
fi

MESSAGE="$1"

echo "👉 Watching for changes... (Ctrl+C to stop)"
echo ""


echo "Adding files to the stage area ..."

git add .

echo "Commit message to save changes "
git commit -m "$MESSAGE"

echo "Commit done: $MESSAGE"
echo ""
