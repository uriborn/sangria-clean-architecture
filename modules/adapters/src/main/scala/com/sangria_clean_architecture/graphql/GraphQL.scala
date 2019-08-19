package com.sangria_clean_architecture.graphql

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.schemas.{DroidSchema, HumanSchema}
import sangria.schema._

import scala.concurrent.ExecutionContext

class GraphQL @Inject()(
  humanSchema: HumanSchema,
  droidSchema: DroidSchema
) {

  def schema(implicit ec: ExecutionContext): Schema[Unit, Unit] = {
    val queryFields = humanSchema.queries ++ droidSchema.queries
    val mutationFields = humanSchema.mutations

    Schema(
      query = ObjectType(
        name = "query",
        fields = fields(
          queryFields: _*
        )
      ),
      mutation = Some(ObjectType(
        name = "mutation",
        fields = fields(
          mutationFields: _*
        )
      ))
    )
  }

}
