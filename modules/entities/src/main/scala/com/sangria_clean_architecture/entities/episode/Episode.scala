package com.sangria_clean_architecture.entities.episode

import com.sangria_clean_architecture.shared.ddd_base.Entity

case class Episode(
  id: EpisodeId,
  name: EpisodeName
) extends Entity[EpisodeId]
