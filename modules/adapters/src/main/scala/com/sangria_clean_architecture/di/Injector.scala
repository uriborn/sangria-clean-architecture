package com.sangria_clean_architecture.di

import com.google.inject.{Guice, Module}

trait Injector {

  val modules: Seq[Module] = Seq(
    new ExecutionContextModule,
    new RepositoryModule
  )

  val injector = Guice.createInjector(modules: _*)

}
