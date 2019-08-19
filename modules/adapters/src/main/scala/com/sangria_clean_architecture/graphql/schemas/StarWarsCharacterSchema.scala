package com.sangria_clean_architecture.graphql.schemas

import sangria.schema._

trait StarWarsCharacterSchema extends EpisodeSchema {

  val episodeInputObjectType = InputObjectType(
    name = "EpisodeInput",
    fields = List(
      InputField("id", LongType),
      InputField("name", StringType),
    )
  )

  val idArgument = Argument("id", LongType)

  val limitArgument = Argument("limit", IntType)
  val offsetArgument = Argument("offset", IntType)

  val nameArgument = Argument("name", StringType)
  val homePlanetArgument = Argument("homePlanet", OptionInputType(StringType))
  val episodesArgument = Argument("episodes", ListInputType(episodeInputObjectType))

  val starWarsCharacterInterface = InterfaceType(
    name = "Character",
    fieldsFn = () => fields[Unit, CharacterSchemaValue](
      Field(name = "id",       fieldType = LongType,                resolve = _.value.id),
      Field(name = "name",     fieldType = StringType,              resolve = _.value.name),
      Field(name = "episodes", fieldType = ListType(episodeObject), resolve = _.value.episodes)
    )
  )

}
