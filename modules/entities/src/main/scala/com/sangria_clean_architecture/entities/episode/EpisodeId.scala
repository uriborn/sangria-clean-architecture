package com.sangria_clean_architecture.entities.episode

import com.sangria_clean_architecture.shared.ddd_base.Identifier

case class EpisodeId(value: Long) extends Identifier[Long]
