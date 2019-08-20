package com.sangria_clean_architecture.repositories.human

import com.google.inject.Inject
import com.sangria_clean_architecture.entities.episode.EpisodeId
import com.sangria_clean_architecture.entities.human.{HomePlanet, Human, HumanId, HumanName}
import com.sangria_clean_architecture.gateway.repositories.HumanRepository
import com.sangria_clean_architecture.repositories.episode.EpisodeTable
import scalikejdbc.{update => updateS, _}

import scala.collection.compat.Factory
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

  override def create(name: HumanName, homePlanet: Option[HomePlanet], episodeIds: List[EpisodeId])(implicit ec: ExecutionContext): Future[HumanId] = Future {
    DB.localTx { implicit session =>
      val humanId = createHuman(name, homePlanet)
      createHumanEpisode(humanId, episodeIds)

      humanId
    }
  }

  override def update(humanId: HumanId, name: HumanName, homePlanet: Option[HomePlanet], episodeIds: List[EpisodeId])(implicit ec: ExecutionContext): Future[Int] = Future {
    DB.localTx { implicit session =>
      val updated = updateHuman(humanId, name, homePlanet)
      deleteHumanEpisode(humanId, episodeIds)
      createHumanEpisode(humanId, episodeIds)

      updated
    }
  }

  private def createHuman(name: HumanName, homePlanet: Option[HomePlanet])(implicit session: DBSession): HumanId = {
    val id = withSQL {
      insert
        .into(HumanTable)
        .namedValues(
          hc.name       -> name.value,
          hc.homePlanet -> homePlanet.map(_.entryName)
        )
    }.updateAndReturnGeneratedKey().apply()

    HumanId(id)
  }

  private def updateHuman(humanId: HumanId, name: HumanName, homePlanet: Option[HomePlanet])(implicit session: DBSession): Int = {
    withSQL {
      updateS(HumanTable)
        .set(
          hc.name       -> name.value,
          hc.homePlanet -> homePlanet.map(_.entryName)
        ).where.eq(hc.id, humanId.value)
    }.update().apply()
  }

  private def deleteHumanEpisode(humanId: HumanId, episodeIds: List[EpisodeId])(implicit session: DBSession): Unit = {
    withSQL {
      delete.from(HumanEpisodeTable).where.eq(hec.humanId, humanId.value)
    }.update().apply()
  }

  private def createHumanEpisode(humanId: HumanId, episodeIds: List[EpisodeId])(implicit session: DBSession): Unit = {
    val params = episodeIds.map(episodeId => Seq(humanId.value, episodeId.value))

    withSQL {
      insert
        .into(HumanEpisodeTable)
        .namedValues(
          hec.humanId   -> sqls.?,
          hec.episodeId -> sqls.?
        )
    }.batch(params: _*).apply()(session, implicitly[Factory[Int, Seq[Int]]])
  }

  private def combineTables(
    human: HumanTable,
    humanEpisode: Seq[HumanEpisodeTable],
    episode: Seq[EpisodeTable]
  ): HumanTable = {
    human.copy(episodes = episode)
  }

}
