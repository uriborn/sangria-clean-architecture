package com.sangria_clean_architecture.repositories.human

import com.sangria_clean_architecture.repositories.episode.EpisodeTable
import scalikejdbc._

case class HumanTable(
  id: Long,
  name: String,
  episodes: Seq[EpisodeTable] = Nil,
  homePlanet: Option[String]
)

object HumanTable extends SQLSyntaxSupport[HumanTable] { self =>

  override val schemaName: Option[String] = None
  override val tableName: String = "human"

  def apply(syn: SyntaxProvider[HumanTable])(rs: WrappedResultSet): HumanTable = apply(syn.resultName)(rs)

  def apply(rn: ResultName[HumanTable])(rs: WrappedResultSet): HumanTable = HumanTable(rs.get(rn.id), rs.get(rn.name), Nil, rs.get(rn.homePlanet))

}
