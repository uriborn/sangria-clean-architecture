package com.sangria_clean_architecture.usecases

import scala.concurrent.Future

trait OutputBoundary[OutputData] {

  def onComplete(result: Future[OutputData]): Unit

}
