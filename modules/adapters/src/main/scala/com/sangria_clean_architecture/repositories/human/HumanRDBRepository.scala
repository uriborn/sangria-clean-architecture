package com.sangria_clean_architecture.repositories.human

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.episode.EpisodeId
import com.sangria_clean_architecture.entities.human.{HomePlanet, Human, HumanId}
import com.sangria_clean_architecture.gateway.repositories.HumanRepository
import com.sangria_clean_architecture.repositories.episode.EpisodeTable
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

class HumanRDBRepository @Inject()(
  humanConverter: HumanConverter
) extends HumanRepository {

  private val h = HumanTable.syntax("h")
  private val hc = HumanTable.column

  private val he = HumanEpisodeTable.syntax("he")
  private val hec = HumanEpisodeTable.column

  private val e = EpisodeTable.syntax("e")

  override def findAll(implicit ec: ExecutionContext): Future[List[Human]] = Future {
    DB.readOnly { implicit session =>
      withSQL[HumanTable] {
        select
          .from(HumanTable as h)
          .innerJoin(HumanEpisodeTable as he).on(h.id, he.humanId)
          .innerJoin(EpisodeTable as e).on(e.id, he.episodeId)
      }.one(HumanTable(h))
        .toManies(
          rs => HumanEpisodeTable.opt(he)(rs),
          rs => EpisodeTable.opt(e)(rs)
        )
        .map(combineTables(_, _, _))
        .list.apply
        .map(humanConverter.convertToEntity)
    }
  }

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

  override def create(name: String, homePlanet: Option[HomePlanet], episodes: List[EpisodeId])(implicit ec: ExecutionContext): Future[HumanId] = Future {
    DB.localTx { implicit session =>
      val humanId = createHuman(name, homePlanet)
      createHumanEpisode(humanId, episodes)

      humanId
    }
  }

  private def createHuman(name: String, homePlanet: Option[HomePlanet])(implicit ec: ExecutionContext, session: DBSession): HumanId = {
    val id = withSQL {
      insert
        .into(HumanTable)
        .namedValues(
          hc.name       -> name,
          hc.homePlanet -> homePlanet.map(_.entryName)
        )
    }.updateAndReturnGeneratedKey().apply()

    HumanId(id)
  }

  private def createHumanEpisode(humanId: HumanId, episodeIds: List[EpisodeId])(implicit ec: ExecutionContext, session: DBSession): Unit = {
    episodeIds.map(createHumanEpisode(humanId, _))
  }

  private def createHumanEpisode(humanId: HumanId, episodeId: EpisodeId)(implicit ec: ExecutionContext, session: DBSession): Unit = {
    withSQL {
      insert
        .into(HumanEpisodeTable)
        .namedValues(
          hec.humanId   -> humanId.value,
          hec.episodeId -> episodeId.value
        )
    }.update().apply()
  }

  private def combineTables(
    human: HumanTable,
    humanEpisode: Seq[HumanEpisodeTable],
    episode: Seq[EpisodeTable]
  ): HumanTable = {
    human.copy(episodes = episode)
  }

}
