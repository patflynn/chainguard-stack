The java buildpack is hacked together from the CNBP samples repo and tutorials.

# chainguard-stack

First you'll need to install [pack](https://buildpacks.io/docs/tools/pack/) and [jam](https://github.com/paketo-buildpacks/jam).

``` sh
cd stack/wolfi
jam create-stack --config stack.toml --build-output build.oci --run-output run.oci
skopeo copy oci-archive:run.oci docker://<registry>/wolfi-stack-base-run
skopeo copy oci-archive:build.oci docker://<registry>/wolfi-stack-base-build

cd ../../builder
pack builder create <registry>/wolfi-base-builder --config ./builder.toml

cd ../sample/recaptcha-demo
pack build recaptcha-demo --builder <registry>/wolfi-base-builder

docker run --rm -p 8080:8080 recaptcha-demo
```
