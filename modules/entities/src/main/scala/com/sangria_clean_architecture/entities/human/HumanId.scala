package com.sangria_clean_architecture.entities.human

import com.sangria_clean_architecture.shared.ddd_base.Identifier

case class HumanId(value: Long) extends Identifier[Long]
