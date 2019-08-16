package com.sangria_clean_architecture.repositories.human

import scalikejdbc._

case class HumanEpisodeTable(
  humanId: Long,
  episodeId: Long
)

object HumanEpisodeTable extends SQLSyntaxSupport[HumanEpisodeTable] { self =>

  override val schemaName: Option[String] = None
  override val tableName: String = "human_episode"

  def apply(syn: SyntaxProvider[HumanEpisodeTable])(rs: WrappedResultSet): HumanEpisodeTable = apply(syn.resultName)(rs)

  def apply(rn: ResultName[HumanEpisodeTable])(rs: WrappedResultSet): HumanEpisodeTable =
    HumanEpisodeTable(rs.get(rn.humanId), rs.get(rn.episodeId))

  def opt(syn: SyntaxProvider[HumanEpisodeTable])(rs: WrappedResultSet): Option[HumanEpisodeTable] =
    rs.longOpt(syn.resultName.humanId).map(_ => HumanEpisodeTable(syn)(rs))
}
