package com.sangria_clean_architecture.graphql.schemas

import com.sangria_clean_architecture.entities.episode.Episode
import sangria.schema._

trait StarWarsCharacterSchema {

  val episodeEnum = EnumType(
    name = "Episode",
    values = List(
      EnumValue(name = Episode.NewHope.entryName, value = Episode.NewHope.entryName),
      EnumValue(name = Episode.Empire.entryName,  value = Episode.Empire.entryName),
      EnumValue(name = Episode.Jedi.entryName,    value = Episode.Jedi.entryName)
    )
  )

  val starWarsCharacterInterface = InterfaceType(
    name = "Character",
    fieldsFn = () => fields[Unit, CharacterSchemaValue](
      Field(name = "id",       fieldType = LongType,              resolve = _.value.id),
      Field(name = "name",     fieldType = StringType,            resolve = _.value.name),
      Field(name = "episodes", fieldType = ListType(episodeEnum), resolve = _.value.episodes)
    )
  )

}
