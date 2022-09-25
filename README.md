The java buildpack is hacked together from the CNBP samples repo and tutorials.

# chainguard-stack

``` sh
cd stack/wolfi
docker build --platform=amd64 -t "chainguard/stack-base:wolfi" "./base"
 
docker build --platform=amd64 --build-arg "base_image=chainguard/stack-base:wolfi" --build-arg "stack_id=wolfi-base-stack" -t "chainguard/stack-build:wolfi" "./build"
 
docker build --platform=amd64 --build-arg "base_image=chainguard/stack-base:wolfi" --build-arg "stack_id=wolfi-base-stack" -t "chainguard/stack-run:wolfi" "./run"

pack builder create chainguard-builder:wolfi --config ./builder.toml

cd ../../sample/recaptcha-demo
pack build recaptcha-demo --builder chainguard-builder:wolfi

docker run --rm -p 8080:8080 recaptcha-demo
```
