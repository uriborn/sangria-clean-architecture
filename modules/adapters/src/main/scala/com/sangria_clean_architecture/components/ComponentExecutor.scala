package com.sangria_clean_architecture.components

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.executor.GraphQLExecutor
import sangria.parser.QueryParser

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class ComponentExecutor @Inject()(graphQLExecutor: GraphQLExecutor) {

  def execute(query: String)(implicit ec: ExecutionContext): String = {
    QueryParser.parse(query) match {
      case Success(document) => graphQLExecutor.execute(document)
      case Failure(error)    => QueryParseError(error.getMessage).asJsonStr
    }
  }

}
