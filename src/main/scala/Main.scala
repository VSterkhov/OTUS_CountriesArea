package ru.broom

import io.circe._
import io.circe.syntax._

import scala.util.Using
import scala.io.Source
import model.CaseModel.{OutModel, RootModel}

import java.io.PrintWriter

object Main {
  def main(args: Array[String]): Unit = {
    val config = Config.parse(args)
    Using.Manager { use =>
      val source = use(Source.fromResource("countries.json"))
      val out = use(new PrintWriter(config.target))
      val jsonString = source.mkString.stripMargin
      val decodingResult = parser.decode[List[RootModel]](jsonString)
      decodingResult match {
        case Right(elements) =>
          val outModels = elements.filter(e=>e.region.isDefined && e.region.get.toLowerCase().contains("africa")).map(
            e=>{
              OutModel(e.name.official, e.capital.head, e.area.toLong)
            }
          )
          out.write(outModels.asJson.toString())
        case Left(error) => println(error.getMessage)
      }
    }
  }
}