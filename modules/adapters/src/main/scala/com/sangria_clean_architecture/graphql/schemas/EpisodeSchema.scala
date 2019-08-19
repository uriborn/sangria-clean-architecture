package com.sangria_clean_architecture.graphql.schemas

import sangria.schema._

trait EpisodeSchema {

  val episodeObject = ObjectType(
    name = "Episode",
    fields = fields[Unit, EpisodeSchemaValue](
      Field(name = "id",   fieldType = LongType,   resolve = _.value.id),
      Field(name = "name", fieldType = StringType, resolve = _.value.name)
    )
  )

}
