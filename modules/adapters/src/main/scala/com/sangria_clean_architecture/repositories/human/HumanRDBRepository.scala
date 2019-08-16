package com.sangria_clean_architecture.repositories.human

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.human.{Human, HumanId}
import com.sangria_clean_architecture.gateway.repositories.HumanRepository
import com.sangria_clean_architecture.repositories.episode.EpisodeTable
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

class HumanRDBRepository @Inject()(
  humanConverter: HumanConverter
) extends HumanRepository {

  private val h = HumanTable.syntax("h")
  private val he = HumanEpisodeTable.syntax("he")
  private val e = EpisodeTable.syntax("e")

  override def findById(id: HumanId)(implicit ec: ExecutionContext): Future[Option[Human]] = Future {
    DB.readOnly { implicit session =>
      withSQL[HumanTable] {
        select
          .from(HumanTable as h)
          .innerJoin(HumanEpisodeTable as he).on(h.id, he.humanId)
          .innerJoin(EpisodeTable as e).on(e.id, he.episodeId)
          .where.eq(h.id, id.value)
      }.one(HumanTable(h))
        .toManies(
          rs => HumanEpisodeTable.opt(he)(rs),
          rs => EpisodeTable.opt(e)(rs)
        )
        .map(combineTables(_, _, _))
        .single.apply
        .map(humanConverter.convertToEntity)
    }
  }

  private def combineTables(
    human: HumanTable,
    humanEpisode: Seq[HumanEpisodeTable],
    episode: Seq[EpisodeTable]
  ): HumanTable = {
    human.copy(episodes = episode)
  }

}
