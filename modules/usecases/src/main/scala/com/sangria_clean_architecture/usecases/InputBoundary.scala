package com.sangria_clean_architecture.usecases

trait InputBoundary[InputData] {

  def execute(inputData: InputData): Unit

}
