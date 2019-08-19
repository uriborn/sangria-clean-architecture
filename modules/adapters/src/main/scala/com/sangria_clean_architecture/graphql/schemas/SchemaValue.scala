package com.sangria_clean_architecture.graphql.schemas

trait SchemaValue

case class EpisodeSchemaValue(
  id: Long,
  name: String
) extends SchemaValue

trait CharacterSchemaValue extends SchemaValue {
  def id: Long
  def name: String
  def episodes: List[EpisodeSchemaValue]
}


case class HumanSchemaValue(
   id: Long,
   name: String,
   episodes: List[EpisodeSchemaValue],
   homePlanet: Option[String]
 ) extends CharacterSchemaValue

case class DroidSchemaValue(
  id: Long,
  name: String,
  episodes: List[EpisodeSchemaValue],
  primaryFunction: Option[String]
) extends CharacterSchemaValue
