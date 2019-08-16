package com.sangria_clean_architecture.components

import scala.beans.BeanProperty

case class LambdaRequest(
  @BeanProperty var query: String,
  @BeanProperty var operationName: String,
  @BeanProperty var variables: String,
) {

  def this() = this(query = "", operationName = "", variables = "")

}
