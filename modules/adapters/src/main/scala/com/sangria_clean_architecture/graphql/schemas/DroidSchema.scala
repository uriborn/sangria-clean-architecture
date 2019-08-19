package com.sangria_clean_architecture.graphql.schemas

import com.google.inject.Inject
import com.sangria_clean_architecture.graphql.resolvers.DroidResolver
import sangria.schema._

import scala.concurrent.ExecutionContext

class DroidSchema @Inject()(
  droidResolver: DroidResolver
) extends StarWarsCharacterSchema {

  val droidObject = ObjectType(
    name = "Droid",
    interfaces = interfaces[Unit, DroidSchemaValue](starWarsCharacterInterface),
    fields = fields[Unit, DroidSchemaValue](
      Field(name = "id",       fieldType = LongType,                resolve = _.value.id),
      Field(name = "name",     fieldType = StringType,              resolve = _.value.name),
      Field(name = "episodes", fieldType = ListType(episodeObject), resolve = _.value.episodes)
    )
  )

  def queries(implicit ec: ExecutionContext): List[Field[Unit, Unit]] = List(
    Field(
      name = "droid",
      fieldType = OptionType(droidObject),
      arguments = idArgument :: Nil,
      resolve = ctx => droidResolver.findDroid(ctx.arg(idArgument))
    ),
    Field(
      name = "droids",
      fieldType = ListType(droidObject),
      arguments = limitArgument :: offsetArgument :: Nil,
      resolve = ctx => droidResolver.findAllDroids(ctx.arg(limitArgument), ctx.arg(offsetArgument))
    )
  )

}
