#!/bin/bash
# Find and run the Spring Boot JAR file
JAR_FILE=$(find target -name '*.jar' -not -name '*-sources.jar' -not -name '*-javadoc.jar' | head -1)
if [ -z "$JAR_FILE" ]; then
    echo "Error: JAR file not found in target directory"
    ls -la target/ || echo "target directory does not exist"
    exit 1
fi
echo "Starting application with JAR: $JAR_FILE"
java -jar "$JAR_FILE"
