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


echo "🧪 Running Maven tests..."
mvn test
TEST_EXIT_CODE=$?

echo "📊 Exit code from Maven: $TEST_EXIT_CODE"

if [ "$TEST_EXIT_CODE" -ne 0 ]; then 
     echo "❌ Tests failed. Commit CANCELLED."
     exit 1	
fi

echo "✅ All tests passed. Committing changes..."



git add .
git commit -m "$MESSAGE"

echo "✔ Commit completed successfully with message: $MESSAGE"
echo ""
