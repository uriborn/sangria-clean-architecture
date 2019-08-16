package com.sangria_clean_architecture.components

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.google.inject.Inject
import com.google.inject.name.Named
import com.sangria_clean_architecture.di.Injector
import com.sangria_clean_architecture.graphql.executor.GraphQLExecutor
import com.sangria_clean_architecture.infrastructure.DBComponent
import sangria.parser.QueryParser

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class HumanComponent extends RequestHandler[LambdaRequest, String] with Injector {

  override def handleRequest(input: LambdaRequest, context: Context): String = {
    val component = injector.getInstance(classOf[HumanComponentExecutor])
    component.execute(input, context)
  }

}

class HumanComponentExecutor @Inject()(
  graphQLExecutor: GraphQLExecutor,
  dbComponent: DBComponent
)(
  @Named("default-app-context") implicit val ec: ExecutionContext
) {

  def execute(input: LambdaRequest, context: Context): String = {
    QueryParser.parse(input.query) match {
      case Success(document) => graphQLExecutor.execute(document)
      case Failure(error)    => QueryParseError(error.getMessage).asJsonStr
    }
  }
}
