package com.sangria_clean_architecture.repositories.droid

import com.sangria_clean_architecture.repositories.episode.EpisodeTable
import scalikejdbc._

case class DroidTable(
  id: Long,
  name: String,
  episodes: Seq[EpisodeTable] = Nil,
  primaryFunction: Option[String]
)

object DroidTable extends SQLSyntaxSupport[DroidTable] { self =>

  override val schemaName: Option[String] = None
  override val tableName: String = "droid"

  def apply(syn: SyntaxProvider[DroidTable])(rs: WrappedResultSet): DroidTable = apply(syn.resultName)(rs)

  def apply(rn: ResultName[DroidTable])(rs: WrappedResultSet): DroidTable = DroidTable(rs.get(rn.id), rs.get(rn.name), Nil, rs.get(rn.primaryFunction))

}
