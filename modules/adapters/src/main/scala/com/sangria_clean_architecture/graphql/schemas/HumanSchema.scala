package com.sangria_clean_architecture.graphql.schemas

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.resolvers.HumanResolver
import sangria.schema._

import scala.concurrent.ExecutionContext

class HumanSchema @Inject()(
  humanResolver: HumanResolver
) extends StarWarsCharacterSchema {

  val humanObject = ObjectType(
    name = "Human",
    interfaces = interfaces[Unit, HumanSchemaValue](starWarsCharacterInterface),
    fields = fields[Unit, HumanSchemaValue](
      Field(name = "id",       fieldType = LongType,                resolve = _.value.id),
      Field(name = "name",     fieldType = StringType,              resolve = _.value.name),
      Field(name = "episodes", fieldType = ListType(episodeObject), resolve = _.value.episodes)
    )
  )

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

  def mutations(implicit ec: ExecutionContext): List[Field[Unit, Unit]] = List(
    Field(
      name = "createHuman",
      fieldType = humanObject,
      arguments = nameArgument :: homePlanetArgument :: episodesArgument :: Nil,
      resolve = ctx => humanResolver.createHuman(ctx.arg(nameArgument), ctx.arg(homePlanetArgument), ctx.arg(episodesArgument))
    )
  )

}
