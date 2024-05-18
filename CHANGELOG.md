# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.1.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [v0.8.0](https://github.com/Josef-Friedrich/engine-omega/releases/tag/v0.8.0) - 2024-05-18

<small>[Compare with v0.7.0](https://github.com/Josef-Friedrich/engine-omega/compare/v0.7.0...v0.8.0)</small>

### Added

- Add package-info.java to all packages ([dae1bdd](https://github.com/Josef-Friedrich/engine-omega/commit/dae1bdd32248bc2fa806bbc9dd29ba548679473d) by Josef Friedrich).
- Add new methode overload: ImageUtil#replaceColor(BufferedImage, Color, Color) ([198becc](https://github.com/Josef-Friedrich/engine-omega/commit/198beccc580bff3542821531db35246f2501ddee) by Josef Friedrich).

### Fixed

- Fix color decode for string arrays ([08fa0cd](https://github.com/Josef-Friedrich/engine-omega/commit/08fa0cdc058d2a6a1898e3f6ed476dbe0ea47756) by Josef Friedrich).

## [v0.7.0](https://github.com/Josef-Friedrich/engine-omega/releases/tag/v0.7.0) - 2024-05-14

<small>[Compare with v0.6.0](https://github.com/Josef-Friedrich/engine-omega/compare/v0.6.0...v0.7.0)</small>

### Added

- Add new method `ImageUtil.replaceColor()` ([f310344](https://github.com/Josef-Friedrich/engine-omega/commit/f31034449583dcaf489cf3959123cd0c3ec24d32) by Josef Friedrich).
- Add resources management code from the LITIENGINE ([bc8af0a](https://github.com/Josef-Friedrich/engine-omega/commit/bc8af0a8e3406179f80ca5e691006aedfc4e29c7) by Josef Friedrich).
- Add the tween engine from the LITIENGINE ([ae8965a](https://github.com/Josef-Friedrich/engine-omega/commit/ae8965a31ab7ebfad0206069b39942c563c88531) by Josef Friedrich).

### Changed

- ResourceLoader is now in the resouces package

### Removed

- ImageLoader -> ImagesContainer
- Optimizer -> ImageUtil

## [v0.6.0](https://github.com/Josef-Friedrich/engine-omega/releases/tag/v0.6.0) - 2024-05-13

<small>[Compare with v0.5.0](https://github.com/Josef-Friedrich/engine-omega/compare/v0.5.0...v0.6.0)</small>

### Added

- Add static global frame update listener ([2866890](https://github.com/Josef-Friedrich/engine-omega/commit/286689046998c9a24413bcd076624f6b8e00b055) by Josef Friedrich).

### Changed

- Change the input and output format of color methods dealing with hexadecimal colors
  from `AARRGGBB` to `RRGGBBAA`
  ([51dfa0f](https://github.com/Josef-Friedrich/engine-omega/commit/51dfa0f257c8dc75ad93afd151b32fd2ce74a8a9) by Josef Friedrich).

## [v0.5.0](https://github.com/Josef-Friedrich/engine-omega/releases/tag/v0.5.0) - 2024-05-12

<small>[Compare with v0.4.0](https://github.com/Josef-Friedrich/engine-omega/compare/v0.4.0...v0.5.0)</small>

### Added

- Add color and math util from the LITIengine ([efd7ca3](https://github.com/Josef-Friedrich/engine-omega/commit/efd7ca31b476ce649addffcddb16bc356d52c0da) by Josef Friedrich).
- Add ImageUtil from the LITIengine ([99b9beb](https://github.com/Josef-Friedrich/engine-omega/commit/99b9beb50e85d5c796b5e6959aaadb9c30e03f4a) by Josef Friedrich).
- Add the LITIengine sound engine ([f687c1a](https://github.com/Josef-Friedrich/engine-omega/commit/f687c1a4233f1c9c56acc4567da8b44471e8c3b2) by Josef Friedrich).

## [v0.4.0](https://github.com/Josef-Friedrich/engine-omega/releases/tag/v0.4.0) - 2024-05-09

<small>[Compare with v0.3.0](https://github.com/Josef-Friedrich/engine-omega/compare/v0.3.0...v0.4.0)</small>

### Changed

- Combine all event specific classes in the package event. `PeriodicTask`,
  `SingleTask` and `FrameUpdateListener` are now located in the package `event`
  ([5f22fd3](https://github.com/Josef-Friedrich/engine-omega/commit/5f22fd3763a7c5ca99626a93694d5cfb07c7f230) by Josef Friedrich)
- Extend class `PeriodicTask`
  ([986e951](https://github.com/Josef-Friedrich/engine-omega/commit/986e951e79753c32f72de35d41dcf2e64267b352) by Josef Friedrich)
- Rewrite global static keyboard listener. `Game.addKeyListener()` and `Game.removeKeyListener()` behave now
  like `scene.addKeyListener()` or `actor.addKeyListener()`.
  ([21574f4](https://github.com/Josef-Friedrich/engine-omega/commit/21574f482e689d1a06b4700f475ddc1037fb0317) by Josef Friedrich)

## [v0.3.0](https://github.com/Josef-Friedrich/engine-omega/releases/tag/v0.3.0) - 2024-05-08

<small>[Compare with v0.2.0](https://github.com/Josef-Friedrich/engine-omega/compare/v0.2.0...v0.3.0)</small>

### Added

- Add static methods addKeyListener and removeKeyListener to the Game class ([393cf54](https://github.com/Josef-Friedrich/engine-omega/commit/393cf543fc4386a9852f2f2f860b476af9cfb99a) by Josef Friedrich).

### Changed

-  Use double instead of float wherever possible
([157033f](https://github.com/Josef-Friedrich/engine-omega/commit/157033fac6c7fcb3159ed8d24ea180908d72cd1a) by Josef Friedrich).
- Change coding style and reformat code using mvn formatter:format [8eb6274](https://github.com/Josef-Friedrich/engine-omega/commit/8eb627475b1faa07d44c7a14c1e007340d5c1164)

## [v0.2.0](https://github.com/Josef-Friedrich/engine-omega/releases/tag/v0.2.0) - 2024-04-28

<small>[Compare with v0.1.0](https://github.com/Josef-Friedrich/engine-omega/compare/v0.1.0...v0.2.0)</small>

### Added

- Add new logos ([3c27d75](https://github.com/Josef-Friedrich/engine-omega/commit/3c27d756fb5f6509e0363c6c0671c05843ef8bbc) by Josef Friedrich).
- Add badge ([abdaeb4](https://github.com/Josef-Friedrich/engine-omega/commit/abdaeb4a1b598a2c3707d801b2c9ee23ecf23090) by Josef Friedrich).
- Add new constructor for the Image actor class ([74af5c2](https://github.com/Josef-Friedrich/engine-omega/commit/74af5c28487c0d24b454c65a070960ed1c4f40f5) by Josef Friedrich).


## [v0.1.0](https://github.com/Josef-Friedrich/engine-omega/releases/tag/v0.1.0) - 2024-04-28

<small>[Compare with first commit](https://github.com/Josef-Friedrich/engine-omega/compare/6ae5809945fc348ae76714907e3aaca2ebc66bb7...v0.1.0)</small>

### Changed

- Change `groupId` from `ea` to `rocks.friedrich.engine_omega` ([8ff4b97](https://github.com/Josef-Friedrich/engine-omega/commit/8ff4b97bb88af3a05517373193cc7b047e2343ae) by Josef Friedrich)
