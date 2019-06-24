# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/).

## [Unreleased]

##[0.1.2]
### Added
- tests
- Now you can configure this module to specify where does the necessary data come from.

### Changed
- Changed the config key provided. Now `{:keycloak {:clientId ...}` became `{:keycloak {:client-id ...}}`

## [0.1.1] - 2019-06-19
### Added
- Config necessary for backend token verification
- Document that the module needs some env variables to be set in order to work properly
- Document that the module adds some Integrant keys to the config, and what they are used for.

## [0.1.0] - 2019-06-10

### Added
- Initial version!

[0.1.0]: https://github.com/magnetcoop/hydrogen.module.session.keycloak/releases/tag/v0.1.0
[0.1.1]: https://github.com/magnetcoop/hydrogen.module.session.keycloak/compare/v0.1.0...v0.1.1/
[0.1.2]: https://github.com/magnetcoop/hydrogen.module.session.keycloak/compare/v0.1.1...v0.1.2/
[UNRELEASED]: https://github.com/magnetcoop/hydrogen.module.session.keycloak/compare/v0.1.2...HEAD/