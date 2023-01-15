package ru.broom
package model

import io.circe.generic.semiauto.deriveEncoder
import io.circe.{Decoder, Encoder, HCursor}


object CaseModel {

  implicit val nameDecoder: Decoder[Name] =
    (hCursor: HCursor) => {
      for {
        common <- hCursor.get[String]("common")
        official <- hCursor.get[String]("official")
      } yield Name(common, official)
    }

  implicit val rootDecoder: Decoder[RootModel] =
    (hCursor: HCursor) => {
      for {
        name <- hCursor.downField("name").as[Name]
        region <- hCursor.get[Option[String]]("region")
        area <- hCursor.get[Float]("area")
        capital <- hCursor.get[Array[String]]("capital")
      } yield RootModel(name, region, area, capital)
    }

  case class RootModel(
                        name: Name,
                        region: Option[String],
                        area: Float,
                        capital: Array[String]
                      )

  case class Name(
                  common: String,
                  official: String
                  )

  implicit val outEncoder: Encoder[OutModel] = deriveEncoder

  case class OutModel(
                        name: String, // official
                        capital: String,
                        area: Long
                      )
}