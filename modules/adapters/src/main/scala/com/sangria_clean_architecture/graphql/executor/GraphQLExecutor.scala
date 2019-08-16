package com.sangria_clean_architecture.graphql.executor

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.GraphQL
import sangria.ast.Document
import sangria.execution.Executor
import sangria.marshalling.circe._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

class GraphQLExecutor @Inject()(graphQL: GraphQL) {

  def execute(query: Document)(implicit ec: ExecutionContext): String = {
    val executed = Executor.execute(graphQL.schema, query).map(_.noSpaces)
    Await.result(executed, Duration.Inf)
  }

}
