scalajs-bundler [![](https://index.scala-lang.org/scalacenter/scalajs-bundler/sbt-scalajs-bundler/latest.svg)](https://index.scala-lang.org/scalacenter/scalajs-bundler) [![Gitter](https://img.shields.io/badge/gitter-join%20chat-green.svg)](https://gitter.im/scalacenter/scalajs-bundler) [![CI](https://github.com/scalacenter/scalajs-bundler/actions/workflows/ci.yml/badge.svg)](https://github.com/scalacenter/scalajs-bundler/actions/workflows/ci.yml)
==============

This is GPP's fork. To build it, run `sbt publishLocal && sbt scalajs-bundler-linker/publishLocal` if you want to build a local snapshot. Once you're happy, give it a tag, e.g., `git tag v0.21.1.3; git push --tags` and then do another `sbt publishLocal && sbt scalajs-bundler-linker/publishLocal`. Then, to use in sciborg, replace the results with what's in sciborg's `plugins` directory.

Module bundler for Scala.js projects that use NPM packages.

Uses [npm](https://www.npmjs.com) and [webpack](https://webpack.github.io/) under the hood.

## Documentation

More information on how to use it in the [documentation](https://scalacenter.github.io/scalajs-bundler).

## Contributing

scalajs-bundler is community-maintained.

Contributions are welcome! See the [open issues](https://github.com/scalacenter/scalajs-bundler/issues) and
the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## License

This content is released under the [BSD 3-Clause License](http://opensource.org/licenses/BSD-3-Clause).
