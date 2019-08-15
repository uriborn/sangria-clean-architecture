package com.sangria_clean_architecture.entities.droid

import com.sangria_clean_architecture.shared.ddd_base.Identifier

case class DroidId(value: Long) extends Identifier[Long]
