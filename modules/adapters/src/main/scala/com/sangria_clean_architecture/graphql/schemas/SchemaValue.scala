package com.sangria_clean_architecture.graphql.schemas

trait SchemaValue

trait CharacterSchemaValue extends SchemaValue {
  def id: Long
  def name: String
  def episodes: List[String]
}

case class HumanSchemaValue(
   id: Long,
   name: String,
   episodes: List[String],
   homePlanet: Option[String]
 ) extends CharacterSchemaValue

case class DroidSchemaValue(
  id: Long,
  name: String,
  episodes: List[String],
  primaryFunction: Option[String]
) extends CharacterSchemaValue
