package com.sangria_clean_architecture

import enumeratum.EnumEntry

package object entities {

  case class EntitiesError(message: String)

  type EntitiesValidationResult[A] = Either[EntitiesError, A]

  private[entities] trait EnumWithValidation[E <: EnumEntry] {
    self: enumeratum.Enum[E] =>

    import com.sangria_clean_architecture.shared.util.EitherSyntax._

    def withNameValidation(name: String): EntitiesValidationResult[E] = {
      self.withNameOption(name).map(_.asRight).getOrElse(EntitiesError(s"$name is not a member").asLeft)
    }
  }

}
