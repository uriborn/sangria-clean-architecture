package com.sangria_clean_architecture.components

import io.circe.generic.auto._
import io.circe.syntax._

case class QueryParseError(message: String) {
  def asJsonStr = this.asJson.noSpaces
}
