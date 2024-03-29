## NOTE: This repo got deprecated. We migrated to [hydrogen.duct-template](https://github.com/magnetcoop/hydrogen.duct-template)

# hydrogen.module for Duct

It implements a module for [Duct](https://github.com/duct-framework/duct).
`hydrogen.module.session.keycloak` manages communication between Hydrogen app and
a [Keycloak](https://www.keycloak.org/) server in order provide session management.

## Installation

[![Clojars Project](https://img.shields.io/clojars/v/hydrogen/module.session.keycloak.svg)](https://clojars.org/hydrogen/module.session.keycloak)

## Usage

```edn
{:hydrogen.module/core {}
 :hydrogen.module/session.keycloak {}}
```

And a more realistic example:
```edn
{:hydrogen.module/core
  {:externs-paths {:production ["src/my-app/client/externs.js"
                                "src/my-app/client/google_maps_api_v3_36.js"]
                   :development ["oksol/client/google_maps_api_v3_36.js"]}}
 :hydrogen.module/session.keycloak {}}
```

##### This module will need those env variables to be set in order to work properly:
1. OIDC_ISSUER_URL
2. OIDC_AUDIENCE
3. OIDC_JWKS_URI

The module merges the following keys to system configuration:

1. `:project-ns.api/config`: it provides the above env variables values to the front-end
2. `:magnet.buddy-auth/jwt-oidc`: it provides a function that implements `:duct.middleware.buddy/authentication` compatible JWT token validation for OpenID Connect ID Tokens.
3. `:duct.middleware.buddy/authentication`: it provides a Ring-compatible middleware that enables authentication using OpenID Connect ID Tokens.

You will need to reference the `:duct.middleware.buddy/authentication` key from the routes' handlers keys where you want to use authentication. E.g.:

### Additional options

- **Externs**\[1\] - to configure them use `:externs-paths` option. It accepts two formats:
    - `{:externs-paths ["a.js" "b.js"]}` - this would apply both files as externs both in development environment
     (as part of `:duct.server/figwheel` config)
     and in production environment
     (as part of `:duct.compiler/cljs` config).
    - `{:externs-paths {:production ["a.js"] :development ["x.js" "y.js"]}}`
- This module is used by Hydrogen CE and by [Hydrogen duct template](https://github.com/magnetcoop/hydrogen.duct-template).
For this reason it usually starts with `:add-example-api? true` option to make running demo more effortless. The default for this option is `false` so there's probably nothing for you to care about :)
 
#### Note
Figwheel expects files with .js extension inside its source
directories to be a foreign library. And foreign libraries **MUST**
declare a namespace. In fact, figwheel assumes it, and if it
doesn't find it and can't map the file back to a source  file,
it bombs out with a NullPointerException.

So even if this is *NOT* a foreign library, but just an externs file,
add a namespace declaration to prevent figwheel from crashing.

Like this: `goog.provide('google.maps');`

## License

Copyright (c) 2019 Magnet S Coop.

The source code for the library is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/.
