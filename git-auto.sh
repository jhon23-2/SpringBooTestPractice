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

# MacOS uses fswatch
if command -v fswatch >/dev/null 2>&1; then
    WATCHER="fswatch -o ."
else
    echo "❌ ERROR: You need to install fswatch: brew install fswatch"
    exit 1
fi

$WATCHER | while read CHANGE
do
    echo "🔄 Change detected! Committing..."

    git add .

    git commit -m "$MESSAGE"

    echo "✔ Commit done: $MESSAGE"
    echo ""
done
