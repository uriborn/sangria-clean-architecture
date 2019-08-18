package com.sangria_clean_architecture.repositories.droid

import scalikejdbc._

case class DroidEpisodeTable(
  droidId: Long,
  episodeId: Long
)

object DroidEpisodeTable extends SQLSyntaxSupport[DroidEpisodeTable] { self =>

  override val schemaName: Option[String] = None
  override val tableName: String = "droid_episode"

  def apply(syn: SyntaxProvider[DroidEpisodeTable])(rs: WrappedResultSet): DroidEpisodeTable = apply(syn.resultName)(rs)

  def apply(rn: ResultName[DroidEpisodeTable])(rs: WrappedResultSet): DroidEpisodeTable =
    DroidEpisodeTable(rs.get(rn.droidId), rs.get(rn.episodeId))

  def opt(syn: SyntaxProvider[DroidEpisodeTable])(rs: WrappedResultSet): Option[DroidEpisodeTable] =
    rs.longOpt(syn.resultName.droidId).map(_ => DroidEpisodeTable(syn)(rs))
}
