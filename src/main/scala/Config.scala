package ru.broom

import scopt.OParser

import java.io.File
import java.lang.System.exit

case class Config(
                   target: File = null
                 )

object Config {
  private val builder = OParser.builder[Config]
  private val parser = {
    import builder._
    OParser.sequence(
      programName("Countries Area"),
      head("Countries Area", "v1"),
      opt[File]('t', "target")
        .required()
        .valueName("<file>")
        .action((path, conf) => {
          path.createNewFile()
          conf.copy(target = path)
        })
        .text("target is required property"),
      help("help").text("prints this usage text")
    )
  }
  def parse(args: Array[String]): Config = {
    OParser.parse(parser, args, Config()) match {
      case Some(config) =>
        config
      case _ =>
        exit(1)
        null
    }
  }
}



