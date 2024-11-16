#!/bin/bash

image="cat_image.jpg"

cat_image_url=$(wget -qO- https://api.thecatapi.com/v1/images/search | jq -r '.[0].url')

wget -q $cat_image_url -O "$image"

catimg "$image"

Chuck_joke=$(wget -qO- https://api.chucknorris.io/jokes/random | jq -r '.value')

echo "$Chuck_joke"

rm -f "$image"