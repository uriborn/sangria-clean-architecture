package com.sangria_clean_architecture.graphql

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.schemas.HumanSchema
import sangria.schema._

import scala.concurrent.ExecutionContext

class GraphQL @Inject()(humanSchema: HumanSchema) {

  def schema(implicit ec: ExecutionContext) = Schema(
    query = ObjectType(
      name = "query",
      fields = fields(
        humanSchema.queries: _*
      )
    )
  )

}
