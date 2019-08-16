package com.sangria_clean_architecture.repositories.episode

import scalikejdbc._

case class EpisodeTable(
  id: Long,
  name: String
)

object EpisodeTable extends SQLSyntaxSupport[EpisodeTable] { self =>

  override val schemaName: Option[String] = None
  override val tableName: String = "episode"

  def apply(syn: SyntaxProvider[EpisodeTable])(rs: WrappedResultSet): EpisodeTable = apply(syn.resultName)(rs)

  def apply(rn: ResultName[EpisodeTable])(rs: WrappedResultSet): EpisodeTable = EpisodeTable(rs.get(rn.id), rs.get(rn.name))

  def opt(syn: SyntaxProvider[EpisodeTable])(rs: WrappedResultSet): Option[EpisodeTable] =
    rs.longOpt(syn.resultName.id).map(_ => EpisodeTable(syn)(rs))

}
