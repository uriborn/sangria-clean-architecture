package com.sangria_clean_architecture.graphql.schemas

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.episode.Episode
import com.sangria_clean_architecture.graphql.resolvers.human.HumanResolver
import com.sangria_clean_architecture.graphql.resolvers.{CharacterSchemaValue, HumanSchemaValue}
import sangria.schema._

import scala.concurrent.ExecutionContext

class HumanSchema @Inject()(humanResolver: HumanResolver) {

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

  val humanObject = ObjectType(
    name = "Human",
    interfaces = interfaces[Unit, HumanSchemaValue](starWarsCharacterInterface),
    fields = fields[Unit, HumanSchemaValue](
      Field(name = "id",       fieldType = LongType,              resolve = _.value.id),
      Field(name = "name",     fieldType = StringType,            resolve = _.value.name),
      Field(name = "episodes", fieldType = ListType(episodeEnum), resolve = _.value.episodes)
    )
  )

  val idArgument = Argument("id", LongType)
  val limitArgument = Argument("limit", IntType)
  val offsetArgument = Argument("offset", IntType)

  def queries(implicit ec: ExecutionContext): List[Field[Unit, Unit]] = List(
    Field(
      name = "human",
      fieldType = OptionType(humanObject),
      arguments = idArgument :: Nil,
      resolve = ctx => humanResolver.findHuman(ctx.arg(idArgument))
    ),
    Field(
      name = "humans",
      fieldType = ListType(humanObject),
      arguments = limitArgument :: offsetArgument :: Nil,
      resolve = ctx => humanResolver.findAllHumans(ctx.arg(limitArgument), ctx.arg(offsetArgument))
    )
  )

}
