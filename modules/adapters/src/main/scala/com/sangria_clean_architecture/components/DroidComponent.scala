package com.sangria_clean_architecture.components

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.google.inject.Inject
import com.google.inject.name.Named
import com.sangria_clean_architecture.di.Injector
import com.sangria_clean_architecture.infrastructure.DBComponent

import scala.concurrent.ExecutionContext

class DroidComponent extends RequestHandler[LambdaRequest, String] with Injector {

  override def handleRequest(input: LambdaRequest, context: Context): String = {
    val component = injector.getInstance(classOf[DroidComponentExecutor])
    component.execute(input, context)
  }

}

class DroidComponentExecutor @Inject()(
  componentExecutor: ComponentExecutor,
  dbComponent: DBComponent
)(
  @Named("default-app-context") implicit val ec: ExecutionContext
) {

  def execute(input: LambdaRequest, context: Context): String = {
    componentExecutor.execute(input.query)
  }

}
