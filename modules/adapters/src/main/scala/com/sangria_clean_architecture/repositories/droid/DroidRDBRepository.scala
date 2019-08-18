package com.sangria_clean_architecture.repositories.droid

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.droid.{Droid, DroidId}
import com.sangria_clean_architecture.gateway.repositories.DroidRepository
import com.sangria_clean_architecture.repositories.episode.EpisodeTable
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

class DroidRDBRepository @Inject()(
  droidConverter: DroidConverter
) extends DroidRepository {

  private val d = DroidTable.syntax("d")
  private val de = DroidEpisodeTable.syntax("de")
  private val e = EpisodeTable.syntax("e")

  override def findAll(implicit ec: ExecutionContext): Future[List[Droid]] = Future {
    DB.readOnly { implicit session =>
      withSQL[DroidTable] {
        select
          .from(DroidTable as d)
          .innerJoin(DroidEpisodeTable as de).on(d.id, de.droidId)
          .innerJoin(EpisodeTable as e).on(e.id, de.episodeId)
      }.one(DroidTable(d))
        .toManies(
          rs => DroidEpisodeTable.opt(de)(rs),
          rs => EpisodeTable.opt(e)(rs)
        )
        .map(combineTables(_, _, _))
        .list.apply
        .map(droidConverter.convertToEntity)
    }
  }

  override def findById(id: DroidId)(implicit ec: ExecutionContext): Future[Option[Droid]] = Future {
    DB.readOnly { implicit session =>
      withSQL[DroidTable] {
        select
          .from(DroidTable as d)
          .innerJoin(DroidEpisodeTable as de).on(d.id, de.droidId)
          .innerJoin(EpisodeTable as e).on(e.id, de.episodeId)
          .where.eq(d.id, id.value)
      }.one(DroidTable(d))
        .toManies(
          rs => DroidEpisodeTable.opt(de)(rs),
          rs => EpisodeTable.opt(e)(rs)
        )
        .map(combineTables(_, _, _))
        .single.apply
        .map(droidConverter.convertToEntity)
    }
  }

  private def combineTables(
    droid: DroidTable,
    droidEpisode: Seq[DroidEpisodeTable],
    episode: Seq[EpisodeTable]
  ): DroidTable = {
    droid.copy(episodes = episode)
  }

}
