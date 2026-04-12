# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.1.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [v0.46.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.46.0) - 2026-04-12

<small>[Compare with v0.45.0](https://github.com/engine-pi/engine-pi/compare/v0.45.0...v0.46.0)</small>

### Added

- Add new package `pi.actor.label`

### Changed

- Rename `FrameUpdateLister` into `FrameListener`
- Refactor the class `ImageText` (former name `ImageFontText`)

## [v0.45.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.45.0) - 2026-04-08

<small>[Compare with v0.44.0](https://github.com/engine-pi/engine-pi/compare/v0.44.0...v0.45.0)</small>

### Added

- Include JBox2D in this monorepo.
- Add basic label support for the actors.

### Changed

- Refactor logging.
- Throw more exceptions in the resource classes.
- Extend and refactor the actor `Line`.

### Removed

- Remove class `Game`.

## [v0.44.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.44.0) - 2026-03-27

<small>[Compare with v0.43.0](https://github.com/engine-pi/engine-pi/compare/v0.43.0...v0.44.0)</small>

### Added

- Add setters to the actor `Circle`.
- Add setters and refactor the actor `Image`.

## [v0.43.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.43.0) - 2026-03-21

<small>[Compare with v0.42.0](https://github.com/engine-pi/engine-pi/compare/v0.42.0...v0.43.0)</small>

### Changed

- Migrate from the `Makefile` to the `Justfile`.
- Extend the actor class `Counter` to support `amount`, `prefix`, `template`
  and `suffix`.

### Fixed

- Fixed the BlueJ demo class by updating the API.
- Fixed the Github releases.

## [v0.42.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.42.0) - 2026-03-19

<small>[Compare with v0.41.0](https://github.com/engine-pi/engine-pi/compare/v0.41.0...v0.42.0)</small>

### Changed

- Remove set and get prefixes from the setters and getters.
- Rename class `Game` into `Controller`.

## [v0.41.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.41.0) - 2025-12-20

<small>[Compare with v0.40.0](https://github.com/engine-pi/engine-pi/compare/v0.40.0...v0.41.0)</small>

### Changed

- Shorten import path `de.pirckheimer_gymasium.engine_pi` to `pi`.

## [v0.40.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.40.0) - 2025-11-16

<small>[Compare with v0.39.0](https://github.com/engine-pi/engine-pi/compare/v0.39.0...v0.40.0)</small>

### Changed

- Split the `dsa/turtle` package into more classes.

### Added

- Add more classes to the package `graphics/boxes`.

## [v0.39.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.39.0) - 2025-11-10

<small>[Compare with v0.38.0](https://github.com/engine-pi/engine-pi/compare/v0.38.0...v0.39.0)</small>

### Fixed

- Fix the loading of the default fonts.
- Add BlueJ template files to the zip file.

## [v0.38.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.38.0) - 2025-11-08

<small>[Compare with v0.37.0](https://github.com/engine-pi/engine-pi/compare/v0.37.0...v0.38.0)</small>

### Added

- Add class `Turtle` to draw turtle grapics.
- Package `graphics/boxes`.

### Changed

- Use lowercase attribute names for the static resource containers
  (e.g. `Resources.FONTS` -> `Resources.fonts`).

### Removed

- Remove the [Graphics and Games](https://gng4java.informatikschulbuch.de/allclasses.html) engine from Cornelsen as `little_engine`.

## [v0.37.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.37.0) - 2025-11-04

<small>[Compare with v0.36.0](https://github.com/engine-pi/engine-pi/compare/v0.36.0...v0.37.0)</small>

### Added

- Add new module `build-tools` containing build resources.
- Include the games `tetris`, `pacman` and `blockly-robot` as submodules.
- Add new method `renderOverlay` in the class `Scene`.

### Changed

- New folder structure for the multi module setup.

## [v0.36.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.36.0) - 2025-10-26

<small>[Compare with v0.35.0](https://github.com/engine-pi/engine-pi/compare/v0.35.0...v0.36.0)</small>

### Added

- Add package `dsa/graph`. `dsa` = Data Structures and Algorithms to visualize graphs.

## [v0.35.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.35.0) - 2025-10-19

<small>[Compare with v0.34.0](https://github.com/engine-pi/engine-pi/compare/v0.34.0...v0.35.0)</small>

### Added

- New actor: `Counter`.
- New methods `.hide()` and `.show()` on the Actor objects.

## [v0.34.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.34.0) - 2025-10-17

<small>[Compare with v0.33.0](https://github.com/engine-pi/engine-pi/compare/v0.33.0...v0.34.0)</small>

### Added

- Add more instant actors: `Circle`, `Text`.
- Add BlueJ template as a build target.

### Changed

- Renamed `InstantController` into Controller..

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

- Add the [Graphics and Games](https://gng4java.informatikschulbuch.de/allclasses.html)
  engine from Cornelsen as `little_engine`.

## [v0.29.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.29.0) - 2024-08-05

<small>[Compare with v0.28.0](https://github.com/engine-pi/engine-pi/compare/v0.28.0...v0.29.0)</small>

### Changed

- Use jbox2d from
  https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/jbox2d-library.
- Update eclipse formatter.

## [v0.28.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.28.0) - 2024-08-01

<small>[Compare with v0.27.0](https://github.com/engine-pi/engine-pi/compare/v0.27.0...v0.28.0)</small>

### Added

- Add new methods to the Camera.

### Removed

- Remove some `addRectangle` methods.
- Remove some `addCircle` overloads.

## [v0.27.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.27.0) - 2024-07-29

<small>[Compare with v0.26.0](https://github.com/engine-pi/engine-pi/compare/v0.26.0...v0.27.0)</small>

### Added

- New classes `actor/ImageFontGlyph`, `actor/ImageFontSpecimen`
  `actor/StatefulAnimatedGifAnimation.java`
  `actor/StatefulImagesPrefixAnimation.java`
  `actor/StatefulSpritesheetAnimation.java`.
- New method Actor#toggleVisible().
- Implement Game#setWindowPosition(Direction).

### Changed

- Refactor coordinate system drawing.
- Create more jar files jar-with-dependencies.

### Fixed

- Fix image font.

## [v0.26.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.26.0) - 2024-07-16

<small>[Compare with v0.25.0](https://github.com/engine-pi/engine-pi/compare/v0.25.0...v0.26.0)</small>

### Added

- Add new class `ColorContainerVisualizer`.
- Add method overload `ColorContainer.add(String, int, int, int, int)`.
- Add new overload for `Animation.createFromImages`.
- Add new class `LetterTileMap`.
- Add new class `StateFulImagesAnimation`.
- Add new class `NamedColor`.

### Changed

- Default zoom is new 32 instead of 30 Pixel per meter.

## [v0.25.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.25.0) - 2024-07-12

<small>[Compare with v0.24.0](https://github.com/engine-pi/engine-pi/compare/v0.24.0...v0.25.0)</small>

### Added

- Add the eclipse formatter to the resources.
- Start main animation with `Game.start()`.
- Implement global pixel multiplication (preview feature).

### Fixed

- Fix ImageFontText.

## [v0.24.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.24.0) - 2024-07-10

<small>[Compare with v0.23.0](https://github.com/engine-pi/engine-pi/compare/v0.23.0...v0.24.0)</small>

### Changed

- Refactor the actor `Image`.

### Fixed

- Fix overload `ImageContainer.get()` not accepting a `Color` array.

## [v0.23.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.23.0) - 2024-07-09

<small>[Compare with v0.22.0](https://github.com/engine-pi/engine-pi/compare/v0.22.0...v0.23.0)</small>

### Added

- Add new get overloads in the class `ImageContainer`.
- Add new a actor named `ImageFontText`.
- Extend the `TextUtils`.

## [v0.22.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.22.0) - 2024-07-02

<small>[Compare with v0.21.0](https://github.com/engine-pi/engine-pi/compare/v0.21.0...v0.22.0)</small>

### Changed

- Make the class Jukebox static and move it to the root package.

## [v0.21.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.21.0) - 2024-06-27

<small>[Compare with v0.20.0](https://github.com/engine-pi/engine-pi/compare/v0.20.0...v0.21.0)</small>

### Added

- Add more method overloads for the repeat method of the interface
  `FrameUpdateListenerRegistration`.

## [v0.20.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.20.0) - 2024-06-25

<small>[Compare with v0.19.0](https://github.com/engine-pi/engine-pi/compare/v0.19.0...v0.20.0)</small>

### Added

- Add counter in the class PeriodicTask.
- Import the font container from the LITIENGINE.

### Changed

- Improve main animation.

## [v0.19.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.19.0) - 2024-06-24

<small>[Compare with v0.18.0](https://github.com/engine-pi/engine-pi/compare/v0.18.0...v0.19.0)</small>

### Changed

- Extend the class PeriodicTask.
- Rename `Game.getMousePositionInCurrentScene()` into `Game.getMousePosition()`.
- Rename the create..() methods into add..().

## [v0.18.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.18.0) - 2024-06-23

<small>[Compare with v0.17.0](https://github.com/engine-pi/engine-pi/compare/v0.17.0...v0.18.0)</small>

### Added

- Add custom javadoc CSS stylesheet.
- Add new logo.
- Add new enum `ColorSchemeSelection`.

### Changed

- Convert project in a monorepo with the three Maven packages
  `engine-pi-project`, `engine-pi` and `engine-pi-demos`.

## [v0.17.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.17.0) - 2024-06-17

<small>[Compare with v0.16.0](https://github.com/engine-pi/engine-pi/compare/v0.16.0...v0.17.0)</small>

### Added

- Calculate the average color of images to draw a shape in complementary colors.
- Alt+A: Add a default shortcut to hide or show the actors fill color or image.
- Add a info box to the debug view to show the current gravity vector.

## [v0.16.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.16.0) - 2024-06-13

<small>[Compare with v0.15.0](https://github.com/engine-pi/engine-pi/compare/v0.15.0...v0.16.0)</small>

### Fixed

- Fix EventListener handling.

## [v0.15.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.15.0) - 2024-06-13

<small>[Compare with v0.14.0](https://github.com/engine-pi/engine-pi/compare/v0.14.0...v0.15.0)</small>

### Added

- Add SceneLaunchListener.
- Add default shortcuts and default controls.
- Awake and sleep actors.

### Changed

- Change logo.
- Rename `MouseWheelListener` into `MouseScrollListener` to avoid confusion
  with JDK version.
- Rename `KeyListener` into `KeyStrokeListener` to avoid confusing with
  `java.awt.event.KeyListener`.

### Removed

- Remove dependency to Showcase.
- Remove static methods Game.getImages() Game.getSounds().

## [v0.14.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.14.0) - 2024-06-08

<small>[Compare with v0.13.0](https://github.com/engine-pi/engine-pi/compare/v0.13.0...v0.14.0)</small>

### Changed

- Change the name of the engine from engine-omega into engine-pi pi stands for PIrckheimer-Gymnasium.
- Change default branch.

## [v0.13.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.13.0) - 2024-06-08

<small>[Compare with v0.12.0](https://github.com/engine-pi/engine-pi/compare/v0.12.0...v0.13.0)</small>

### Added

- Add new geometric actors.

## [v0.12.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.12.0) - 2024-06-07

<small>[Compare with v0.11.0](https://github.com/engine-pi/engine-pi/compare/v0.11.0...v0.12.0)</small>

### Added

- Add color schema.
- Add constructor without arguments for the Rectangle class.
- Add new methods Scene#gravityOfEarth and Scene#gravityOfEarth.
- Add new actor: Triangle.

### Changed

- Rename the attribute zoom of the class Camera into meter.

## [v0.11.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.11.0) - 2024-06-01

<small>[Compare with v0.10.0](https://github.com/engine-pi/engine-pi/compare/v0.10.0...v0.11.0)</small>

### Added

- Add new action class: Grid.
- Add setter for BodyTypes on Actor.
- Add the source code of jbox2d to the project instead of the jar file.

## [v0.10.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.10.0) - 2024-05-30

<small>[Compare with v0.9.0](https://github.com/engine-pi/engine-pi/compare/v0.9.0...v0.10.0)</small>

### Added

- Add ResourceManipulator.

## [v0.9.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.9.0) - 2024-05-26

<small>[Compare with v0.8.0](https://github.com/engine-pi/engine-pi/compare/v0.8.0...v0.9.0)</small>

### Added

- Add PressedKeyRepeater.
- Add sound playback.

## [v0.8.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.8.0) - 2024-05-18

<small>[Compare with v0.7.0](https://github.com/engine-pi/engine-pi/compare/v0.7.0...v0.8.0)</small>

### Added

- Add package-info.java to all packages.
- Add new methode overload: ImageUtil#replaceColor(BufferedImage, Color, Color).

### Fixed

- Fix color decode for string arrays.

## [v0.7.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.7.0) - 2024-05-14

<small>[Compare with v0.6.0](https://github.com/engine-pi/engine-pi/compare/v0.6.0...v0.7.0)</small>

### Added

- Add new method `ImageUtil.replaceColor()`.
- Add resources management code from the LITIENGINE.
- Add the tween engine from the LITIENGINE.

### Changed

- `ResourceLoader` is now in the resouces package.

### Removed

- Remove `ImageLoader` -> `ImagesContainer`.
- Remove `Optimizer` -> `ImageUtil`.

## [v0.6.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.6.0) - 2024-05-13

<small>[Compare with v0.5.0](https://github.com/engine-pi/engine-pi/compare/v0.5.0...v0.6.0)</small>

### Added

- Add static global frame update listener.

### Changed

- Change the input and output format of color methods dealing with hexadecimal colors
  from `AARRGGBB` to `RRGGBBAA`.

## [v0.5.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.5.0) - 2024-05-12

<small>[Compare with v0.4.0](https://github.com/engine-pi/engine-pi/compare/v0.4.0...v0.5.0)</small>

### Added

- Add color and math util from the LITIengine.
- Add ImageUtil from the LITIengine.
- Add the LITIengine sound engine.

## [v0.4.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.4.0) - 2024-05-09

<small>[Compare with v0.3.0](https://github.com/engine-pi/engine-pi/compare/v0.3.0...v0.4.0)</small>

### Changed

- Combine all event specific classes in the package event. `PeriodicTask`,
  `SingleTask` and `FrameUpdateListener` are now located in the package `event`.
- Extend class `PeriodicTask`.
- Rewrite global static keyboard listener. `Game.addKeyListener()` and
  `Game.removeKeyListener()` behave now
  like `scene.addKeyListener()` or `actor.addKeyListener()`.


## [v0.3.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.3.0) - 2024-05-08

<small>[Compare with v0.2.0](https://github.com/engine-pi/engine-pi/compare/v0.2.0...v0.3.0)</small>

### Added

- Add static methods addKeyListener and removeKeyListener to the Game class.

### Changed

- Use double instead of float wherever possible.
- Change coding style and reformat code using mvn formatter:format.

## [v0.2.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.2.0) - 2024-04-28

<small>[Compare with v0.1.0](https://github.com/engine-pi/engine-pi/compare/v0.1.0...v0.2.0)</small>

### Added

- Add new logos.
- Add badge.
- Add new constructor for the Image actor class.

## [v0.1.0](https://github.com/engine-pi/engine-pi/releases/tag/v0.1.0) - 2024-04-28

<small>[Compare with first commit](https://github.com/engine-pi/engine-pi/compare/6ae5809945fc348ae76714907e3aaca2ebc66bb7...v0.1.0)</small>

### Changed

- Change `groupId` from `ea` to `de.pirckheimer_gymnasium.engine_pi`.
