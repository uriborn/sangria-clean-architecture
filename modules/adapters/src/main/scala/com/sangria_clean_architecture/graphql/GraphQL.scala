package com.sangria_clean_architecture.graphql

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.schemas.{DroidSchema, HumanSchema}
import sangria.schema._

import scala.concurrent.ExecutionContext

class GraphQL @Inject()(
  humanSchema: HumanSchema,
  droidSchema: DroidSchema
) {

  def schema(implicit ec: ExecutionContext) = {
    val queryFields = humanSchema.queries ++ droidSchema.queries

    Schema(
      query = ObjectType(
        name = "query",
        fields = fields(
          queryFields: _*
        )
      )
    )
  }

}
