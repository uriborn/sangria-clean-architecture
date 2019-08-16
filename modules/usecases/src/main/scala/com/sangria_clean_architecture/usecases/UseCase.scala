package com.sangria_clean_architecture.usecases

import scala.concurrent.{ExecutionContext, Future}

trait UseCase[InputData, OutputData] {

  def execute(inputData: InputData)(implicit ec: ExecutionContext): Future[OutputData]

}
