package com.sangria_clean_architecture.usecases

import scala.concurrent.Future

abstract class UseCaseInteractor[InputData, OutputData](
  outputBoundary: OutputBoundary[OutputData]
) extends InputBoundary[InputData] {

  protected def call(inputData: InputData): Future[OutputData]

  override def execute(inputData: InputData): Unit = outputBoundary.onComplete(call(inputData))

}
