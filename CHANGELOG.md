# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.1.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [v0.40.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.40.0) - 2025-11-16

<small>[Compare with v0.39.0](https://github.com/engine-pi/engine-pi/compare/v0.39.0...v0.40.0)</small>

### Changed

- Split the `dsa/turtle` package into more classes.

### Added

- Add more classes to the package `graphics/boxes`.

## [v0.39.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.39.0) - 2025-11-10

<small>[Compare with v0.38.0](https://github.com/engine-pi/engine-pi/compare/v0.38.0...v0.39.0)</small>

### Fixed

- Fix the loading of the default fonts
- Add BlueJ template files to the zip file

## [v0.38.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.38.0) - 2025-11-08

<small>[Compare with v0.37.0](https://github.com/engine-pi/engine-pi/compare/v0.37.0...v0.38.0)</small>

### Added

- Add class `Turtle` to draw turtle grapics
- Package `graphics/boxes`

### Changed

- Use lowercase attribute names for the static resource containers (e.g. `Resources.FONTS` -> `Resources.fonts`)

### Removed

- Remove the [Graphics and Games](https://gng4java.informatikschulbuch.de/allclasses.html) engine from Cornelsen as `little_engine`.

## [v0.37.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.37.0) - 2025-11-04

<small>[Compare with v0.36.0](https://github.com/engine-pi/engine-pi/compare/v0.36.0...v0.37.0)</small>

### Added

- Add new module `build-tools` containing build resources.
- Include the games `tetris`, `pacman` and `blockly-robot` as submodules
- Add new method `renderOverlay` in the class `Scene`

### Changed

- New folder structure for the multi module setup

## [v0.36.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.36.0) - 2025-10-26

<small>[Compare with v0.35.0](https://github.com/engine-pi/engine-pi/compare/v0.35.0...v0.36.0)</small>

### Added

- Add package `dsa/graph`. `dsa` = Data Structures and Algorithms to visualize graphs.

## [v0.35.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.35.0) - 2025-10-19

<small>[Compare with v0.34.0](https://github.com/engine-pi/engine-pi/compare/v0.34.0...v0.35.0)</small>

### Added

- New actor: Counter
- New methods .hide() and .show() on the Actor objects

## [v0.34.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.34.0) - 2025-10-17

<small>[Compare with v0.33.0](https://github.com/engine-pi/engine-pi/compare/v0.33.0...v0.34.0)</small>

### Added

- More instant actors: Circle, Text
- BlueJ template as a build target

### Changed

- Renamed `InstantController` into Controller

## [v0.33.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.33.0) - 2025-10-12

<small>[Compare with v0.32.0](https://github.com/engine-pi/engine-pi/compare/v0.32.0...v0.33.0)</small>

### Added

- Add instant mode.

## [v0.32.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.32.0) - 2025-10-05

<small>[Compare with v0.31.0](https://github.com/engine-pi/engine-pi/compare/v0.31.0...v0.32.0)</small>

### Changed

- Translate more methods and attributes of the `little_engine` into English.

## [v0.31.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.31.0) - 2025-09-29

<small>[Compare with v0.30.0](https://github.com/engine-pi/engine-pi/compare/v0.30.0...v0.31.0)</small>

### Changed

- Translate some methods and attributes of the `little_engine` into English.

## [v0.30.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.30.0) - 2025-09-28

<small>[Compare with v0.29.0](https://github.com/engine-pi/engine-pi/compare/v0.29.0...v0.30.0)</small>

### Added

- Add the [Graphics and Games](https://gng4java.informatikschulbuch.de/allclasses.html) engine from Cornelsen as `little_engine`.

## [v0.29.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.29.0) - 2024-08-05

<small>[Compare with v0.28.0](https://github.com/engine-pi/engine-pi/compare/v0.28.0...v0.29.0)</small>

### Changed

- Use jbox2d from https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/jbox2d-library ([64865e15](64865e15f89b96c36ef0d109094f8d6688a023eb) by Josef Friedrich).
- Update eclipse formatter ([38e30075](38e3007568796025a37b0d6f89b14d6f52ee45a7) by Josef Friedrich).

## [v0.28.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.28.0) - 2024-08-01

<small>[Compare with v0.27.0](https://github.com/engine-pi/engine-pi/compare/v0.27.0...v0.28.0)</small>

### Added

- Add new methods to the Camera ([68c15fa](https://github.com/engine-pi/engine-pi/commit/68c15fa0369b4eb4bc6d1e7e9c63c166034c08a8) by Josef Friedrich).

### Removed

- Remove some addRectangle methods ([5821efb](https://github.com/engine-pi/engine-pi/commit/5821efb26395bdc52b4bf39a431615154b8f73df) by Josef Friedrich).
- Remove some addCircle overloads ([c6b6965](https://github.com/engine-pi/engine-pi/commit/c6b6965cf5fa46c91d5dafb690955a6a4bb560db) by Josef Friedrich).

## [v0.27.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.27.0) - 2024-07-29

<small>[Compare with v0.26.0](https://github.com/engine-pi/engine-pi/compare/v0.26.0...v0.27.0)</small>

### Added

- New classes `actor/ImageFontGlyph`, `actor/ImageFontSpecimen`
  `actor/StatefulAnimatedGifAnimation.java`
  `actor/StatefulImagesPrefixAnimation.java`
  `actor/StatefulSpritesheetAnimation.java`
- New method Actor#toggleVisible() ([58303071](58303071ab9acda1392df125ea812f14518f5afa) by Josef Friedrich).
- Implement Game#setWindowPosition(Direction) ([e2e928b8](e2e928b8ffabe7da762241ef7a9bb38b9a512d4b) by Josef Friedrich).

### Changed

- Refactor coordinate system drawing ([fe6563ba](fe6563ba2abc8042d06c7462287fe90cb04c982b) by Josef Friedrich).
- Create more jar files jar-with-dependencies ([30d8be42](30d8be42ee5db3dca5674bf9c64c97e72582617e) by Josef Friedrich).

### Fixed

- Fix image font ([fa6438a](https://github.com/engine-pi/engine-pi/commit/fa6438aa75ae18f7e3badb1091b2f745711d129b) by Josef Friedrich).

## [v0.26.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.26.0) - 2024-07-16

<small>[Compare with v0.25.0](https://github.com/engine-pi/engine-pi/compare/v0.25.0...v0.26.0)</small>

### Added

- Add new class `ColorContainerVisualizer` ([8bb5ca7](https://github.com/engine-pi/engine-pi/commit/8bb5ca72933209822fe1160fa8f8a964c7df2f2d) by Josef Friedrich).
- Add method overload ColorContainer.add(String, int, int, int, int) ([3719d25](https://github.com/engine-pi/engine-pi/commit/3719d2548254ee4e0a882f12d6b016db59172749) by Josef Friedrich).
- Add new overload for Animation.createFromImages ([5a05c09](https://github.com/engine-pi/engine-pi/commit/5a05c09c7c1e06c7adcf0142b733808e30d1a477) by Josef Friedrich).
- Add new class `LetterTileMap` ([3d5f3a6](https://github.com/engine-pi/engine-pi/commit/3d5f3a62bc79e49b2590de3a940dead579868124) by Josef Friedrich).
- Add new class `StateFulImagesAnimation`
- Add new class `NamedColor`

### Changed

- Default zoom is new 32 instead of 30 Pixel per meter.

## [v0.25.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.25.0) - 2024-07-12

<small>[Compare with v0.24.0](https://github.com/engine-pi/engine-pi/compare/v0.24.0...v0.25.0)</small>

### Added

- Add the eclipse formatter to the resources ([923d11e](https://github.com/engine-pi/engine-pi/commit/923d11e0b396d24e31cf93df8d4311b3c2fb1d9d) by Josef Friedrich).
- Start main animation with Game.start(); ([3051bd0d](3051bd0d18b2d34fdf889bdd7d28a42615abc42d) by Josef Friedrich).
- Implement global pixel multiplication (preview feature) ([a0195152](a019515235380a05c0e04a874017574c954b9b5b) by Josef Friedrich).

### Fixed

- Fix ImageFontText ([979cc51](https://github.com/engine-pi/engine-pi/commit/979cc5136b0752ae766dd781b81b8ff16dfa194f) by Josef Friedrich).

## [v0.24.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.24.0) - 2024-07-10

<small>[Compare with v0.23.0](https://github.com/engine-pi/engine-pi/compare/v0.23.0...v0.24.0)</small>

### Changed

- Refactor the actor Image ([a2aa4b3f](a2aa4b3f66aef367a7b849a34e3d7d91e7434d14) by Josef Friedrich).

### Fixed

- Fix overload ImageContainer.get() not accepting a Color array  ([86960c0](https://github.com/engine-pi/engine-pi/commit/86960c0463bbc111914c7d3a69fb833344e1fadf) by Josef Friedrich).

## [v0.23.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.23.0) - 2024-07-09

<small>[Compare with v0.22.0](https://github.com/engine-pi/engine-pi/compare/v0.22.0...v0.23.0)</small>

### Added

- Add new get overloads in the class ImageContainer ([99b76f8](https://github.com/engine-pi/engine-pi/commit/99b76f88f9e902de69e99d927b67ac3dd55035fe) by Josef Friedrich).
- Add new a actor named ImageFontText ([6213ae9](https://github.com/engine-pi/engine-pi/commit/6213ae9fba9801778eb8634aee5523069c46f66d) by Josef Friedrich).
- Extend the TextUtils ([58648e9](https://github.com/engine-pi/engine-pi/commit/58648e98a6519d194c1f0c2af33b6d230ef5844a) by Josef Friedrich).

## [v0.22.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.22.0) - 2024-07-02

<small>[Compare with v0.21.0](https://github.com/engine-pi/engine-pi/compare/v0.21.0...v0.22.0)</small>

### Changed

-  Make the class Jukebox static and move it to the root package ([6e911dd](https://github.com/engine-pi/engine-pi/commit/6e911dd5f2ff09bdd90813d6bd9a3453e419d4aa) by Josef Friedrich).

## [v0.21.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.21.0) - 2024-06-27

<small>[Compare with v0.20.0](https://github.com/engine-pi/engine-pi/compare/v0.20.0...v0.21.0)</small>

### Added

- Add more method overloads for the repeat method of the interface FrameUpdateListenerRegistration ([6efbce8](https://github.com/engine-pi/engine-pi/commit/6efbce8faa74047803a8c568e0d0c623cba9f09f) by Josef Friedrich).

## [v0.20.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.20.0) - 2024-06-25

<small>[Compare with v0.19.0](https://github.com/engine-pi/engine-pi/compare/v0.19.0...v0.20.0)</small>

### Added

- Add counter in the class PeriodicTask ([bd6cf0d](https://github.com/engine-pi/engine-pi/commit/bd6cf0d847f6d3a10ece9da32ff4bd23db9fec92) by Josef Friedrich).
- Import the font container from the LITIENGINE ([bd6cf0d](695e04d57effc2977344b35e21e78bb25214c27a) by Josef Friedrich).

### Changed

- Improve main animation ([3118f26](3118f266e63ab4f6c123638773ab3aa0e282a755) by Josef Friedrich).

## [v0.19.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.19.0) - 2024-06-24

<small>[Compare with v0.18.0](https://github.com/engine-pi/engine-pi/compare/v0.18.0...v0.19.0)</small>

### Changed

- Extend the class PeriodicTask ([ac3aa2f](https://github.com/engine-pi/engine-pi/commit/ac3aa2fee8cfb61238fb8d669109a115f8d8c566) by Josef Friedrich).
- Rename `Game.getMousePositionInCurrentScene()` into `Game.getMousePosition()` ([df75ced](https://github.com/engine-pi/engine-pi/commit/df75cedc9fc78ddd9bb82c3b3f17f27939d56e7a) by Josef Friedrich).
- Rename the create..() methods into add..() ([6099302](https://github.com/engine-pi/engine-pi/commit/6099302dc2f5ed404e414b789cc261e9ba447808) by Josef Friedrich).

## [v0.18.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.18.0) - 2024-06-23

<small>[Compare with v0.17.0](https://github.com/engine-pi/engine-pi/compare/v0.17.0...v0.18.0)</small>

### Added

- Add custom javadoc CSS stylesheet ([3b33f87](https://github.com/engine-pi/engine-pi/commit/3b33f87ba6818dd6b6e45a41d465cfc3d72aa4b9) by Josef Friedrich).
- Add new logo ([b1ee1c2](https://github.com/engine-pi/engine-pi/commit/b1ee1c216d911df3cb4e3e88e75c0f4dd4910ab3) by Josef Friedrich).
- new enum `ColorSchemeSelection` ([afda4c6](https://github.com/engine-pi/engine-pi/commit/afda4c6f93f4c444f105d631fec11fe1447d5d57) by Josef Friedrich).

### Changed

- Convert project in a monorepo with the three Maven packages `engine-pi-meta` `engine-pi` `engine-pi-demos` ([9f338eb](https://github.com/engine-pi/engine-pi/commit/9f338eb7567af4975d0a42712b1448ed7a537d2d) by Josef Friedrich).

## [v0.17.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.17.0) - 2024-06-17

<small>[Compare with v0.16.0](https://github.com/engine-pi/engine-pi/compare/v0.16.0...v0.17.0)</small>

### Added

- Calculate the average color of images to draw a shape in complementary colors ([618284b](https://github.com/engine-pi/engine-pi/commit/618284b91d08b9e3b071ade3763d0a7afb43d9be) by Josef Friedrich).
- Alt+A: Add a default shortcut to hide or show the actors fill color or image ([1859ae7](https://github.com/engine-pi/engine-pi/commit/1859ae7d6b7d45d2b845873619ded7cb08910c8a) by Josef Friedrich).
- Add a info box to the debug view to show the current gravity vector ([5a1807a](https://github.com/engine-pi/engine-pi/commit/5a1807af662bf08bdef7fd5078289039ebff66f5) by Josef Friedrich).

## [v0.16.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.16.0) - 2024-06-13

<small>[Compare with v0.15.0](https://github.com/engine-pi/engine-pi/compare/v0.15.0...v0.16.0)</small>

### Fixed

- Fix EventListener handling ([3977572](https://github.com/engine-pi/engine-pi/commit/3977572e92c0ec29c1db709dbc5561a3388efe2b) by Josef Friedrich).

## [v0.15.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.15.0) - 2024-06-13

<small>[Compare with v0.14.0](https://github.com/engine-pi/engine-pi/compare/v0.14.0...v0.15.0)</small>

### Added

- Add SceneLaunchListener ([93eeeb7](https://github.com/engine-pi/engine-pi/commit/93eeeb7ebe373ec07d2c56ba04688947a463dea5) by Josef Friedrich).
- Add default shortcuts and default controls ([622363a](https://github.com/engine-pi/engine-pi/commit/622363a6613b93438364ebaa153abb8206cd1c88) by Josef Friedrich).
- Awake and sleep actors ([b9023ce](https://github.com/engine-pi/engine-pi/commit/b9023ceb0c892953989647d0234d94e6c0466e07) by Josef Friedrich).

### Changed

- Change logo ([f40d956](https://github.com/engine-pi/engine-pi/commit/f40d956c44c9be43a3edd18cce1bf8f3d2f00741) by Josef Friedrich).
- Rename MouseWheelListener into MouseScrollListener to avoid confusion with JDK version ([63fc357](https://github.com/engine-pi/engine-pi/commit/63fc3573e139d331b1d57915e7317673b4fbe7b0) by Josef Friedrich).
- Rename KeyListener into KeyStrokeListener to avoid confusing with java.awt.event.KeyListener ([5e438ad](https://github.com/engine-pi/engine-pi/commit/5e438ad5771ca6db116ff48ceaeb6fdd8fb9c0e2) by Josef Friedrich).

### Removed

- Remove dependency to Showcase ([f508f17](https://github.com/engine-pi/engine-pi/commit/f508f17601904411c11ef2a03913abdea82ea2a7) by Josef Friedrich).
- Remove static methods Game.getImages() Game.getSounds() ([20449a6](https://github.com/engine-pi/engine-pi/commit/20449a6f00e2560c6a57f2c6d183560a6e50e7b7) by Josef Friedrich).

## [v0.14.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.14.0) - 2024-06-08

<small>[Compare with v0.13.0](https://github.com/engine-pi/engine-pi/compare/v0.13.0...v0.14.0)</small>

### Changed

- Change the name of the engine from engine-omega into engine-pi pi stands for PIrckheimer-Gymnasium  ([4b28682](https://github.com/engine-pi/engine-pi/commit/4b28682348eaee71639249b6b106f9bceac99322) by Josef Friedrich).
- Change default branch ([8377779](https://github.com/engine-pi/engine-pi/commit/837777993e5dda56c0137e21ca3e728453343c82) by Josef Friedrich).

## [v0.13.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.13.0) - 2024-06-08

<small>[Compare with v0.12.0](https://github.com/engine-pi/engine-pi/compare/v0.12.0...v0.13.0)</small>

### Added

- Add new geometric actors ([938931c](https://github.com/engine-pi/engine-pi/commit/938931cd06b3ba2e3e0a42f67b2391525190f295) by Josef Friedrich).

## [v0.12.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.12.0) - 2024-06-07

<small>[Compare with v0.11.0](https://github.com/engine-pi/engine-pi/compare/v0.11.0...v0.12.0)</small>

### Added

- Add color schema ([22823d2](https://github.com/engine-pi/engine-pi/commit/22823d28cab4d001822ee7125171e99a077fdc7a) by Josef Friedrich).
- Add constructor without arguments for the Rectangle class ([6ef6ad7](https://github.com/engine-pi/engine-pi/commit/6ef6ad74072ba8561380810bc1f315d194dde791) by Josef Friedrich).
- Add new methods Scene#setGravityOfEarth and Layer#setGravityOfEarth ([1eefabd](https://github.com/engine-pi/engine-pi/commit/1eefabd511ec28d19fe018e6db74ddbc374c49cd) by Josef Friedrich).
- Add new actor: Triangle ([ad1a803](https://github.com/engine-pi/engine-pi/commit/ad1a8035f2367969708e70e1022f76506eb29dc2) by Josef Friedrich).

### Changed

- Rename the attribute zoom of the class Camera into meter ([1b668d5](https://github.com/engine-pi/engine-pi/commit/1b668d5b1f106f4a67ee2c2d6fb91124e4964a40) by Josef Friedrich)

## [v0.11.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.11.0) - 2024-06-01

<small>[Compare with v0.10.0](https://github.com/engine-pi/engine-pi/compare/v0.10.0...v0.11.0)</small>

### Added

- Add new action class: Grid ([3d129af](https://github.com/engine-pi/engine-pi/commit/3d129af5b9cb6c2739bf6532c418c665df87d486) by Josef Friedrich).
- Add setter for BodyTypes on Actor ([ee3aa43](https://github.com/engine-pi/engine-pi/commit/ee3aa43f46d1ecf0f44b452853a12641263e6acb) by Josef Friedrich).
- Add the source code of jbox2d to the project instead of the jar file ([fed9c77](https://github.com/engine-pi/engine-pi/commit/fed9c77640ec947181678cbb1450f2c606904b16) by Josef Friedrich)

## [v0.10.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.10.0) - 2024-05-30

<small>[Compare with v0.9.0](https://github.com/engine-pi/engine-pi/compare/v0.9.0...v0.10.0)</small>

### Added

- Add ResourceManipulator ([27fff2d](https://github.com/engine-pi/engine-pi/commit/27fff2d24d06a6bfb23df216d39150c5b658d3f5) by Josef Friedrich).

## [v0.9.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.9.0) - 2024-05-26

<small>[Compare with v0.8.0](https://github.com/engine-pi/engine-pi/compare/v0.8.0...v0.9.0)</small>

### Added

- Add PressedKeyRepeater ([cdd2304](https://github.com/engine-pi/engine-pi/commit/cdd2304f035cbcd35e48a64f86b49add14f5c82e) by Josef Friedrich).
- Add sound playback ([0eebe43](https://github.com/engine-pi/engine-pi/commit/0eebe43ac2c883817cde08c4b93552c468560a39) by Josef Friedrich).

## [v0.8.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.8.0) - 2024-05-18

<small>[Compare with v0.7.0](https://github.com/engine-pi/engine-pi/compare/v0.7.0...v0.8.0)</small>

### Added

- Add package-info.java to all packages ([dae1bdd](https://github.com/engine-pi/engine-pi/commit/dae1bdd32248bc2fa806bbc9dd29ba548679473d) by Josef Friedrich).
- Add new methode overload: ImageUtil#replaceColor(BufferedImage, Color, Color) ([198becc](https://github.com/engine-pi/engine-pi/commit/198beccc580bff3542821531db35246f2501ddee) by Josef Friedrich).

### Fixed

- Fix color decode for string arrays ([08fa0cd](https://github.com/engine-pi/engine-pi/commit/08fa0cdc058d2a6a1898e3f6ed476dbe0ea47756) by Josef Friedrich).

## [v0.7.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.7.0) - 2024-05-14

<small>[Compare with v0.6.0](https://github.com/engine-pi/engine-pi/compare/v0.6.0...v0.7.0)</small>

### Added

- Add new method `ImageUtil.replaceColor()` ([f310344](https://github.com/engine-pi/engine-pi/commit/f31034449583dcaf489cf3959123cd0c3ec24d32) by Josef Friedrich).
- Add resources management code from the LITIENGINE ([bc8af0a](https://github.com/engine-pi/engine-pi/commit/bc8af0a8e3406179f80ca5e691006aedfc4e29c7) by Josef Friedrich).
- Add the tween engine from the LITIENGINE ([ae8965a](https://github.com/engine-pi/engine-pi/commit/ae8965a31ab7ebfad0206069b39942c563c88531) by Josef Friedrich).

### Changed

- ResourceLoader is now in the resouces package

### Removed

- ImageLoader -> ImagesContainer
- Optimizer -> ImageUtil

## [v0.6.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.6.0) - 2024-05-13

<small>[Compare with v0.5.0](https://github.com/engine-pi/engine-pi/compare/v0.5.0...v0.6.0)</small>

### Added

- Add static global frame update listener ([2866890](https://github.com/engine-pi/engine-pi/commit/286689046998c9a24413bcd076624f6b8e00b055) by Josef Friedrich).

### Changed

- Change the input and output format of color methods dealing with hexadecimal colors
  from `AARRGGBB` to `RRGGBBAA`
  ([51dfa0f](https://github.com/engine-pi/engine-pi/commit/51dfa0f257c8dc75ad93afd151b32fd2ce74a8a9) by Josef Friedrich).

## [v0.5.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.5.0) - 2024-05-12

<small>[Compare with v0.4.0](https://github.com/engine-pi/engine-pi/compare/v0.4.0...v0.5.0)</small>

### Added

- Add color and math util from the LITIengine ([efd7ca3](https://github.com/engine-pi/engine-pi/commit/efd7ca31b476ce649addffcddb16bc356d52c0da) by Josef Friedrich).
- Add ImageUtil from the LITIengine ([99b9beb](https://github.com/engine-pi/engine-pi/commit/99b9beb50e85d5c796b5e6959aaadb9c30e03f4a) by Josef Friedrich).
- Add the LITIengine sound engine ([f687c1a](https://github.com/engine-pi/engine-pi/commit/f687c1a4233f1c9c56acc4567da8b44471e8c3b2) by Josef Friedrich).

## [v0.4.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.4.0) - 2024-05-09

<small>[Compare with v0.3.0](https://github.com/engine-pi/engine-pi/compare/v0.3.0...v0.4.0)</small>

### Changed

- Combine all event specific classes in the package event. `PeriodicTask`,
  `SingleTask` and `FrameUpdateListener` are now located in the package `event`
  ([5f22fd3](https://github.com/engine-pi/engine-pi/commit/5f22fd3763a7c5ca99626a93694d5cfb07c7f230) by Josef Friedrich)
- Extend class `PeriodicTask`
  ([986e951](https://github.com/engine-pi/engine-pi/commit/986e951e79753c32f72de35d41dcf2e64267b352) by Josef Friedrich)
- Rewrite global static keyboard listener. `Game.addKeyListener()` and `Game.removeKeyListener()` behave now
  like `scene.addKeyListener()` or `actor.addKeyListener()`.
  ([21574f4](https://github.com/engine-pi/engine-pi/commit/21574f482e689d1a06b4700f475ddc1037fb0317) by Josef Friedrich)

## [v0.3.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.3.0) - 2024-05-08

<small>[Compare with v0.2.0](https://github.com/engine-pi/engine-pi/compare/v0.2.0...v0.3.0)</small>

### Added

- Add static methods addKeyListener and removeKeyListener to the Game class ([393cf54](https://github.com/engine-pi/engine-pi/commit/393cf543fc4386a9852f2f2f860b476af9cfb99a) by Josef Friedrich).

### Changed

- Use double instead of float wherever possible
  ([157033f](https://github.com/engine-pi/engine-pi/commit/157033fac6c7fcb3159ed8d24ea180908d72cd1a) by Josef Friedrich).
- Change coding style and reformat code using mvn formatter:format [8eb6274](https://github.com/engine-pi/engine-pi/commit/8eb627475b1faa07d44c7a14c1e007340d5c1164)

## [v0.2.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.2.0) - 2024-04-28

<small>[Compare with v0.1.0](https://github.com/engine-pi/engine-pi/compare/v0.1.0...v0.2.0)</small>

### Added

- Add new logos ([3c27d75](https://github.com/engine-pi/engine-pi/commit/3c27d756fb5f6509e0363c6c0671c05843ef8bbc) by Josef Friedrich).
- Add badge ([abdaeb4](https://github.com/engine-pi/engine-pi/commit/abdaeb4a1b598a2c3707d801b2c9ee23ecf23090) by Josef Friedrich).
- Add new constructor for the Image actor class ([74af5c2](https://github.com/engine-pi/engine-pi/commit/74af5c28487c0d24b454c65a070960ed1c4f40f5) by Josef Friedrich).

## [v0.1.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.1.0) - 2024-04-28

<small>[Compare with first commit](https://github.com/engine-pi/engine-pi/compare/6ae5809945fc348ae76714907e3aaca2ebc66bb7...v0.1.0)</small>

### Changed

- Change `groupId` from `ea` to `de.pirckheimer_gymnasium.engine_pi` ([8ff4b97](https://github.com/engine-pi/engine-pi/commit/8ff4b97bb88af3a05517373193cc7b047e2343ae) by Josef Friedrich)
