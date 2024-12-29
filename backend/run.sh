#!/bin/bash


echo "starting ngrok app....."


ngrok http 8080 > ngrok.log &

sleep 3

GENERATED_URL=$(grep -0 'https://[a-zA-Z0-9.-]*\.ngrok-free\.app' ngrok.log | head -n 1)

if [ -z "$GENERATED_URL" ]; then
	echo "Failed to retrieve url"
	#nano ngrok.log
	exit 0
fi

export ngrokHost=$GENERATED_URL

echo "ngrok host : [$ngrokHost]"

echo "Starting spring boot app : 8080 "

mvn spring-boot:run
